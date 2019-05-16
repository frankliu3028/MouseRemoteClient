package com.usiellau.mouseremoteclient.protocol;

import com.usiellau.mouseremoteclient.entity.ScreenSize;
import com.usiellau.mouseremoteclient.utils.Util;

import java.util.Arrays;

public class Parser {

    private static final String TAG = Parser.class.getSimpleName();

    public static void parseMouseMoveTo(BasicProtocol basicProtocol){
        byte[] data = basicProtocol.getDataArray();
        int x = Util.bytes2Int(data, 0);
        int y = Util.bytes2Int(data, 4);
        System.out.println("x:" + x + " y:" + y);
    }

    public static void parseMouseMoveRelativeTo(BasicProtocol basicProtocol){
        byte[] data = basicProtocol.getDataArray();
        int x = Util.bytes2Int(data, 0);
        int y = Util.bytes2Int(data, 4);
        System.out.println("x:" + x + " y:" + y);
    }

    public static int parsePressDown(BasicProtocol basicProtocol){
        int button = Util.byteArrayToInt(basicProtocol.getDataArray());
        return button;
    }

    public static int parsePressUp(BasicProtocol basicProtocol){
        int button = Util.byteArrayToInt(basicProtocol.getDataArray());
        return button;
    }

    public static ScreenSize parseScreenSizeResponse(BasicProtocol basicProtocol){
        byte[] data = basicProtocol.getDataArray();
        int width = Util.bytes2Int(data, 0);
        int height = Util.bytes2Int(data, 4);
        return new ScreenSize(width, height);
    }
}
