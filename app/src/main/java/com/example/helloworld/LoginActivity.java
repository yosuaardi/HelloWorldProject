package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText txtUser;
    private EditText txtPass;
    private Button btnLogin;
    private Button btnSignup;
    private String user = "admin";
    private String pass = "admin";

    DatabaseHandler dbhandler;
    protected Cursor cursor;
    public static SharedPreferences sharedPreferences;
    public static final String myPref = "MY_PREF";
    public static final String KEY_EMAIL = "KEY_EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("success", "Testing");
        setContentView(R.layout.activity_main);
        txtUser = findViewById(R.id.txtUsername);
        txtPass = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        Log.d(TAG, "Tes "+sharedPreferences.getString(KEY_EMAIL, new String()));
        if(!sharedPreferences.getString(KEY_EMAIL, new String()).equals("")){
            Log.d(TAG, sharedPreferences.getString(KEY_EMAIL, new String()));
            //ke home activity
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("COBA_INTENT_EXTRA", "Percobaan");
            startActivity(intent);
        }
        btnLogin.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v) {
                 cekLogin();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void cekLogin(){
        dbhandler = new DatabaseHandler(this);
        SQLiteDatabase db = dbhandler.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM user " +
                        "WHERE username = '" + txtUser.getText().toString() + "'" +"AND password = '"+txtPass.getText().toString()+"'",null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, txtUser.getText().toString());
            editor.commit();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("COBA_INTENT_EXTRA", "Percobaan");
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), "Username atau Password Anda tidak benar!", Toast.LENGTH_LONG).show();
        }
    }

    public void signup(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }



}