package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by sai praneeth reddy on 14-10-2017.
 */

public class mouse extends Activity implements View.OnClickListener{
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
        setContentView(R.layout.mouse);
        final globalClass gl_class = (globalClass) getApplicationContext();
        try {
            if(gl_class.mode) data_out = new DataOutputStream(gl_class.sock.getOutputStream());
            else data_out = new DataOutputStream(gl_class.b_sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            data_out.writeUTF("mouse");
            data_out.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        Button leftClick = (Button) findViewById(R.id.left_click);
        Button rightClick = (Button) findViewById(R.id.right_click);
        leftClick.setOnClickListener(this);
        rightClick.setOnClickListener(this);
        TextView mousepad = (TextView) findViewById(R.id.mousePad);
        mousepad.setOnTouchListener(new View.OnTouchListener() {
            float x=0,y=0,x1=0,y1=0;
            @Override
            public boolean onTouch(View v,MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    x = event.getX();
                    y = event.getY();
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    x1 = event.getX()-x;y1 = event.getY()-y;
                    if (x1 != 0 && y1 != 0) {
                        try {
                            data_out.writeUTF("move");
                            data_out.flush();
                            data_out.writeUTF(String.valueOf(x1));
                            data_out.flush();
                            data_out.writeUTF(String.valueOf(y1));
                            data_out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    if(x1 == 0 && y1 == 0)
                        try {
                            data_out.writeUTF("left_click");
                            data_out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                return true;
            }
        });
    }
    @Override
    public void onClick(View v){
        if (v.getId() == R.id.right_click){
            try{
                data_out.writeUTF("right_click");
                data_out.flush();
            }
            catch(Exception e) {e.printStackTrace();}
        }
        else if (v.getId() == R.id.left_click){
            try{
                data_out.writeUTF("left_click");
                data_out.flush();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
