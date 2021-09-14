package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String[] dataTask = {"Nama Task","Nama Task","Nama Task","Nama Task","Nama Task","Nama Task","Nama Task","Nama Task","Nama Task","Nama Task"};
    private String[] desc = {"Description","Description","Description","Description","Description","Description","Description","Description","Description","Description" };
    private String[] dueDate = {"Due date","Due date","Due date","Due date","Due date","Due date","Due date","Due date","Due date","Due date" };

//    private String dataTask = "Nama Task";
//    private String desc = "Description";
//    private String dueDate = "Due date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));

//        ListView listView = findViewById(R.id.lvTask);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_2, android.R.id.text1, dataTask);
//        listView.setAdapter(adapter);

        // Construct the data source
        ArrayList<Tasks> arrayOfTasks = new ArrayList<>();

        // Create the adapter to convert the array to views
        TasksAdapter adapter = new TasksAdapter(MainActivity.this, arrayOfTasks);

        // Attach the adapter to a ListView
        ListView listView = findViewById(R.id.lvTask);
        listView.setAdapter(adapter);

        // Add item to adapter
//        Tasks newTask = new Tasks(dataTask, desc, dueDate);
//        arrayOfTasks.add(newTask);

        for (int i=0; i<dataTask.length; i++) {
            Tasks newTask = new Tasks(dataTask, desc, dueDate);
            arrayOfTasks.add(newTask);
        }

//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(this, arrayOfTasks, Toast.LENGTH_SHORT).show();
//            }
//        });

        FloatingActionButton fabPlus = findViewById(R.id.fab);
        fabPlus.setOnClickListener(
                v -> Toast.makeText(getBaseContext(), "Ini FAB", Toast.LENGTH_SHORT).show());
    }
}