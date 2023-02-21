package com.example.familybasket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pages.MainActivity;

public class ChooseLoginOption extends AppCompatActivity {

    private Button signUpButton;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_option);
        getSupportActionBar().hide();
        initSignUpButton();
        initLoginButton();

    }

    public void initSignUpButton() {
        signUpButton = findViewById(R.id.sign_up_button);
    }

    public void initLoginButton() {
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(ChooseLoginOption.this, LoginActivity.class));
               }
           }
        );
    }
}