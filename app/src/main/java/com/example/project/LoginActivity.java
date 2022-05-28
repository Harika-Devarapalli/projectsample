package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText phone;
    TextView Signup;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        phone = findViewById(R.id.input_phone_number);
        Signup = findViewById(R.id.signin_signup);
        submit = findViewById(R.id.phone_submit_button);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = phone.getText().toString();
                Intent intent = new Intent(getApplicationContext(),OtpCheck.class);
                intent.putExtra("phone",phoneNum);
                intent.putExtra("signup",-1);
                startActivity(intent);
                finish();
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),SignUpPage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}