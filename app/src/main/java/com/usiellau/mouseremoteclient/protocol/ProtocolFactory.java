package com.usiellau.mouseremoteclient.protocol;

import java.io.File;

public class ProtocolFactory {

    public static BasicProtocol createIdentificationResponse(int errorCode){
        BasicProtocol basicProtocol = new BasicProtocol();

        basicProtocol.setErrorCode((byte)errorCode);
        return basicProtocol;
    }

}
