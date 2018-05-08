package com.mas14llo.bt_test_2;


import android.bluetooth.BluetoothSocket;

import java.io.IOException;
/**
public class ReadThread extends Thread{
    BluetoothSocket btSocket;
    int nbrBytes;
    byte[] buffer = new byte[1024];
    String readString;

    public ReadThread(BluetoothSocket btSocket){
        this.btSocket = btSocket;
    }

    public void run(){
        while(btSocket != null)
            try {
                nbrBytes= btSocket.getInputStream().read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(nbrBytes > 0){
                readString = new String(buffer, 0,nbrBytes)
                readTxt.setText(read());
            }
    }

}
