package com.example.familybasket;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pages.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button button;

    private Button directToRegister;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initButton();

        mEmailEditText = findViewById(R.id.email_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        directToRegister = findViewById(R.id.link_signup);
        directUserToTheRegisterActivity(directToRegister);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButtonFunctionality();
    }

    public void loginButtonFunctionality() {

        button.setOnClickListener(view -> {
                    String emailInput = mEmailEditText.getText().toString().trim();
                    String passwordInput = mPasswordEditText.getText().toString().trim();

                    showErrorMessage(emailInput, passwordInput);
                    loginIntoTheMainActivity(emailInput, passwordInput, firebaseAuth);
                }
        );

    }

    private void showErrorMessage(String emailInput, String passwordInput) {
        if (TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput)) {
            Toast.makeText(LoginActivity.this, "Enter your Email",
                    Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(emailInput)) {
            Toast.makeText(LoginActivity.this, "Enter your Password",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void loginIntoTheMainActivity(String emailInput, String passwordInput, FirebaseAuth auth) {

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getApplicationContext(), "Login Successful",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Login Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void directUserToTheRegisterActivity (Button button) {

        button.setOnClickListener(view -> startActivity(new Intent
                (LoginActivity.this, RegisterActivity.class)));
    }


    private void initButton() {
        button = findViewById(R.id.login_button);
    }
}