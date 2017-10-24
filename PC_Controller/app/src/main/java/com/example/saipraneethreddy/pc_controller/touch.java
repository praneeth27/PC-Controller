package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by sai praneeth reddy on 14-10-2017.
 */

public class touch extends Activity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{
    DataInputStream data_in;
    DataOutputStream data_out;
    GestureDetectorCompat mDetector;
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
    private class display extends AsyncTask<String,Integer,Bitmap>{
        private final WeakReference<ImageView> ref;
        public display(ImageView v){
            ref = new WeakReference<>(v);
        }
        @Override
        protected Bitmap doInBackground(String... params){
            try {
                int Arrlength = data_in.readInt();
                byte[] msg = new byte[Arrlength];
                data_in.readFully(msg, 0, Arrlength);
                Bitmap bmp = BitmapFactory.decodeByteArray(msg, 0, Arrlength);
                return bmp;
            }
            catch(Exception e){e.printStackTrace();return null;
            }
        }
        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);
            ImageView v = ref.get();
            v.setImageBitmap(Bitmap.createScaledBitmap(bmp, 576*4, 320*4, false));
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.touch2);
        setContentView(R.layout.touch);
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        final globalClass gl_class = (globalClass) getApplicationContext();
        try {
            if(gl_class.mode) {
                data_out = new DataOutputStream(gl_class.sock.getOutputStream());
                data_in = new DataInputStream(gl_class.sock.getInputStream());
            }
            else {
                data_out = new DataOutputStream(gl_class.b_sock.getOutputStream());
                data_in = new DataInputStream(gl_class.b_sock.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            data_out.writeUTF("touch");
            ImageView image = (ImageView) findViewById(R.id.touchscreen);
            for(int i =0;i<10000;i++)new display(image).execute("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        float x=event.getX(),y=event.getY(),x1=0,y1=0;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x = event.getX();
            y = event.getY();
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            x1 = event.getX()-x;y1 = event.getY()-y;
            if (x1 != 0 && y1 != 0) {
                try {
                    String inp = "";
                    inp = inp + "2 " + Integer.toString(Math.round(x1)) + " " + Integer.toString(Math.round(y1));
                    data_out.writeUTF(inp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            if(x1 == 0 && y1 ==0 ) {
                if (!(x == 0 && y == 0)) {
                    try {
                        String inp = "";
                        inp = inp + "1 " + Integer.toString(Math.round(x)) + " " + Integer.toString(Math.round(y));
                        data_out.writeUTF(inp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {

        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {

    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {

        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        float x = event.getX(),y = event.getY();
        String inp = "3 " + Integer.toString(Math.round(x)) + " " + Integer.toString(Math.round(y));
        try {
            data_out.writeUTF(inp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {

        return true;
    }
}