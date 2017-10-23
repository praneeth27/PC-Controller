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

public class joystick extends Activity implements View.OnClickListener {
    DataOutputStream data_out;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final globalClass gl_class = (globalClass) getApplicationContext();
        try {
            if(gl_class.mode) data_out = new DataOutputStream(gl_class.sock.getOutputStream());
            else data_out = new DataOutputStream(gl_class.b_sock.getOutputStream());

            data_out.writeUTF("joystick");
            data_out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.joystick);

        TextView stick = (TextView) findViewById(R.id.analog);
        Button bt_O,bt_D,bt_T,bt_X,bt_start,bt_select,bt_left,bt_right;
        bt_O = (Button) findViewById(R.id.joy_O);
        bt_D = (Button) findViewById(R.id.joy_D);
        bt_T = (Button) findViewById(R.id.joy_T);
        bt_X = (Button) findViewById(R.id.joy_X);
        bt_start = (Button) findViewById(R.id.start);
        bt_select = (Button) findViewById(R.id.select);
        bt_left = (Button) findViewById(R.id.left_1);
        bt_right = (Button) findViewById(R.id.right_1);
        bt_O.setOnClickListener(this);
        bt_T.setOnClickListener(this);
        bt_X.setOnClickListener(this);
        bt_D.setOnClickListener(this);
        bt_start.setOnClickListener(this);
        bt_select.setOnClickListener(this);
        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        stick.setOnTouchListener(new View.OnTouchListener(){
            float x=0,y=0,x1=0,y1=0;
            @Override
            public boolean onTouch(View v,MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    x = event.getX();
                    y = event.getY();
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE){
                    x1 = event.getX()-x;y1 = event.getY()-y;
                        try {
                            if( x1 > 50) {
                                data_out.writeUTF("d");
                                data_out.flush();
                            }
                            else if( x1 < -50) {
                                data_out.writeUTF("a");
                                data_out.flush();
                            }
                            if( y1 < -50) {
                                data_out.writeUTF("w");
                                data_out.flush();
                            }
                            else if( y1 > 50) {
                                data_out.writeUTF("s");
                                data_out.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                return true;
            }
        });
    }
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
    public void onClick(View v){
        switch(v.getId()){
            case R.id.joy_T :{
                try{
                    data_out.writeUTF("right_click");
                    data_out.flush();
                }
                catch(Exception e) {e.printStackTrace();}
                break;
            }
            case R.id.joy_X :{
                try{
                    data_out.writeUTF("left_click");
                    data_out.flush();
                }
                catch(Exception e) {e.printStackTrace();}
                break;
            }
            case R.id.joy_D :{
                try{
                    data_out.writeUTF("c");
                    data_out.flush();
                }
                catch(Exception e) {e.printStackTrace();}
                break;
            }
            case R.id.joy_O :{
                try{
                    data_out.writeUTF("space");
                    data_out.flush();
                }
                catch(Exception e) {e.printStackTrace();}
                break;
            }
            case R.id.start :{
                try{
                    data_out.writeUTF("enter");
                    data_out.flush();
                }
                catch(Exception e) {e.printStackTrace();}
                break;
            }
            case R.id.select :{
                try{
                    data_out.writeUTF("esc");
                    data_out.flush();
                }
                catch(Exception e) {e.printStackTrace();}
                break;
            }
            case R.id.left_1 :{
                try{
                    data_out.writeUTF("q");
                    data_out.flush();
                }
                catch(Exception e) {e.printStackTrace();}
                break;
            }
            case R.id.right_1:{
                try{
                    data_out.writeUTF("e");
                    data_out.flush();
                }
                catch(Exception e) {e.printStackTrace();}
                break;
            }
        }
    }
}
