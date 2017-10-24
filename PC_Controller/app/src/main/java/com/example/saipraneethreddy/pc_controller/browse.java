package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;

public class browse extends Activity implements View.OnClickListener{
    EditText typer;
    DataOutputStream data_out;

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if ((keycode == android.view.KeyEvent.KEYCODE_BACK)) {
            try {
                data_out.writeUTF("exit");
                Intent prev_activity = new Intent(getApplicationContext(), track.class);
                startActivity(prev_activity);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);
        final globalClass gl_class = (globalClass) getApplicationContext();
        try {
            data_out = new DataOutputStream(gl_class.sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        typer = (EditText) findViewById(R.id.webaddress);
        Button bt_submit = (Button) findViewById(R.id.submit);
        bt_submit.setOnClickListener(this);
        Button bt_reset = (Button) findViewById(R.id.clear);
        bt_reset.setOnClickListener(this);
        typer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                typer.setFocusableInTouchMode(true);
                return false;
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            try {
                data_out.writeUTF("submit");
                data_out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                data_out.writeUTF(typer.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(v.getId() == R.id.clear){
            try {
                data_out.writeUTF("reset");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
