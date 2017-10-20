package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by sai praneeth reddy on 14-10-2017.
 */

public class keyboard extends Activity {
    EditText typer;
    DataOutputStream data_out;
    @Override
    public boolean onKeyDown(int keycode,KeyEvent event) {
        if ((keycode == android.view.KeyEvent.KEYCODE_BACK)) {
            try {
                data_out.writeUTF("exit");
                Intent prev_activity = new Intent(getApplicationContext(), select.class);
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
        setContentView(R.layout.keyboard);
        final globalClass gl_class = (globalClass) getApplicationContext();
        try {
            data_out = new DataOutputStream(gl_class.sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            data_out.writeUTF("keyboard");
            data_out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        typer = (EditText) findViewById(R.id.type);
        typer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                typer.setFocusableInTouchMode(true);
                return false;
            }
        });
        typer.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                if(before - s.length() == 1) {
                    try{data_out.writeUTF("backspace");}
                    catch(Exception e){e.printStackTrace();}
                }
                else if(before == s.length()){
                    try{data_out.writeUTF("space");}
                    catch(Exception e){e.printStackTrace();}
                }
                else {
                    char key = s.charAt(s.length()-1);
                    String keyStr = Character.toString(key);
                    try{data_out.writeUTF(keyStr);}
                    catch(Exception e){e.printStackTrace();}
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
