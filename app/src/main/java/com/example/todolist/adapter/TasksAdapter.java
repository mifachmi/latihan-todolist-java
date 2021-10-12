package com.example.todolist.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.example.todolist.activity.AddTaskActivity;
import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id_task, task_name, task_date, task_desc;

    public TasksAdapter(Activity activity, Context context, ArrayList id, ArrayList name, ArrayList desc, ArrayList date) {
        this.activity = activity;
        this.context = context;
        this.id_task = id;
        this.task_name = name;
        this.task_desc = desc;
        this.task_date = date;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView task_id, task_name, task_desc, task_date;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task_id = itemView.findViewById(R.id.tvIdTask);
            task_name = itemView.findViewById(R.id.tvNamatask);
            task_desc = itemView.findViewById(R.id.tvDescription);
            task_date = itemView.findViewById(R.id.tvDueDate);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.task_list, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.TaskViewHolder holder, int position) {
        holder.task_id.setText(id_task.get(position).toString());
        holder.task_name.setText(task_name.get(position).toString());
        holder.task_desc.setText(task_desc.get(position).toString());
        holder.task_date.setText(task_date.get(position).toString());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddTaskActivity.class);
            intent.putExtra("id", String.valueOf(id_task.get(position)));
            intent.putExtra("name", String.valueOf(task_name.get(position)));
            intent.putExtra("desc", String.valueOf(task_desc.get(position)));
            intent.putExtra("date", String.valueOf(task_date.get(position)));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return id_task.size();
    }

}
