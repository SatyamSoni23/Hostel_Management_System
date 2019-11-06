package com.example.hostelmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {
    Button register;
    EditText username, password, cnfPassword;
    private FirebaseAuth mAuth;
    DatabaseReference rootRef, demoRef, demoRef1;
    public static String strUsername, strPassword, strRePassword, strNewUsername;
    ProgressDialog nDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        cnfPassword = findViewById(R.id.confirmPassword);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("admin");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nDialog = new ProgressDialog(register.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                strUsername = username.getText().toString();
                strPassword = password.getText().toString();
                strRePassword = cnfPassword.getText().toString();
                strNewUsername = strUsername.replaceAll("[@.]","");
                if(strUsername.isEmpty()){
                    Toast.makeText(register.this, "Enter email", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(!(strUsername.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+") || strUsername.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"))){
                    Toast.makeText(register.this, "Enter valid email",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                if(strPassword.isEmpty() || strRePassword.isEmpty()){
                    Toast.makeText(register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(strRePassword.length() < 6 || strPassword.length() < 6){
                    Toast.makeText(register.this, "Password length should be greater then 6", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(!strRePassword.equals(strPassword)){
                    Toast.makeText(register.this, "Password and confirm password must be same", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(strNewUsername).exists()){
                            Toast.makeText(register.this, "Username already registered",Toast.LENGTH_LONG).show();
                            nDialog.dismiss();
                        }
                        else{
                            mAuth.createUserWithEmailAndPassword(strUsername, strPassword)
                                    .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                sendVerificationEmail();
                                                demoRef1 = demoRef.child(strNewUsername);
                                                demoRef1.child("email").setValue(strNewUsername);

                                            } else {
                                                startOfflineActivity();
                                            }
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void startOfflineActivity(){
        Intent intent = new Intent(this, offline.class);
        startActivity(intent);
        nDialog.dismiss();
    }
    private void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(register.this, successRegistration.class));
                    nDialog.dismiss();
                    finish();
                }
                else{
                    Toast.makeText(register.this, "Signup failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onBackPressed() {
        startLoginActivity();
    }
    public void startLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

