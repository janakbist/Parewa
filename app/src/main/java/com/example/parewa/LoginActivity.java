package com.example.parewa;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        TextView fp = (TextView) findViewById(R.id.forgot_password);
        Button login = findViewById(R.id.loginbtn);
        TextView register = (TextView) findViewById(R.id.register_button);
        fp.setPaintFlags(fp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(user.isEmpty()) {
                    email.setError("Email cannot be Empty");
                }
                if(pass.isEmpty()) {
                    password.setError("password cannot be Empty");
                }
                else{
                    mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Login Failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });

    }
}
