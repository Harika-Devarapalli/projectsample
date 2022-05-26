package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SignUpPage extends AppCompatActivity {

    EditText name,collegeid,phone;
    Spinner gender;
    Button button;
    TextView signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);


        name = findViewById(R.id.otp_name);
        collegeid = findViewById(R.id.otp_collegeid);
        phone = findViewById(R.id.otp_phone);
        signIn = findViewById(R.id.signup_signin);


        gender = findViewById(R.id.otp_gender);
        ArrayList<String> genders = new ArrayList<>();
        genders.add(new String("Gender"));
        genders.add(new String("Male"));
        genders.add(new String("Female"));
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,genders);
        gender.setAdapter(genderAdapter);

        button = findViewById(R.id.signup_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = phone.getText().toString();
                UserDetails userDetails = new UserDetails();
                Intent intent = new Intent(getApplicationContext(),OtpCheck.class);
                intent.putExtra("phone",phoneNum);
                intent.putExtra("signup",1);
                intent.putExtra("userdetails",userDetails);
                startActivity(intent);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

    }
}