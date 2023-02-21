package com.example.familybasket;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static Button button;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initButton();

        mEmailEditText = findViewById(R.id.email_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);

        button.setOnClickListener(v -> {
            String emailInput = mEmailEditText.getText().toString().trim();
            String passwordInput = mPasswordEditText.getText().toString().trim();
        });


    }

    private void initButton() {
        button = findViewById(R.id.login_button);
    }
}