package com.usiellau.mouseremoteclient.protocol;

public class MsgId {
    public static final byte MOVE_TO = 0;
    public static final byte MOVE_RELATIVE = 1;
    public static final byte PRESS_DOWN = 2;
    public static final byte PRESS_UP = 3;
    public static final byte MOUSE_CLICK = 4;
    public static final byte SCREEN_SIZE_REQUEST = 5;
    public static final byte SCREEN_SIZE_RESPONSE = 6;
    public static final byte SERVICE_DISCOVER_REQUEST = 7;
    public static final byte SERVICE_DISCOVER_RESPONSE = 8;
}
