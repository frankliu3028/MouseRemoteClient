package com.usiellau.mouseremoteclient.protocol;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parseMoveRelative() {
        BasicProtocol basicProtocol = ProtocolFactory.createMoveRelative(100, 200);
        Parser.parseMoveRelative(basicProtocol);
    }
}