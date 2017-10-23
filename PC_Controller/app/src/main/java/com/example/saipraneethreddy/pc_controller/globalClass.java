package com.example.saipraneethreddy.pc_controller;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

import java.net.Socket;

/**
 * Created by sai praneeth reddy on 15-10-2017.
 */

public class globalClass extends Application{
    public BluetoothSocket b_sock;
    public Socket sock;
    public boolean mode;
}
