package com.mas14llo.bt_test_2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
        import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
        import java.util.UUID;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.MotionEvent;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
        import android.widget.Toast;

import static android.os.SystemClock.sleep;

public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG = "MainActivity";
    Context appContext;
    BluetoothSocket btSocket;

    Button i1;
    TextView t1;
    TextView seekBarTxt;
    TextView readTxt;
    Button sendBtn;
    Button setBtn;
    EditText entervalue;
    SeekBar seekBar;
    TextView btAddress;

    CheckBox posBox;
    CheckBox velBox;

    String red = "red";
    String green = "green";
    String blue = "blue";
    String readString;
    String sendString;

    int nbrBytes;
    byte[] buffer = new byte[1024];

    int speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {setw();} catch (Exception e) {}

        // INIT SETUP;

        appContext = getApplicationContext();
        Setup setup = new Setup(appContext, btAddress);
        btSocket = setup.setUpBluetooth();
        if(btSocket != null){
            Log.d(TAG, "OnCreate: setupBluetooth finished");
        }else{
            Log.d(TAG, "OnCreate: socket error");
        }



    }

    @SuppressLint("ClickableViewAccessibility")
    private void setw() throws IOException {

        readTxt = (TextView) findViewById(R.id.readTxt);
        seekBarTxt = (TextView) findViewById(R.id.seekBarTxt);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        setBtn = (Button) findViewById(R.id.setBtn);
        entervalue = (EditText) findViewById(R.id.enterVal);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        posBox = (CheckBox) findViewById(R.id.box_pos);
        velBox = (CheckBox) findViewById(R.id.box_val);
        btAddress = findViewById(R.id.btAddress);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick: checking boxes.....");
                if(posBox.isChecked()){
                    Log.d(TAG, "OnClick: positionbox.....");
                    sendString = "p" + entervalue.getText().toString();
                    writeBytes(sendString);
                    Log.d(TAG, "OnClick: sent!");
                }else if(velBox.isChecked()){
                    Log.d(TAG, "OnClick: velbox.....");
                    sendString = "s" + entervalue.getText().toString();
                    writeBytes(sendString);
                    Log.d(TAG, "OnClick: sent!");
                }else{
                    Log.d(TAG, "OnClick: no box checked");
                    Toast.makeText(getApplicationContext(),"Please check one box", Toast.LENGTH_SHORT).show();
                }


                entervalue.setText("");
                readTxt.setText("");

                sleep(1000);
                readTxt.setText(read());

            }
        });

        /**
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speed = (2*i - 100);
                seekBarTxt.setText("Speed: " + speed );

                if(speed ==0){
                    led_on_off(red);
                    Log.d(TAG, "SeekBar: red");
                }else if (speed == 100){
                    led_on_off(green);
                    Log.d(TAG, "SeekBar: green");
                }else if(speed == -100){
                    led_on_off(blue);
                }

            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                seekBar.setProgress(50);
            }
        });**/
    }



    @Override
    public void onClick(View v) {
        try {

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void writeBytes(String i) {
        try {
            if (btSocket!=null){
                Log.d(TAG, "Led_on_off: writing.....");
                btSocket.getOutputStream().write(i.toString().getBytes());
                Log.d(TAG, "Led_on_off: done writing.");
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
/*
    private class ReadThread extends Thread {
        BluetoothSocket socket;
        InputStream input;
        public ReadThread(BluetoothSocket socket) {
            Log.d(TAG, "ReadThread: Starting.");
             this.socket = socket;
            try {
                input = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){

            try {
                if (socket!=null){
                    nbrBytes= input.read(buffer);
                    readString = new String(buffer, 0,nbrBytes);
                    readTxt.setText(readString);
                    Log.d(TAG, "Read : bytes read " + nbrBytes);
                    Log.d(TAG, "Read : length of readstring " +readString.length());

                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }*/



    private String read(){
        try {
            if (btSocket!=null){
                nbrBytes= btSocket.getInputStream().read(buffer);
                readString = new String(buffer, 0,nbrBytes);
                Log.d(TAG, "Read : bytes read " + nbrBytes);
                Log.d(TAG, "Read : length of readstring " +readString.length());
                return readString;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "error";
    }
}
