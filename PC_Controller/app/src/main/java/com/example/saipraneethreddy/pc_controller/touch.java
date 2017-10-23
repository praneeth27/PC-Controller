package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by sai praneeth reddy on 14-10-2017.
 */

public class touch extends Activity {
    DataInputStream data_in;
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
        //setContentView(R.layout.touch2);
        setContentView(R.layout.touch);
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


            data_out.writeUTF("touch");
            ImageView image = (ImageView) findViewById(R.id.touchscreen);
            AnimationDrawable anim = new AnimationDrawable();
            for(int i = 0;i<4;i++){
                int Arrlength = data_in.readInt();
                byte[] msg = new byte[Arrlength];
                data_out.writeUTF("1");
                data_in.readFully(msg,0,Arrlength);
                Bitmap bmp = BitmapFactory.decodeByteArray(msg, 0, Arrlength);
                Drawable draw = new BitmapDrawable(getResources(), bmp);
                anim.addFrame(draw,5000);
                if(i == 0) {

                    image.setBackgroundDrawable(anim);
                    anim.start();
                }
                Toast.makeText(getApplicationContext(),Integer.toString(Arrlength), Toast.LENGTH_SHORT).show();
                data_out.writeUTF("1");
            }
            /*
            int Arrlength = data_in.readInt();
            byte[] msg = new byte[Arrlength];
            data_out.writeUTF("1");
            data_in.readFully(msg,0,Arrlength);
            Bitmap bmp = BitmapFactory.decodeByteArray(msg, 0, Arrlength);
            image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 576*4, 320*4, false));
            data_out.writeUTF("1");

            int Arrlength2 = data_in.readInt();
            byte[] msg2 = new byte[Arrlength2];
            data_out.writeUTF("1");
            data_in.readFully(msg2,0,Arrlength2);
            Bitmap bmp2 = BitmapFactory.decodeByteArray(msg, 0, Arrlength);
            image.setImageBitmap(Bitmap.createScaledBitmap(bmp2, 576*4, 320*4, false));


            //RelativeLayout layout = (RelativeLayout) findViewById(R.id.imLayout);

            while(true) {
                int Arrlength = data_in.readInt();
                byte[] msg = new byte[Arrlength];
                data_out.writeUTF("1");
                data_in.readFully(msg,0,Arrlength);
                Toast.makeText(getApplicationContext(),Arrlength.toString(),Toast.LENGTH_SHORT).show();
                Bitmap bmp = BitmapFactory.decodeByteArray(msg, 0, Arrlength);
                ImageView image = new ImageView(this);
                image.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 576*4, 320*4, false));
                layout.addView(image);

                if(i == 1) {
                    File outfile = new File(path + "im1.jpg");
                    outfile.createNewFile();

                    FileOutputStream out_stream = new FileOutputStream(outfile);
                    out_stream.write(msg, 0, Arrlength);
                }
                else if (i == 3){
                    File outfile = new File(path + "im2.jpg");
                    outfile.createNewFile();

                    FileOutputStream out_stream = new FileOutputStream(outfile);
                    out_stream.write(msg, 0, Arrlength);
                }
                i = i+1;
                data_out.writeUTF("1");
            }
            */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}