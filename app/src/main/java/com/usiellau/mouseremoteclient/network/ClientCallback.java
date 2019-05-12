package com.usiellau.mouseremoteclient.network;

import com.usiellau.mouseremoteclient.protocol.BasicProtocol;

public interface ClientCallback {
    void receivePacket(BasicProtocol basicProtocol);
}
