package com.usiellau.mouseremoteclient.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Client {
    private final String TAG = Client.class.getSimpleName();
    private final String remoteIp;
    private final int port;

    private ClientHandler clientHandler;

    public Client(String remoteIp, int port){
        this.remoteIp = remoteIp;
        this.port = port;
    }

    public void start(){
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            clientHandler = new ClientHandler();
                            ch.pipeline().addLast(new ProtocolEncoder(), new ProtocolDecoder(), clientHandler);
                        }
                    });
            ChannelFuture f = b.connect(remoteIp, port).sync();
            f.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
            worker.shutdownGracefully();
        }
    }

}
