package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUser;
    private EditText txtPass;
    private Button btnLogin;
    private String user = "admin";
    private String pass = "admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("success", "Testing");
        setContentView(R.layout.activity_main);
        txtUser = findViewById(R.id.txtUsername);
        txtPass = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v) {
                 cekLogin();
            }
        });
    }

    public void cekLogin(){
        if(txtUser.getText().toString().equals(user) && txtPass.getText().toString().equals(pass)){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "Username atau Password Anda tidak benar!", Toast.LENGTH_LONG).show();
        }
    }
}