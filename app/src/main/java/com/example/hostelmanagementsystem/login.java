package com.example.hostelmanagementsystem;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

public class login extends AppCompatActivity {
    DatabaseReference rootRef, demoRef,demoRef1;
    private FirebaseAuth mAuth;
    Button login;
    TextView forgotPassword;
    EditText username, password;
    public static String strUsername,strNewUsername, strPassword;
    ProgressDialog nDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("admin");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nDialog = new ProgressDialog(login.this);
                nDialog.setMessage("Loading..");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(true);
                nDialog.show();
                strNewUsername = username.getText().toString();
                strPassword = password.getText().toString();
                strUsername = strNewUsername.replaceAll("[@.]","");
                startActivityLogin();
                if(strNewUsername.isEmpty()){
                    Toast.makeText(login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(strPassword.isEmpty()){
                    Toast.makeText(login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    nDialog.dismiss();
                    return;
                }
                if(!(strNewUsername.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+") || strNewUsername.matches("[a-zA-Z0-9.]+@[a-z]+\\.+[a-z]+\\.+[a-z]+"))){
                    Toast.makeText(login.this, "Enter valid email",Toast.LENGTH_LONG).show();
                    nDialog.dismiss();
                    return;
                }
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(strUsername).exists())
                        {
                            mAuth.signInWithEmailAndPassword(strNewUsername, strPassword).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        if (user != null) {
                                            startActivityLogin();
                                        }
                                    }
                                    else{
                                        startWrongActivity();
                                    }
                                }
                            });
                        }
                        else{
                            startWrongActivity();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        startErrorActivity();;
                    }
                });
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForgotPasswordActivity();
            }
        });
    }
    public void startActivityLogin(){
        Intent intent = new Intent(this, adminHome.class);
        File myDir = new File(android.os.Environment.getExternalStorageDirectory().toString(), "E-Receipt");
        if(myDir.exists()){
            startActivity(intent);
        }
        else{
            myDir.mkdir();
            startActivity(intent);
        }
    }
    public void startWrongActivity(){
        Intent intent = new Intent(this, wrg.class);
        startActivity(intent);
        nDialog.dismiss();
    }
    public void startErrorActivity(){
        Intent intent = new Intent(this, somethingWentWrong.class);
        startActivity(intent);
        nDialog.dismiss();
    }
    public void startForgotPasswordActivity(){
        Intent intent = new Intent(this, forgotPassword.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        startLoginActivity();
    }
    public void startLoginActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
