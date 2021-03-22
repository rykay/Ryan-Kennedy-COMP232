package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

//declare
    ListView myListView;
    EditText myEditText;
    com.example.todo.addToArrayList addToArrayList;
    boolean arrayListBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myListView = (ListView) findViewById(R.id.myListView);
        myEditText = (EditText) findViewById(R.id.myEditText);

        final ArrayAdapter<String> arrayAdapter;
        //Creates service
        addToArrayList = new addToArrayList();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, addToArrayList.getToDo());

        myListView.setAdapter(arrayAdapter);

        myEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        String item = myEditText.getText().toString();
                        addToArrayList.addToArrayList(item);
                        //Array adapter just clears whatever is on it now and readds everything just after adding the item to the arraylist in the service
                        arrayAdapter.clear();
                        arrayAdapter.addAll(addToArrayList.getToDo());
                        myEditText.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, com.example.todo.addToArrayList.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(arrayListBound) {
            unbindService(serviceConnection);
            arrayListBound = false;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            com.example.todo.addToArrayList.arrayListBinder factBinder = (com.example.todo.addToArrayList.arrayListBinder) service;
            addToArrayList = factBinder.getService();
            arrayListBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}