//first screen that people see when they open the app
package com.example.eagletalk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreen extends AppCompatActivity {

    Button login, signUp;

    FirebaseUser firebaseUser;

    @Override
    protected void onStart(){
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //redirect if user is not null
        if(firebaseUser != null){
            startActivity(new Intent(HomeScreen.this, MainScreen.class));
            Log.i("current scene", "You are in HOME");
            finish();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        login = findViewById(R.id.logInButton);
        signUp = findViewById(R.id.SignUpButton);

        login.setOnClickListener(view -> startActivity(new Intent(HomeScreen.this, LogIn.class))); //directs user to log in screen

        signUp.setOnClickListener(view -> startActivity(new Intent(HomeScreen.this, SignUp.class))); //directs user to sign up screen
    }
}