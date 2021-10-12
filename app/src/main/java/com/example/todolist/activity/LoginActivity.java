package com.example.todolist.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.R;
import com.example.todolist.database.UsersDatabaseHelper;
import com.example.todolist.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private UsersDatabaseHelper usersDatabaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        resetEditText();

        usersDatabaseHelper = new UsersDatabaseHelper(this);
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
        checkLogin();

        binding.mbLogin.setOnClickListener(this);
        binding.mbRegisterInLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetEditText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.mbLogin):
                onClickBtnLogin();
                break;
            case (R.id.mbRegisterInLogin):
                onCLickBtnRegisterInLogin();
                break;
        }
    }

    private void onCLickBtnRegisterInLogin() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void onClickBtnLogin() {
        if (binding.etEmail.getText().toString().equals("") || binding.etPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Form must be filled", Toast.LENGTH_SHORT).show();
        } else {
            boolean isUserInDB = usersDatabaseHelper.checkLogin(
                    binding.etEmail.getText().toString().trim(),
                    binding.etPassword.getText().toString().trim()
            );
            if (isUserInDB) {
                saveLoginSession(binding.etEmail.getText().toString().trim(), binding.etPassword.getText().toString().trim());
                Toast.makeText(this, "Sign In successfully", Toast.LENGTH_SHORT).show();
                intentToMain(binding.etEmail.getText().toString().trim(), binding.etPassword.getText().toString().trim());
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void saveLoginSession(String _email, String _password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_LOGIN", true);
        editor.putString("EMAIL", _email);
        editor.putString("PASSWORD", _password);
        editor.apply();
    }

    private void checkLogin() {
        if (this.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private boolean isLoggedIn(){
        return this.sharedPreferences.getBoolean("IS_LOGIN", false);
    }

    private void intentToMain(String _email, String _password) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("EMAIL", _email);
        intent.putExtra("PASSWORD", _password);
        startActivity(intent);
    }

    private void resetEditText() {
        binding.etEmail.setText("");
        binding.etPassword.setText("");
    }
}