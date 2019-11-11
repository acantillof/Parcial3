package com.example.bledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bledemo.adapters.BluetoothDeviceListAdapter;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        setLogsMessages();
    }

    public void setLogsMessages(){
        ListView listView=(ListView)findViewById(R.id.logs_list_id);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.log_list_item,
                MainActivity.logManager.messagesLogs);
        listView.setAdapter(adapter);
    }

}
