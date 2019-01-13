package com.usiellau.mouseremoteclient;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ServerScanner {

    private Context mContext;

    public ServerScanner(Context context){
        mContext=context;
    }

    public InetSocketAddress scan(){
        WifiManager wm=(WifiManager)mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi=wm.getConnectionInfo();
        if(wi==null)return null;
        int ipint=wi.getIpAddress();
        String ip= Formatter.formatIpAddress(ipint);
        final String front=ip.substring(0,ip.lastIndexOf(".")+1);

        try{

            DatagramSocket socket=new DatagramSocket();
            byte[] msgb=new byte[2];
            msgb[0] = 0;
            msgb[1] = 106;
            for(int i=0;i<256;++i){
                try{
                    InetAddress targetIp=InetAddress.getByName(front+i);
                    DatagramPacket packet=new DatagramPacket(msgb,msgb.length,targetIp,10106);
                    //Log.d("ServerScanner","send to ip:"+targetIp);
                    socket.send(packet);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            byte[] buffer=new byte[1024];
            DatagramPacket rPacket=new DatagramPacket(buffer,buffer.length);
            socket.setSoTimeout(3000);
            socket.receive(rPacket);
            InetSocketAddress res=new InetSocketAddress(rPacket.getAddress(),10106);
            return res;
        }catch(SocketTimeoutException e){
            e.printStackTrace();
        }catch(SocketException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return null;

    }

}
