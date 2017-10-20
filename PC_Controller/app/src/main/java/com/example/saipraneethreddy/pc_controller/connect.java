package com.example.saipraneethreddy.pc_controller;

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

public class connect extends AppCompatActivity implements View.OnClickListener {
    Button bt_wifi, bt_bluetooth, bt_connect;
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
        Button bt_back;
        inputip = (EditText) findViewById(R.id.ipfield);
        bt_wifi.setOnClickListener(this);
        bt_bluetooth.setOnClickListener(this);
        bt_connect.setOnClickListener(this);
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
            //b_sock = new BluetoothSocket();
        }

        else if (v.getId() == R.id.ip_ok) {
            //ip = "192.168.0.4";
            ip = inputip.getText().toString();
            int port = 554;
            int bool = 0;
            try{
                final globalClass gl_class = (globalClass) getApplicationContext();
                gl_class.sock = new Socket(ip, port);
                bool = 1;
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
            if (bool == 1) {

                Intent select_activity = new Intent(this, select.class);
                //select_activity.putExtra("outStream", (Parcelable) data_out);
                //setContentView(R.layout.select);
                startActivity(select_activity);
                //startActivity(select_activity);

                //Toast.makeText(getApplicationContext(),"over",Toast.LENGTH_LONG).show();
                /*
                setContentView(R.layout.select);
                now = R.layout.select;
                Button bt_key,bt_mouse,bt_joy,bt_screen,bt_touch;
                bt_key = (Button) findViewById(R.id.keyboard);
                bt_mouse = (Button) findViewById(R.id.mouse);
                bt_joy = (Button) findViewById(R.id.screenshot);
                bt_touch = (Button) findViewById(R.id.touch);
                bt_screen = (Button) findViewById(R.id.screenshot);
                bt_joy.setOnClickListener(this);
                bt_touch.setOnClickListener(this);
                bt_mouse.setOnClickListener(this);
                bt_key.setOnClickListener(this);
                bt_screen.setOnClickListener(this);
                */
            }
        }
        /*
        else if (v.getId() == R.id.keyboard){
            Intent key_activity = new Intent(getApplicationContext(), keyboard.class);
            key_activity.putExtra("outStream", (Parcelable) data_out);
            startActivity(key_activity);

            setContentView(R.layout.keyboard);
            now = R.layout.keyboard;
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
                    char key = s.charAt(s.length()-1);
                    String keyStr = Character.toString(key);
                    try{data_out.writeUTF(keyStr);}
                    catch(Exception e){e.printStackTrace();}
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });

        }
        */
        /*
        else if (v.getId() == R.id.mouse){
            Intent mouse_activity = new Intent(getApplicationContext(), mouse.class);
            mouse_activity.putExtra("outStream", (Parcelable) data_out);
            startActivity(mouse_activity);

            setContentView(R.layout.mouse);
            now = R.layout.mouse;
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

        else if (v.getId() == R.id.right_click){
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

        else if (v.getId() == R.id.joystick){
            //setContentView(R.layout.joystick);
            //now = R.layout.joystick;
        }
        else if (v.getId() == R.id.touch){
            //setContentView(R.layout.touch);
            //now = R.layout.touch;
        }
        else if(v.getId() == R.id.screenshot){
            setContentView(R.layout.screenshot);
            now = R.layout.screenshot;
            byte[] msg;
            int Arrlength;
            try{
                data_out.writeUTF("screenshot");
                Arrlength = data_in.readInt();
                msg = new byte[Arrlength];
                data_in.readFully(msg,0,Arrlength);
                Bitmap bmp = BitmapFactory.decodeByteArray(msg, 0, Arrlength);
                ImageView image = (ImageView) findViewById(R.id.imageView);
                image.setImageBitmap(Bitmap.createScaledBitmap(bmp,576,271,false));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        */
    }

}
