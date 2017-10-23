package com.example.saipraneethreddy.pc_controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by sai praneeth reddy on 15-10-2017.
 */

public class file extends Activity implements View.OnClickListener {
    DataOutputStream data_out;
    DataInputStream data_in;
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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filetransfer);
        Button bt_send = (Button) findViewById(R.id.send);
        Button bt_recieve = (Button) findViewById(R.id.recieve);
        bt_send.setOnClickListener(this);
        bt_recieve.setOnClickListener(this);
    }
    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:

                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    try {
                        String path = getPath(this, uri);
                        File myfile = new File(path);
                        FileInputStream stream = new FileInputStream(myfile);
                        int filelength = (int) myfile.length();
                        data_out.writeInt(filelength);
                        Toast.makeText(getApplicationContext(),"file sent",Toast.LENGTH_LONG).show();
                        data_out.writeUTF(myfile.getName());
                        byte[] dataArr = new byte[filelength];
                        stream.read(dataArr,0,filelength);
                        data_out.write(dataArr);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    @Override
    public void onClick(View v){
        if(v.getId()== R.id.send){
            final globalClass gl_class = (globalClass) getApplicationContext();
            try{
                if(gl_class.mode) data_out = new DataOutputStream(gl_class.sock.getOutputStream());
                else data_out = new DataOutputStream(gl_class.b_sock.getOutputStream());

                data_out.writeUTF("filerecieve");
                showFileChooser();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (v.getId() == R.id.recieve){
            final globalClass gl_class = (globalClass) getApplicationContext();
            byte[] data;
            int fileSize;
            String filename;
            String extStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            String path = extStorage+"/PC Controller/";
            Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();
            File dir = new File(path);
            if(! dir.exists()) dir.mkdir();
            try {

                if(gl_class.mode) {
                    data_out = new DataOutputStream(gl_class.sock.getOutputStream());
                    data_in = new DataInputStream(gl_class.sock.getInputStream());
                }
                else {
                    data_out = new DataOutputStream(gl_class.b_sock.getOutputStream());
                    data_in = new DataInputStream(gl_class.b_sock.getInputStream());
                }
                data_out.writeUTF("filetransfer");

                fileSize = data_in.readInt();
                filename = data_in.readUTF();
                data = new byte[fileSize];
                data_in.readFully(data,0,fileSize);



                File outfile = new File(path+filename);
                Toast.makeText(getApplicationContext(),filename,Toast.LENGTH_LONG).show();
                outfile.createNewFile();

                FileOutputStream out_stream = new FileOutputStream(outfile);
                out_stream.write(data,0,fileSize);
                Toast.makeText(getApplicationContext(),"file recieved",Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
