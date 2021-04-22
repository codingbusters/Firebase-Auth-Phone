package com.codingbusters.firebasephoneauthentication;

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

import java.util.concurrent.TimeUnit;

public class manageotp extends AppCompatActivity {

    EditText t2;
    Button b2;
    String Phonenumber;
    String OtpId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageotp);
        Phonenumber = getIntent().getStringExtra("mobile").toString();
        t2 = findViewById(R.id.t2);
        b2 = findViewById(R.id.b2);
        mAuth = FirebaseAuth.getInstance();

        Initiateotp();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t2.getText().toString().isEmpty()){
                    Toast.makeText(manageotp.this, "Blank Fields", Toast.LENGTH_SHORT).show();
                }else if (t2.getText().toString().length() != 6){
                    Toast.makeText(manageotp.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OtpId,t2.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });


    }

    private void Initiateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(Phonenumber, 60, TimeUnit.SECONDS, this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        OtpId = s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    } 

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(manageotp.this, "Error"+e, Toast.LENGTH_LONG).show();
                    }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(manageotp.this,dashboard.class));
                            finish();
                        } else {
                            Toast.makeText(manageotp.this, "Sign In Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}