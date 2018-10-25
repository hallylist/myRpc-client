package com.dtl.myRpc.myRpcclient.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean
    public Bootstrap bootstrapInstance(){
        return new Bootstrap();
    }
    @Bean("worker")
    public EventLoopGroup workerEventLoopGroupInstance(){
        return new NioEventLoopGroup();
    }

}
