package com.dtl.myRpc.myRpcclient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientListener.class);
    private EventLoopGroup worker;
    private Bootstrap bootstrap;
    @Value("${netty.port}")
    public int port;
    @Value("${netty.host}")
    public String host;

    public ClientListener(@Qualifier("worker") EventLoopGroup worker, Bootstrap bootstrap) {
        this.worker = worker;
        this.bootstrap = bootstrap;
    }

    /**
     * 关闭netty服务
     */
    public void close() {
        LOGGER.info("netty server close ...");
        this.worker.shutdownGracefully();
    }

    /**
     * 启动netty-server服务
     */
    public void start() {
        LOGGER.info("netty client start ...");
        this.bootstrap.group( worker)
                .channel(NioSocketChannel.class);
//                .option(ChannelOption.SO_KEEPALIVE, true);
        try {
            //设置事件处理
            this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch){
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });
            ChannelFuture f = this.bootstrap.connect(host, port).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.info("release source");
            worker.shutdownGracefully();
        }
    }
}
