package com.example.bledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        setLogsMessages();
    }

    public void setLogsMessages(){
        ListView listView=(ListView)findViewById(R.id.logs_list_id);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.list_item,
                Index.logManager.messagesLogs);
        listView.setAdapter(adapter);
    }

}
