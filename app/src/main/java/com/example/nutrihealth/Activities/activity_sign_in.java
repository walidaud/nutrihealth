package com.example.nutrihealth.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutrihealth.Dish.activity_main_menu;
import com.example.nutrihealth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_sign_in extends AppCompatActivity {

    private EditText SignInMail, SignInPass;
    private FirebaseAuth mAuth;
    private Button SignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize FirebaseAuth Instance
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_in);
        SignInMail = findViewById(R.id.SignInMail);
        SignInPass = findViewById(R.id.SignInPass);
        SignInButton = findViewById(R.id.SignInButton);
        final ProgressDialog loadingBar = new ProgressDialog(this);

        // Add an on click listener to the sign in button
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // When the button is clicked, the following is executed
            public void onClick(View view) {
                // Get the string of text input from user in the email and password field
                final String email = SignInMail.getText().toString();
                final String password = SignInPass.getText().toString();

                // Toast popup notification if the user leaves the email and password field empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your mail address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadingBar.setTitle("User Login");
                loadingBar.setMessage("Please wait, checking credentials");
                loadingBar.show();

                // Authenticate the user by taking in email and password as arguments and validating them
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity_sign_in.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    // Toast to notify the user that there was an error
                                    if (password.length() < 8) {
                                        Toast.makeText(getApplicationContext(),
                                                "Password must be more than 8 digit",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Error",Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If the user is successfully validated, the navigation activity appears
                                    Intent intent = new Intent(activity_sign_in.this, activity_main_menu.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }


    // If the user is not signed up, clicking this would take the user to the sign up activity
    public void GotToSignUp(View view) {
        Intent intent = new Intent(this, activity_register.class);
        startActivity(intent);
    }
}