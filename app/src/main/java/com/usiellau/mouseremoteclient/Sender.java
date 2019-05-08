package com.usiellau.mouseremoteclient;

import com.usiellau.mouseremoteclient.utils.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Sender extends Thread {

    private DatagramSocket socket;
    private String ip;
    private int port;

    public Sender(String ip, int port){
        this.ip = ip;
        this.port = port;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while(true){
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendRelativePos(int x, int y){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(1);
        try {
            bos.write(Util.int2ByteArrays(x));
            bos.write(Util.int2ByteArrays(y));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = bos.toByteArray();
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length,InetAddress.getByName(ip),port);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendClick(){
        byte[] data = new byte[1];
        data[0] = 2;
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length,InetAddress.getByName(ip), port);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
