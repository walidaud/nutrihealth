package com.example.nutrihealth.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutrihealth.Database.Dish;
import com.example.nutrihealth.Database.User;
import com.example.nutrihealth.Dish.activity_main_menu;
import com.example.nutrihealth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_register extends AppCompatActivity {

    EditText signUpMail, signUpPass, firstName, lastName, phoneNumber;
    Button signUpButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference users;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        signUpMail = findViewById(R.id.signUpMail);
        signUpPass = findViewById(R.id.signUpPass);
        signUpButton = findViewById(R.id.signUpButton);
        final ProgressDialog loadingBar = new ProgressDialog(this);

        //Initialize FirebaseAuth Instance
        mAuth = FirebaseAuth.getInstance();
        //Retrieve an instance of the database we are writing to
        db = FirebaseDatabase.getInstance();
        //Get reference of the location we are writing to
        users = db.getReference("Users");

        //Add an on click listener to the sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            //When the button is clicked, the following is executed
            public void onClick(View view) {
                final String fstName = firstName.getText().toString();
                final String lstName = lastName.getText().toString();
                final String phoneNo = phoneNumber.getText().toString();
                final String email = signUpMail.getText().toString();
                final String pass = signUpPass.getText().toString();

                //Toast popup notification if the user leaves any field empty
                if (TextUtils.isEmpty(fstName) && TextUtils.isEmpty(lstName) && TextUtils.isEmpty(email)
                        && TextUtils.isEmpty(phoneNo) && TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Fill in all the details", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadingBar.setTitle("User Registration");
                loadingBar.setMessage("Please wait while we create an account for you");
                loadingBar.show();

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(activity_register.this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(activity_register.this, "ERROR", Toast.LENGTH_LONG).show();
                                } else {
                                    //Create a new object from the User.java class
                                    User user = new User();
                                    //Setting the values of the variables
                                    user.setFirstName(fstName);
                                    user.setLastName(lstName);
                                    user.setPhoneNumber(phoneNo);
                                    user.setEmail(email);
                                    user.setPassword(pass);

                                    //get current user ID of the user
                                    //set it as the primary key
                                    //add the values of user object to the database
                                    users.child(mAuth.getCurrentUser().getUid()).setValue(user);

                                    //Once successful it goes to navigation activity
                                    startActivity(new Intent(activity_register.this, activity_main_menu.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}