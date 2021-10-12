package com.example.todolist.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.R;
import com.example.todolist.database.UsersDatabaseHelper;
import com.example.todolist.databinding.ActivityRegisterBinding;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegisterBinding binding;
    private String _etFullName, _etEmail, _etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        binding.mbRegister.setOnClickListener(this);
        binding.mbLoginInRegister.setOnClickListener(this);
    }

    private void bindingWidget() {
        _etFullName = binding.etNamaLengkap.getText().toString();
        _etEmail = binding.etEmail.getText().toString();
        _etPassword = binding.etPassword.getText().toString();
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

    private void onClickBtnRegister() {
        bindingWidget();
        if(_etFullName.equals("") || _etEmail.equals("") || _etPassword.equals("")) {
            Toast.makeText(this, "Form must be filled", Toast.LENGTH_SHORT).show();
        } else {
            UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(this);
            String emailInDb = usersDatabaseHelper.getEmailUser(_etEmail);
            if (_etEmail.trim().equals(emailInDb)) {
                Toast.makeText(this, "the email already registered", Toast.LENGTH_SHORT).show();
            } else {
                usersDatabaseHelper.addUser(_etFullName, _etEmail, _etPassword);
                finish();
                resetEditText();
            }
        }
    }

    private void resetEditText() {
        binding.etNamaLengkap.setText("");
        binding.etEmail.setText("");
        binding.etPassword.setText("");
    }

}