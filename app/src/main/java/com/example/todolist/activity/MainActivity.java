package com.example.todolist.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todolist.R;
import com.example.todolist.adapter.TasksAdapter;
import com.example.todolist.database.DatabaseHelper;
import com.example.todolist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;
    ArrayList<String> task_id, task_name, task_desc, task_date;
    TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.todolist.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#03A9F4")));

        binding.fabAddTask.setOnClickListener(this);
        createObjectTask();

        tasksAdapter = new TasksAdapter(MainActivity.this, this, task_id, task_name, task_desc, task_date);
        binding.rvTask.setAdapter(tasksAdapter);
        binding.rvTask.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvTask.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    private void createObjectTask() {
        databaseHelper = new DatabaseHelper(MainActivity.this);
        task_id = new ArrayList<>();
        task_name = new ArrayList<>();
        task_desc = new ArrayList<>();
        task_date = new ArrayList<>();

        storeDataInArrays();
    }

    private void storeDataInArrays() {
        Cursor cursor = databaseHelper.getAllTask();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_name.add(cursor.getString(1));
                task_desc.add(cursor.getString(2));
                task_date.add(cursor.getString(3));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabAddTask) {
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivity(intent);
        }
    }
}