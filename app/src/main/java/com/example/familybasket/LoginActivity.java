package com.example.familybasket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class LoginActivity extends AppCompatActivity {
    private static Button button;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private static final String appID = "familybasket-mzzoj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initButton();
        Realm.init(this);
        AppConfiguration config = new AppConfiguration.Builder(appID).build();
        App app = new App(config);

        mEmailEditText = findViewById(R.id.email_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);

        button.setOnClickListener( v -> {
            String  emailInput = mEmailEditText.getText().toString().trim();
            String passwordInput = mPasswordEditText.getText().toString().trim();
            app.getEmailPassword().registerUserAsync(emailInput, passwordInput, it -> {
                if (it.isSuccess()) {
                    Log.i("AUTH", "Successfully registered user.");
                } else {
                    Log.e("AUTH", "Failed to register user: " + it.getError().getErrorMessage());
                }
            });
        });


    }

    private void initButton() {
        button = findViewById(R.id.login_button);
    }
}