package com.usiellau.mouseremoteclient.network;

import android.util.Log;

import com.usiellau.mouseremoteclient.entity.ScreenSize;
import com.usiellau.mouseremoteclient.protocol.BasicProtocol;
import com.usiellau.mouseremoteclient.protocol.MsgId;
import com.usiellau.mouseremoteclient.protocol.Parser;
import com.usiellau.mouseremoteclient.protocol.ProtocolFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private final String TAG = ClientHandler.class.getSimpleName();

    private ChannelHandlerContext ctx;
    private ClientCallback callback;

    private ScreenSize screenSize;

    public ClientHandler(ClientCallback callback){
        this.callback = callback;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
        ctx.writeAndFlush(ProtocolFactory.createScreenSizeRequest());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicProtocol basicProtocol = (BasicProtocol) msg;
        switch (basicProtocol.getMsgId()){
            case MsgId.SCREEN_SIZE_RESPONSE:
                ScreenSize screenSize = Parser.parseScreenSizeResponse(basicProtocol);
                Log.d(TAG, "screen size:" + screenSize.toString());
                this.screenSize = screenSize;
                break;
                default:
                    break;
        }
    }

    public void mouseMoveTo(int x, int y){
        BasicProtocol basicProtocol = ProtocolFactory.createMouseMoveTo(x, y);
        ctx.writeAndFlush(basicProtocol);
    }

    public void mouseMoveRelativeTo(int x, int y){
        BasicProtocol basicProtocol = ProtocolFactory.createMouseMoveRelativeTo(x, y);
        ctx.writeAndFlush(basicProtocol);
    }

    public void mousePressDown(int button){
        BasicProtocol basicProtocol = ProtocolFactory.createPressDown(button);
        ctx.writeAndFlush(basicProtocol);
    }

    public void mousePressUp(int button){
        BasicProtocol basicProtocol = ProtocolFactory.createPressUp(button);
        ctx.writeAndFlush(basicProtocol);
    }

    public void mouseClick(int button){
        BasicProtocol basicProtocol = ProtocolFactory.createMouseClick(button);
        ctx.writeAndFlush(basicProtocol);
    }

    public ScreenSize getScreenSize(){
        return screenSize;
    }

    public void close(){
        ctx.close();
    }
}
