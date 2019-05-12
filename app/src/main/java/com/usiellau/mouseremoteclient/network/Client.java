package com.usiellau.mouseremoteclient.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
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
    private ClientCallback callback;

    public Client(String remoteIp, int port, ClientCallback callback){
        this.remoteIp = remoteIp;
        this.port = port;
        this.callback = callback;
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
                            clientHandler = new ClientHandler(callback);
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

    public void mouseMoveTo(int x, int y){
        clientHandler.mouseMoveTo(x, y);
    }

    public void mouseMoveRelativeTo(int x, int y){
        clientHandler.mouseMoveRelativeTo(x, y);
    }

    public void mousePressDown(int button){
        clientHandler.mousePressDown(button);
    }

    public void mousePressUp(int button){
        clientHandler.mousePressUp(button);
    }

    public void close(){
        clientHandler.close();
    }

}
