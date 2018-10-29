package com.dtl.myRpc.myRpcclient.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf = ctx.alloc().buffer(12);
        buf.writeBytes("hello server".getBytes());
        for(int i=0;i<100;i++){
            executorService.execute(()->{
                ctx.writeAndFlush(buf);
//                f.addListener(future -> {
//                    assert f==future;
////            ctx.close();
//                });
            });
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        System.out.println("receive from server: "+m.toString(CharsetUtil.UTF_8));
        try {
//            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
//            System.out.println(new Date(currentTimeMillis));
            System.out.println("receive from server: "+m.toString(CharsetUtil.UTF_8));
//            ctx.close();
        } finally {
//            m.release();
            m.retain();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
