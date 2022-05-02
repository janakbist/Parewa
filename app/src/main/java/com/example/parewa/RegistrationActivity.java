package com.example.parewa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    //Defining variables
    private EditText email, password, firstname,lastname;
    private RadioButton male,female,others;
    private CheckBox chktc;
    private Button mRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private TextView backtologin;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Getting id of the xml views

        setContentView(R.layout.activity_registration);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        male =findViewById(R.id.male);
        female = findViewById(R.id.female);
        others = findViewById(R.id.other);
        chktc = findViewById(R.id.terms_conditions);
        backtologin = findViewById(R.id.backtologin);
        mRegister = findViewById(R.id.signupbtn);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Register");


        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting values from xml documnet uisng thier ids

                String emaill = email.getText().toString().trim();
                String fn = firstname.getText().toString().trim();
                String ln = lastname.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String id = mAuth.getCurrentUser().getUid();
                String gender = "";
                if(male.isChecked()) {
                    gender = "Male";
                }
                if(female.isChecked()) {
                    gender = "Female";
                }
                if(others.isChecked()) {
                    gender = "Others";
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emaill).matches()) {
                    email.setError("Invalid Email");
                    email.setFocusable(true);
                } else if (pass.length() < 6) {
                    password.setError("Length Must be greater than 6 character");
                    password.setFocusable(true);
                } else if(gender.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    password.setFocusable(true);
                }else if(!chktc.isChecked()) {
                    Toast.makeText(RegistrationActivity.this, "Please Read and Accept terms and Conditions", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Write a message to the database

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users");
                    myRef.child(id).child("Firstname").setValue(fn);
                    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef1 = database1.getReference("Users");
                    myRef1.child(id).child("Lastname").setValue(ln);
                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef2 = database2.getReference("Users");
                    myRef2.child(id).child("Email").setValue(emaill);
                    FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef3 = database3.getReference("Users");
                    myRef3.child(id).child("Gender").setValue(gender);

                    mAuth.createUserWithEmailAndPassword(emaill,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                progressDialog.show();
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                finish();
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "User Registration Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}