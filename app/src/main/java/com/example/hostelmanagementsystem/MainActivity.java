package com.example.hostelmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button login, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         login = findViewById(R.id.login);
         register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityRegister();
            }
        });

         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivityLogin();
             }
         });
    }
    public void startActivityRegister(){
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }
    public void startActivityLogin(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}
