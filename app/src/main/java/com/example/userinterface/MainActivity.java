package com.example.userinterface;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etPassword;
    Button btnRegister;
    Button btnLogin;
    EditText etEmail;
    String stringEmail, stringPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = findViewById(R.id.register);
        etPassword = findViewById(R.id.password);
        etEmail = findViewById(R.id.email);
        btnLogin = findViewById(R.id.login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringEmail = etEmail.getText().toString();
                stringPassword = etPassword.getText().toString();
                String task = "login";
                BackgroundActivity backgroundTask = new BackgroundActivity(MainActivity.this);

                etEmail.setText("");
                etPassword.setText("");
                backgroundTask.execute(task,stringEmail,stringPassword);
            }
        });



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
