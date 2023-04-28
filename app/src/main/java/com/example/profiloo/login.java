 package com.example.profiloo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    // User Login Attributes
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText email1, password1;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button login1;
    TextView register1;
    // User Login Attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Login Attributes
        email1 = findViewById(R.id.email1);
        password1 = findViewById(R.id.password1);
        login1 = findViewById(R.id.login1);
        register1 = findViewById(R.id.register1);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        // User Login Attributes


        //taking user to register window from login window using register button
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, register.class));
            }
        });


        // User Login Attributes
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email1.getText().toString();
                String password = password1.getText().toString();

                if(!email.matches(emailPattern)){
                    email1.setError("Enter correct Email");
                }
                else if (password.isEmpty() || password.length()<8){
                    password1.setError("Enter proper password");
                }
                else{
                    progressDialog.setMessage("Please wait while Registration...");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();

                                sendUserToNextActivity();
                                //Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                // commented that upper line because in profile section already added this comment so we don't require this twice therefore commented
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(login.this, "Login Unsuccessful"+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        // User Login Attributes
    }

    // User Login Attributes
    private void sendUserToNextActivity(){
        Intent intent = new Intent(login.this, profile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    // User Login Attributes
}