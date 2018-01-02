package com.example.networktest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        ArrayList<String> data = intent.getStringArrayListExtra("data");
        showResponse(data);
    }

    private void showResponse(final List<String> ResponseData){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ResponseData);
                ListView listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(adapter);
            }
        });
    }

}
