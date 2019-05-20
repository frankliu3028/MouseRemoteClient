package com.usiellau.mouseremoteclient.sd;

import com.usiellau.mouseremoteclient.entity.DeviceInfo;

import java.util.ArrayList;

public interface SDClientCallback {
    void discoverDevices(ArrayList<DeviceInfo> deviceInfoList);
}
