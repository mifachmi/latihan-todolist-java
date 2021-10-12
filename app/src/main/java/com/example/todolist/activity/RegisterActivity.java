package com.example.todolist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.database.UsersDatabaseHelper;
import com.example.todolist.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterBinding binding;
    private EditText _etFullName, _etEmail, _etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        bindingWidget();

        binding.mbRegister.setOnClickListener(this);
        binding.mbLoginInRegister.setOnClickListener(this);
    }

    private void bindingWidget() {
        _etFullName = binding.etNamaLengkap;
        _etEmail = binding.etEmail;
        _etPassword = binding.etPassword;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.mbRegister):
                onClickBtnRegister();
                break;
            case (R.id.mbLoginInRegister):
                onClickBtnLoginInRegister();
                break;
        }
    }

    private void onClickBtnLoginInRegister() {
        finish();
    }

//    private void onClickBtnRegister() {
//        if(_etFullName.getText().toString().equals("") ||
//                _etEmail.getText().toString().equals("") ||
//                _etPassword.getText().toString().equals("")) {
//            Toast.makeText(this, "Form must be filled", Toast.LENGTH_SHORT).show();
//        } else {
//            UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(this);
//            usersDatabaseHelper.addUser(
//                    binding.etNamaLengkap.getText().toString(),
//                    binding.etEmail.getText().toString(),
//                    binding.etPassword.getText().toString()
//            );
//            finish();
//            resetEditText();
//        }
//    }
//
//    private void resetEditText() {
//        binding.etNamaLengkap.setText("");
//        binding.etEmail.setText("");
//        binding.etPassword.setText("");
//    }

    private void onClickBtnRegister() {
        if(binding.etNamaLengkap.getText().toString().equals("") ||
                binding.etEmail.getText().toString().equals("") ||
                binding.etPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Form must be filled", Toast.LENGTH_SHORT).show();
        } else {
            UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(this);
            usersDatabaseHelper.addUser(
                    binding.etNamaLengkap.getText().toString(),
                    binding.etEmail.getText().toString(),
                    binding.etPassword.getText().toString()
            );
            finish();
            resetEditText();
        }
    }

    private void resetEditText() {
        binding.etNamaLengkap.setText("");
        binding.etEmail.setText("");
        binding.etPassword.setText("");
    }
}