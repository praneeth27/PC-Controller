package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;



public class snapshot extends Activity {
    DataInputStream data_in;
    DataOutputStream data_out;
    @Override
    public boolean onKeyDown(int keycode,KeyEvent event) {
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
        setContentView(R.layout.snapshot);
        final globalClass gl_class = (globalClass) getApplicationContext();
        try {
            if(gl_class.mode) {
                data_in = new DataInputStream(gl_class.sock.getInputStream());
                data_out = new DataOutputStream(gl_class.sock.getOutputStream());
            }
            else {
                data_in = new DataInputStream(gl_class.b_sock.getInputStream());
                data_out = new DataOutputStream(gl_class.b_sock.getOutputStream());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] msg;
        int Arrlength;
        try{
            Arrlength = data_in.readInt();
            msg = new byte[Arrlength];
            data_in.readFully(msg,0,Arrlength);
            Bitmap bmp = BitmapFactory.decodeByteArray(msg, 0, Arrlength);
            ImageView image = (ImageView) findViewById(R.id.viewimg);
            image.setImageBitmap(Bitmap.createScaledBitmap(bmp,576*4,320*4,false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}