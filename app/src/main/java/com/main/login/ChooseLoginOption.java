package com.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familybasket.R;

import java.util.Objects;

public class ChooseLoginOption extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_option);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initSignUpButton();
        initLoginButton();

    }

    public void initSignUpButton() {
        Button signUpButton = findViewById(R.id.sign_up_button);

        signUpButton.setOnClickListener(view -> startActivity(new Intent
                (ChooseLoginOption.this, RegisterActivity.class)));
    }

    public void initLoginButton() {
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(view -> startActivity(new Intent(ChooseLoginOption.this,
                LoginActivity.class))
        );
    }
}