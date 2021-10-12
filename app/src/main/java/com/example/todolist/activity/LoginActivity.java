package com.example.todolist.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.database.UsersDatabaseHelper;
import com.example.todolist.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private UsersDatabaseHelper usersDatabaseHelper;
    private SharedPreferences sharedPreferences;
    private String _email, _password;
    private int user_id;

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

    private void getTextFromWidget() {
        _email = binding.etEmail.getText().toString();
        _password = binding.etPassword.getText().toString();
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
        getTextFromWidget();
        if (_email.equals("") || _password.equals("")) {
            Toast.makeText(this, "Form must be filled", Toast.LENGTH_SHORT).show();
        } else {
            boolean isUserInDB = usersDatabaseHelper.checkLogin(
                    _email.trim(),
                    _password.trim()
            );
            if (isUserInDB) {
                user_id = usersDatabaseHelper.getIdUser(_email.trim(), _password.trim());
                saveLoginSession(user_id, _email.trim(), _password.trim());
                Toast.makeText(this, "Sign In successfully", Toast.LENGTH_SHORT).show();
                intentToMain(_email.trim(), _password.trim());
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void saveLoginSession(int _userId, String _email, String _password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_LOGIN", true);
        editor.putInt("USER_ID", _userId);
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

    private boolean isLoggedIn() {
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