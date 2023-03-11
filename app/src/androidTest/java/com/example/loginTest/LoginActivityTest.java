package com.example.loginTest;

import static android.app.PendingIntent.getActivity;
import static android.service.autofill.Validators.not;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.familybasket.LoginActivity;
import com.example.familybasket.R;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    private Button button;
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth firebaseAuth;

    @Before
    public void setUp() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            button = activity.findViewById(R.id.login_button);
            emailEditText = activity.findViewById(R.id.email_edit_text);
            passwordEditText = activity.findViewById(R.id.password_edit_text);
            firebaseAuth = FirebaseAuth.getInstance();
        });
    }

    @Test
    public void testLoginButtonFunctionality_withValidInputs() {
        ActivityScenario.launch(LoginActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.email_edit_text)).perform(ViewActions.typeText("validemail@test.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password_edit_text)).perform(ViewActions.typeText("validpassword"));
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());
        assertNotNull(firebaseAuth.getCurrentUser());
    }
}


