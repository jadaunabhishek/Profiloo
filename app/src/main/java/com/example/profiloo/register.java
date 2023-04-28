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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {

    // User Register Attributes
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    EditText email2, password2, confirmpassword2;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button register2;
    TextView login2;
    // User Register Attributes

    // User Data add to Firebase Attributes
    String strname, strdepartment, stremail, strpassword, strconfirmpassword, strregisternumber, strphonenumber;
    EditText name, department, registernumber, phonenumber;
    // User Data add to Firebase Attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // User Register Attributes
        email2 = findViewById(R.id.email2);
        password2 = findViewById(R.id.password2);
        confirmpassword2 = findViewById(R.id.confirmpassword2);
        register2 = findViewById(R.id.register2);
        login2 = findViewById(R.id.login2);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        // User Register Attributes

        // Taking user to login window from register window with login button
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(register.this,login.class));
            }
        });



        // User Data add to Firebase Attributes
        name = findViewById(R.id.name2);
        department = findViewById(R.id.department2);
        registernumber = findViewById(R.id.registernumber2);
        phonenumber = findViewById(R.id.phonenumber2);
        // User Data add to Firebase Attributes


        // User Register Attributes
        register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stremail = email2.getText().toString();
                strpassword = password2.getText().toString();
                strconfirmpassword = confirmpassword2.getText().toString();

                if(!stremail.matches(emailPattern)){
                    email2.setError("Enter correct Email");
                }
                else if (strpassword.isEmpty() || strpassword.length()<8){
                    password2.setError("Enter proper password");
                }
                else if (!strpassword.equals(strconfirmpassword)){
                    confirmpassword2.setError("Password not match both field");
                }
                else{

                    //if email and password are correct and passes all test cases then data will be entered in firebase and then intent to next activity

                    progressDialog.setMessage("Please wait while Registration...");
                    progressDialog.setTitle("Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(stremail,strpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                // User Data add to Firebase Attributes
                                strname = name.getText().toString();
                                strdepartment = department.getText().toString();
                                strregisternumber = registernumber.getText().toString();
                                strphonenumber = phonenumber.getText().toString();

                                //display name of user
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(strname).build();
                                mUser.updateProfile(userProfileChangeRequest);

                                readwriteuserdata writeuserdata = new readwriteuserdata(strregisternumber, strphonenumber, strdepartment);
                                DatabaseReference profilereference = FirebaseDatabase.getInstance().getReference("Students Data");
                                profilereference.child(mUser.getUid()).setValue(writeuserdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressDialog.dismiss();
                                            //take user to next activity
                                            sendUserToNextActivity();
                                            Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        }

                                        else{
                                            Toast.makeText(register.this, "Registration failed! Please try again.", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                                // User Data, add to Firebase Attributes
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(register.this, "Registration failed! Please try again."+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        // User Register Attributes

    }

    // User Register Attributes, intent to this new class
    private void sendUserToNextActivity(){
        Intent intent = new Intent(register.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    // User Register Attributes
}