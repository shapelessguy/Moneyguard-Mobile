package com.example.mobilemoneyguard;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilemoneyguard.ui.login.LoginActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //FirebaseAuth mAuth;
        //mAuth = FirebaseAuth.getInstance();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        //GoogleSignInActivity googleactivity = new GoogleSignInActivity();
        //startActivity(new Intent(this, BaseActivity.class));
        startActivity(new Intent(this, GoogleSignInActivity.class));
    }

}
