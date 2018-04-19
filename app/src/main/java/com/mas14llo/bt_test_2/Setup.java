package com.mas14llo.bt_test_2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by louis on 2018-04-15.
 */

public class Setup {
    Context appContext;
    TextView t1;

    private static final String TAG = "SetUp";

    String address = null , name=null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    Set<BluetoothDevice> pairedDevices;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    public Setup(Context appContext, TextView t1){
        this.appContext = appContext;
        this.t1 = t1;


    }

    public BluetoothSocket setUpBluetooth(){
        try {
            bluetooth_connect_device();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return btSocket;
    }

    private void bluetooth_connect_device() throws IOException {
        try {
            myBluetooth = BluetoothAdapter.getDefaultAdapter();
            address = myBluetooth.getAddress();
            pairedDevices = myBluetooth.getBondedDevices();

            if (pairedDevices.size()>0) {
                for(BluetoothDevice bt : pairedDevices) {
                    address=bt.getAddress().toString();
                    name = bt.getName().toString();
                    Toast.makeText(appContext,"Connected", Toast.LENGTH_SHORT).show();
                }
            }
        } catch(Exception we){

        }

        myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
        BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
        try{
            btSocket.connect();
        }catch(Exception e){
            Log.d(TAG,"bluetoothconnectdevice: Error in connect, " + e);
        }

        try {
            t1.setText("BT Name: "+name+"\nBT Address: "+address);
        }catch(Exception e){

        }
    }
}
