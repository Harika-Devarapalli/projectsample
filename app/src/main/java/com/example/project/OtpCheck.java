package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OtpCheck extends AppCompatActivity {

    EditText otp_input;
    Button submit;
    String phoneNumber,sentCode;
    UserDetails userDetails;
    static int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_check);

        phoneNumber = "+91";
        sentCode = "";
        userDetails = null;
        flag = -1;

        otp_input = findViewById(R.id.otp_phone_number);
        submit = findViewById(R.id.otp_submit_button);



        flag = getIntent().getIntExtra("signup",-1);
        if(flag==1){
            userDetails = getIntent().getParcelableExtra("userdetails");
            Toast.makeText(this, ""+userDetails.getName(), Toast.LENGTH_SHORT).show();
        }

        String phone = getIntent().getStringExtra("phone");
        phoneNumber += phone;
        Toast.makeText(this, "+91 "+phone, Toast.LENGTH_SHORT).show();

        sendVerificationCode();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = otp_input.getText().toString();
                verifycode(otp);
            }
        });
    }

    public void sendVerificationCode(){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            sentCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            verifycode(code);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpCheck.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("error",e.toString());
        }
    };


    public void verifycode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sentCode,code);
        signInWithUserCredentials(credential);
    }

    public void signInWithUserCredentials(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = task.getResult().getUser();
                    long c_time = user.getMetadata().getCreationTimestamp();
                    long n_time = user.getMetadata().getLastSignInTimestamp();
                    if(c_time == n_time){
                        if(flag==-1){
                            user.delete();
                            Toast.makeText(OtpCheck.this, "Create Account First !!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),SignUpPage.class));
                        }else{
                            String uID = user.getUid();
                            FirebaseDatabase.getInstance().getReference().child("Users").child(uID).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(OtpCheck.this, "User Created Successfully...!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }
                            });
                        }
                    }else{

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                    }
                    
                    

                }else{
                    Toast.makeText(OtpCheck.this, "Error!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}