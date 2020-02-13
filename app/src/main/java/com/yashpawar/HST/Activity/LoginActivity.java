package com.yashpawar.HST.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.yashpawar.HST.R;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button button;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        //if SharedPreferences contains username and password then directly redirect to Home activity
        if(sp.contains("username") && sp.contains("password")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();   //finish current activity
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().equalsIgnoreCase("")) {
                    username.setError("Please Enter username.");
                }else if (password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError("Please enter password");
                } else {
                    loginCheck();
                }
            }
        });

    }
    void loginCheck(){
        //check username and password are correct and then add them to SharedPreferences
        if(username.getText().toString().equals("yash") && password.getText().toString().equals("yash")){
            SharedPreferences.Editor e=sp.edit();
            e.putString("username","yash");
            e.putString("password","yash");
            e.commit();

            Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();

            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
        /*else{
            Toast.makeText(LoginActivity.this,"Incorrect Login Details",Toast.LENGTH_LONG).show();
        }*/
    }
}
