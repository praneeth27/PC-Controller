package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;


public class track extends Activity implements View.OnClickListener {
    DataOutputStream data_out;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track);
        Button bt_shut = (Button) findViewById(R.id.shut);
        Button bt_snapshot = (Button) findViewById(R.id.snapshot);
        Button bt_restart = (Button) findViewById(R.id.restart);
        Button bt_suspend = (Button) findViewById(R.id.suspend);
        Button bt_lock = (Button) findViewById(R.id.lock);
        Button bt_browse = (Button) findViewById(R.id.browse);
        bt_snapshot.setOnClickListener(this);
        bt_shut.setOnClickListener(this);
        bt_suspend.setOnClickListener(this);
        bt_restart.setOnClickListener(this);
        bt_lock.setOnClickListener(this);
        bt_browse.setOnClickListener(this);
        final globalClass gl_class = (globalClass) getApplicationContext();
        try {
            if(gl_class.mode) data_out = new DataOutputStream(gl_class.sock.getOutputStream());
            else data_out = new DataOutputStream(gl_class.b_sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            data_out.writeUTF("track");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onKeyDown(int keycode,KeyEvent event) {
        if ((keycode == android.view.KeyEvent.KEYCODE_BACK)) {
            try {
                data_out.writeUTF("exit1");
                Intent prev_activity = new Intent(getApplicationContext(), select.class);
                startActivity(prev_activity);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.shut) {
            try {
                data_out.writeUTF("shutdown");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(v.getId() == R.id.restart){
            try {
                data_out.writeUTF("restart");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(v.getId() == R.id.lock){
            try {
                data_out.writeUTF("lock");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(v.getId() == R.id.suspend){
            try {
                data_out.writeUTF("suspend");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(v.getId() == R.id.snapshot){
            try {
                data_out.writeUTF("snapshot");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent key_activity = new Intent(getApplicationContext(), snapshot.class);
            startActivity(key_activity);
        }
        else if(v.getId()==R.id.browse){
            try {
                data_out.writeUTF("browse");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent key_activity = new Intent(getApplicationContext(), browse.class);
            startActivity(key_activity);
        }
    }
}




