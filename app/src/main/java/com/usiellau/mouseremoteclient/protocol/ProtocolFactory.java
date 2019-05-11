package com.usiellau.mouseremoteclient.protocol;

import com.usiellau.mouseremoteclient.utils.Constant;
import com.usiellau.mouseremoteclient.utils.Util;

import java.nio.ByteBuffer;

public class ProtocolFactory {

    public static BasicProtocol createMoveRelative(int x, int y){
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.MOVE_RELATIVE);
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(x);
        buffer.putInt(y);
        basicProtocol.setDataArray(buffer.array());
        return basicProtocol;
    }

    public static BasicProtocol createPressDown(int button){
        if(button != Constant.BUTTON_LEFT && button != Constant.BUTTON_RIGHT){
            return null;
        }
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.PRESS_DOWN);
        basicProtocol.setDataArray(Util.int2ByteArrays(button));
        return basicProtocol;
    }

    public static BasicProtocol createPressUp(int button){
        if(button != Constant.BUTTON_LEFT && button != Constant.BUTTON_RIGHT){
            return null;
        }
        BasicProtocol basicProtocol = new BasicProtocol();
        basicProtocol.setMsgId(MsgId.PRESS_UP);
        basicProtocol.setDataArray(Util.int2ByteArrays(button));
        return basicProtocol;
    }

}
