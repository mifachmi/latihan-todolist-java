package com.example.todolist.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
    String id;
    String task_name;
    String task_desc;
    String task_date;
    int task_user_id;
    String _idTask, _nameTask, _descTask, _dueDateTask, _saveTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        checkIntentData();
        task_user_id = getIntent().getIntExtra("user_id", 0);
        Log.d("TAG Add", String.valueOf(task_user_id));
        binding.tvIdUserTask.setText(String.valueOf(task_user_id));

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
//            task_user_id = getIntent().getIntExtra("user_id", 0);

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

    private void getTextFromWidget() {
        _idTask = binding.tvIdTask.getText().toString();
        _nameTask = binding.etNameTask.getText().toString();
        _descTask = binding.etDescription.getText().toString();
        _dueDateTask = binding.etDueDate.getText().toString();
        _saveTaskBtn = (String) binding.btnSaveTask.getText();
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

        // Set text dari textview
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
        getTextFromWidget();
        if (_nameTask.equals("") || _descTask.equals("") || _dueDateTask.equals("")) {
            Toast.makeText(this, "Form must be filled", Toast.LENGTH_SHORT).show();
        } else if (_saveTaskBtn.equals(String.valueOf(R.string.save)) || _idTask.equals("")) {
            databaseHelper = new DatabaseHelper(AddTaskActivity.this);
            databaseHelper.addTask(_nameTask, _descTask, _dueDateTask, task_user_id);
            finish();
        } else {
            databaseHelper = new DatabaseHelper(this);
            databaseHelper.updateData(_idTask, _nameTask, _descTask, _dueDateTask);
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