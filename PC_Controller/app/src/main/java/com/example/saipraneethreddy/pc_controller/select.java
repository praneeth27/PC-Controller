package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by sai praneeth reddy on 14-10-2017.
 */

public class select extends Activity implements View.OnClickListener {
    DataOutputStream data_out;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        Button bt_key, bt_mouse, bt_joy, bt_screen, bt_touch,bt_file;
        bt_key = (Button) findViewById(R.id.keyboard);
        bt_mouse = (Button) findViewById(R.id.mouse);
        bt_joy = (Button) findViewById(R.id.joystick);
        bt_touch = (Button) findViewById(R.id.touch);
        bt_screen = (Button) findViewById(R.id.screenshot);
        bt_file = (Button) findViewById(R.id.filetransfer);
        bt_file.setOnClickListener(this);
        bt_joy.setOnClickListener(this);
        bt_touch.setOnClickListener(this);
        bt_mouse.setOnClickListener(this);
        bt_key.setOnClickListener(this);
        bt_screen.setOnClickListener(this);
        final globalClass gl_class = (globalClass) getApplicationContext();
        try {
            data_out = new DataOutputStream(gl_class.sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onKeyDown(int keycode,KeyEvent event) {
        if ((keycode == android.view.KeyEvent.KEYCODE_BACK)) {
            try {
                data_out.writeUTF("exit1");
                Intent prev_activity = new Intent(getApplicationContext(), connect.class);
                startActivity(prev_activity);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keyboard) {
            Intent key_activity = new Intent(getApplicationContext(), keyboard.class);
            startActivity(key_activity);
        }
        else if(v.getId() == R.id.mouse){
            Intent key_activity = new Intent(getApplicationContext(), mouse.class);
            startActivity(key_activity);
        }
        else if(v.getId() == R.id.screenshot){
            Intent key_activity = new Intent(getApplicationContext(), screenshot.class);
            startActivity(key_activity);
        }
        else if(v.getId() == R.id.joystick){
            Intent key_activity = new Intent(getApplicationContext(), joystick.class);
            startActivity(key_activity);
        }
        else if(v.getId() == R.id.touch){
            Intent key_activity = new Intent(getApplicationContext(), touch.class);
            startActivity(key_activity);
        }
        else if(v.getId()==R.id.filetransfer){
            Intent key_activity = new Intent(getApplicationContext(), file.class);
            startActivity(key_activity);
        }
    }
}
