package com.example.familybasket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;

public class RegisterActivity extends Activity {

    private static Button button;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private static final String appID = "familybasket-mzzoj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initButton();
        Realm.init(this);
        AppConfiguration config = new AppConfiguration.Builder(appID).build();
        App app = new App(config);

        mEmailEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);

        button.setOnClickListener( v -> {
            String  emailInput = mEmailEditText.getText().toString().trim();
            String passwordInput = mPasswordEditText.getText().toString().trim();
            app.getEmailPassword().registerUserAsync(emailInput, passwordInput, it -> {
                if (it.isSuccess()) {
                    Log.i("EXAMPLE", "Successfully registered user.");
                } else {
                    Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                }
            });
        });


    }

    private void initButton() {
        button = findViewById(R.id.login_button);
    }

}
