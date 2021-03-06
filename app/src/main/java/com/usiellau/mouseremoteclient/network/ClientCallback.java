package com.usiellau.mouseremoteclient.network;

import com.usiellau.mouseremoteclient.entity.ScreenSize;
import com.usiellau.mouseremoteclient.protocol.BasicProtocol;

public interface ClientCallback {
    void receivePacket(BasicProtocol basicProtocol);
    void connectSuccess();
    void connectFailure();
}
