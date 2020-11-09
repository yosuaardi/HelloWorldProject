package com.example.helloworld;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity{
    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnSign;

    DatabaseHandler dbhandler;
    protected Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        txtUsername = findViewById(R.id.txtUsernameSign);
        txtEmail = findViewById(R.id.txtEmailSign);
        txtPassword = findViewById(R.id.txtPasswordSign);
        btnSign = findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                daftar();
            }
        });
    }

    public void daftar(){
        if(txtUsername.getText().toString().equals("") || txtEmail.getText().toString().equals("") || txtPassword.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Username/Email/Password tidak boleh kosong!", Toast.LENGTH_LONG).show();
        }else {
            dbhandler = new DatabaseHandler(this);
            SQLiteDatabase db = dbhandler.getWritableDatabase();
            db.execSQL("INSERT INTO user(username, email, password) VALUES('" +
                    txtUsername.getText().toString() + "','" +
                    txtEmail.getText().toString() + "','" +
                    txtPassword.getText().toString() + "')");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("STATUS_SIGN_UP", "Berhasil");
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Sign Up Berhasil!", Toast.LENGTH_LONG).show();
        }
    }
}
