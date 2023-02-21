package com.example.familybasket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class RegisterActivity extends Activity {

    private static Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initButton();
    }

    private void initButton() {
        button = findViewById(R.id.login_button);
    }

}
