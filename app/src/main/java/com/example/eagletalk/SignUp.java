package com.example.eagletalk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    EditText username,fullName,email,password, grade, birthday, identity;
    Button register;
    TextView txt_login;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        ArrayList<String> censorList = new ArrayList<String>();
        censorList.add("fuck");
        censorList.add("shit");
        censorList.add("damn");
        censorList.add("bitch");
        censorList.add("ass");
        censorList.add("asshole");
        censorList.add("pussy");
        censorList.add("cunt");
        censorList.add("hoe");
        censorList.add("whore");
        censorList.add("fag");
        censorList.add("faggot");
        censorList.add("dick");
        censorList.add("bullshit");
        censorList.add("nigga");
        censorList.add("nigger");





        username = findViewById(R.id.username);
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        grade = findViewById(R.id.grade);
        birthday = findViewById(R.id.birthday);
        identity = findViewById(R.id.identity);

        register = findViewById(R.id.clickToSignUp);
        txt_login = findViewById(R.id.txt_login);

        auth = FirebaseAuth.getInstance();

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, LogIn.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SignUp.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_username = username.getText().toString();
                String str_fullName = fullName.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_grade = grade.getText().toString();
                String str_birthday = birthday.getText().toString();
                String str_identity = identity.getText().toString();


                boolean goodToGo = false;
                boolean appropiateUser = true;



               // int lengthOfEmail = str_email.length();
               // int idk = lengthOfEmail - (lengthOfEmail - 12);
                //str_email.substring(idk) != "@mcdonogh.org"

                for(int i = 0; i < censorList.size(); i++){
                    if(str_username.contains(censorList.get(i))){
                        Toast.makeText(SignUp.this, "Please be appropiate!", Toast.LENGTH_SHORT).show();
                        appropiateUser = false;
                    } else {
                        i++;
                    }
                }


                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullName) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(SignUp.this, "ALL FIELDS ARE REQUIRED!", Toast.LENGTH_SHORT).show();
                } else if (str_password.length() < 6) {
                    Toast.makeText(SignUp.this, "Must have at least 6 characters", Toast.LENGTH_SHORT).show();
                } else if (str_email.contains("@mcdonogh.org") == false){
                    Toast.makeText(SignUp.this, "MUST BE MCDONOGH EMAIL", Toast.LENGTH_SHORT).show();
                } else {
                    goodToGo = true;
                }

                if (appropiateUser = true && goodToGo == true){
                    register(str_username, str_fullName, str_email, str_password, str_grade, str_birthday, str_identity);

                }

            }

        });
    }



    private void register(String username, String fullName, String email, String password, String grade, String birthday, String identity){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userID = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id",userID);
                            hashMap.put("username",username.toLowerCase());
                            hashMap.put("fullName",fullName);
                            hashMap.put("bio", "");
                            hashMap.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/eagle-talk.appspot.com/o/placeholder.png?alt=media&token=29136c1f-32ae-4329-902a-80d0f5dd2ed5");
                            hashMap.put("grade", grade);
                            hashMap.put("birthday", birthday);
                            hashMap.put("identity", identity);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent= new Intent(SignUp.this, MainScreen.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });

                        } else {
                            pd.dismiss();
                            Toast.makeText(SignUp.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }

                        }
                });
    }
}
