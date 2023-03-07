package com.example.familybasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pages.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends Activity {
    private EditText editTextEmail, editTextPassword;
    private Button buttonReg;
    private FirebaseAuth firebase;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebase = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.reg_email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        textView = findViewById(R.id.loginNow);

        textView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
        buttonReg.setOnClickListener(view -> {
            String emailInput = String.valueOf(editTextEmail.getText());
            String passwordInput = String.valueOf(editTextPassword.getText());
            showErrorMessage(emailInput, passwordInput);
            loginIntoTheMainActivity(emailInput, passwordInput, firebase);
        });
    }


    private void showErrorMessage(String emailInput, String passwordInput) {
        if (TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput)) {
            Toast.makeText(RegisterActivity.this, "Enter your Email",
                    Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(emailInput)) {
            Toast.makeText(RegisterActivity.this, "Enter your Password",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void loginIntoTheMainActivity(String emailInput, String passwordInput, FirebaseAuth auth) {

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getApplicationContext(), "Register Successful",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterActivity.this, "Register" +
                                        " Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
