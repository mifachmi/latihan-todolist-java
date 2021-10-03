package com.example.todolist.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.database.DatabaseHelper;
import com.example.todolist.databinding.ActivityAddTaskBinding;
import com.example.todolist.fragment.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener {

    private ActivityAddTaskBinding binding;
    private DatabaseHelper databaseHelper;
    String id, task_name, task_desc, task_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        checkIntentData();

        binding.btnSaveTask.setOnClickListener(this);
        binding.btnCancelTask.setOnClickListener(this);
        binding.btnPickDate.setOnClickListener(this);
        binding.constraintLayout.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void checkIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("desc") && getIntent().hasExtra("date")) {
            id = getIntent().getStringExtra("id");
            task_name = getIntent().getStringExtra("name");
            task_desc = getIntent().getStringExtra("desc");
            task_date = getIntent().getStringExtra("date");

            binding.tvIdTask.setText(id);
            binding.etNameTask.setText(task_name);
            binding.etDescription.setText(task_desc);
            binding.etDueDate.setText(task_date);

            binding.btnSaveTask.setText("UPDATE");
            binding.btnSaveTask.setOnClickListener(v -> onClickBtnSave());

            binding.btnCancelTask.setText("DELETE");
            binding.btnCancelTask.setOnClickListener(v -> onClickBtnCancel());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnSaveTask):
                onClickBtnSave();
                break;
            case (R.id.btnCancelTask):
                onClickBtnCancel();
                break;
            case (R.id.btnPickDate):
                onClickBtnPickDate();
                break;
            case (R.id.constraintLayout):
                onClickContraintLayout(v);
                break;
        }
    }

    private void onClickContraintLayout(View v) {
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void onClickBtnPickDate() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        // Siapkan date formatter-nya terlebih dahulu
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Set text dari textview once
        binding.etDueDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void onClickBtnCancel() {
        if (!binding.btnCancelTask.getText().equals(String.valueOf(R.string.cancel))
                && !binding.tvIdTask.getText().equals("")) {
            databaseHelper = new DatabaseHelper(this);
            databaseHelper.deleteData(binding.tvIdTask.getText().toString());
        }
        finish();
    }

    private void onClickBtnSave() {
        if (binding.etNameTask.getText().toString().equals("")
                || binding.etDescription.getText().toString().equals("")
                || binding.etDueDate.getText().toString().equals("")) {
            Toast.makeText(this, "Form must be filled", Toast.LENGTH_SHORT).show();
        } else if (binding.btnSaveTask.getText().equals(String.valueOf(R.string.save))
                || binding.tvIdTask.getText().equals("")) {
            databaseHelper = new DatabaseHelper(AddTaskActivity.this);
            databaseHelper.addTask(
                    binding.etNameTask.getText().toString(),
                    binding.etDescription.getText().toString(),
                    binding.etDueDate.getText().toString());
            finish();
        } else {
            databaseHelper = new DatabaseHelper(this);
            databaseHelper.updateData(
                    binding.tvIdTask.getText().toString(),
                    binding.etNameTask.getText().toString(),
                    binding.etDescription.getText().toString(),
                    binding.etDueDate.getText().toString());
            finish();
        }
        resetEditText();
    }

    private void resetEditText() {
        binding.etNameTask.setText("");
        binding.etDescription.setText("");
        binding.etDueDate.setText("");
    }
}