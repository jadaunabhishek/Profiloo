package com.example.profiloo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    //retrieve user data

    TextView TextEmail, TextName, TextDepartment, TextPhoneNumber, TextRegisterNumber;
    String useremail, username, userdepartment, userphonenumber, userregisternumber;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    FirebaseAuth authprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextEmail = findViewById(R.id.email3);
        TextName = findViewById(R.id.name3);
        TextDepartment = findViewById(R.id.department3);
        TextPhoneNumber = findViewById(R.id.phonenumber3);
        TextRegisterNumber = findViewById(R.id.registernumber3);

        progressDialog = new ProgressDialog(this);
        authprofile = FirebaseAuth.getInstance();
        firebaseUser = authprofile.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(this, "Something went wrong! User's details are not available!", Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setMessage("Please wait..");
            progressDialog.setTitle("Updating Profile");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        DatabaseReference referenceprofile = FirebaseDatabase.getInstance().getReference("Students Data");
        referenceprofile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readwriteuserdata readuserdata = snapshot.getValue(readwriteuserdata.class);
                if (readuserdata != null){

                    Toast.makeText(profile.this, "Successfully logged in with user details!", Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();

                    useremail = firebaseUser.getEmail();
                    username = firebaseUser.getDisplayName();
                    userdepartment = readuserdata.Department;
                    userphonenumber = readuserdata.PhoneNumber;
                    userregisternumber = readuserdata.RegisterNumber;


                    TextName.setText(username);
                    TextEmail.setText(useremail);
                    TextDepartment.setText(userdepartment);
                    TextPhoneNumber.setText(userphonenumber);
                    TextRegisterNumber.setText(userregisternumber);

                }
                else{

                    progressDialog.dismiss();

                    Toast.makeText(profile.this, "Something went wrong! User's details are not synced to firebase!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(profile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //retrieve user data
}