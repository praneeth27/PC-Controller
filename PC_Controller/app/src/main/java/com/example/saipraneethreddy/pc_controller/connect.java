package com.example.saipraneethreddy.pc_controller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Socket;
import java.util.UUID;

public class connect extends AppCompatActivity implements View.OnClickListener {
    Button bt_wifi, bt_bluetooth, bt_connect, bt_b_connect;
    String ip;
    EditText inputip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        bt_wifi = (Button) findViewById(R.id.wifi);
        bt_bluetooth = (Button) findViewById(R.id.bluetooth);
        bt_connect = (Button) findViewById(R.id.ip_ok);
        bt_b_connect = (Button) findViewById(R.id.mac_ok);
        inputip = (EditText) findViewById(R.id.ipfield);
        bt_wifi.setOnClickListener(this);
        bt_bluetooth.setOnClickListener(this);
        bt_connect.setOnClickListener(this);
        bt_b_connect.setOnClickListener(this);
    }
    @Override
    public boolean onKeyDown(int keycode,KeyEvent event){
        if((keycode == android.view.KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.wifi) {
            inputip.setVisibility(View.VISIBLE);
            bt_connect.setVisibility(View.VISIBLE);
        }
        else if (v.getId() == R.id.bluetooth) {
            inputip.setVisibility(View.VISIBLE);
            bt_b_connect.setVisibility(View.VISIBLE);
        }
        else if (v.getId() == R.id.mac_ok){
            int bool = 0;
            String address = inputip.getText().toString();
            StringBuilder build = new StringBuilder(address);
            build.insert(10,':');build.insert(8,':');build.insert(6,':');
            build.insert(4,':');build.insert(2,':');
            address = build.toString();
            Toast.makeText(getApplicationContext(),address,Toast.LENGTH_LONG).show();
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            final globalClass gl_class = (globalClass) getApplicationContext();
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = adapter.getRemoteDevice(address);
            try{

                gl_class.b_sock = device.createRfcommSocketToServiceRecord(uuid);
                gl_class.b_sock.connect();
                Toast.makeText(getApplicationContext(),"Connected", Toast.LENGTH_LONG).show();
                gl_class.mode = false;
                bool = 1;
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
            if (bool == 1) {
                Intent select_activity = new Intent(this, select.class);
                startActivity(select_activity);
            }
        }
        else if (v.getId() == R.id.ip_ok) {
            //ip = "192.168.0.4";
            ip = inputip.getText().toString();
            int port = 554;
            int bool = 0;
            try{
                final globalClass gl_class = (globalClass) getApplicationContext();
                gl_class.sock = new Socket(ip, port);
                gl_class.mode = true;
                bool = 1;
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
            if (bool == 1) {
                Intent select_activity = new Intent(this, select.class);
                startActivity(select_activity);
            }
        }
    }

}
