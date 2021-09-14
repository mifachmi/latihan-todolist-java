package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TasksAdapter extends ArrayAdapter<Tasks> {

    public TasksAdapter(Context context, ArrayList<Tasks> tasks) {
        super(context, R.layout.task_list, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Tasks tasks = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list, parent, false);
        }

        // Lookup view for data population
        TextView tvNametask = convertView.findViewById(R.id.tvNamatask);
        TextView tvDescTask = convertView.findViewById(R.id.tvDescription);
        TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);

        // Populate the data into the template view using the data object
        tvNametask.setText(tasks.nameTask[position]);
        tvDescTask.setText(tasks.descriptionTask[position]);
        tvDueDate.setText(tasks.dueDateTask[position]);

        // Return the completed view to render on screen
        return convertView;
    }
}
