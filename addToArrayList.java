package com.example.todo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

public class addToArrayList extends Service {

    private final IBinder mBinder = new arrayListBinder();
    private ArrayList<String> toDo = new ArrayList<String>();

    public addToArrayList() {}

    public class arrayListBinder extends Binder {
        addToArrayList getService() {
            return addToArrayList.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public void addToArrayList(String item) {
        toDo.add(0, item);
        System.out.println(toDo);
    }

    public ArrayList<String> getToDo() {
        return toDo;
    }

}
