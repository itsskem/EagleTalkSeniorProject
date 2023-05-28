package com.example.eagletalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eagletalk.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    TextView profilename, usersname, bio, birthday, identity, grade;
    String str_profilename, str_usersname, str_bio, str_birthday, str_identity, str_grade;

    public void back(View view) {
        Intent goBack = new Intent(getApplicationContext(), MainScreen.class);


        startActivity(goBack);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
/*
        profilename = findViewById(R.id.profile_full_name);
        usersname = findViewById(R.id.my_username);
        bio = findViewById(R.id.my_bio);
        birthday = findViewById(R.id.my_birthday);
        identity = findViewById(R.id.my_gender);
        grade = findViewById(R.id.my_grade);

        str_profilename = getIntent().getExtras().getString("ProfileValue");
        profilename.setText(str_profilename);

        str_usersname = getIntent().getExtras().getString("NameValue");
        usersname.setText(str_usersname);

        str_bio = getIntent().getExtras().getString("BioValue");
        bio.setText(str_bio);

        str_birthday = getIntent().getExtras().getString("BirthdayValue");
        birthday.setText(str_birthday);

        str_identity = getIntent().getExtras().getString("IdentityValue");
        identity.setText(str_identity);

        str_grade = getIntent().getExtras().getString("GradeValue");
        grade.setText(str_grade);
*/
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView profilenametextview = (TextView) findViewById(R.id.profile_full_name);
        final TextView usernametextview = (TextView) findViewById(R.id.my_username);
        final TextView gradetextview = (TextView) findViewById(R.id.my_grade);
        final TextView birthdaytextview = (TextView) findViewById(R.id.my_birthday);
        final TextView gendertextview = (TextView) findViewById(R.id.my_gender);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String profilename = userProfile.getFullname();
                    String username = userProfile.getUsername();
                    String grade = userProfile.getGrade();
                    String birthday = userProfile.getBirthday();
                    String gender = userProfile.getGender();

                    profilenametextview.setText(profilename);
                    usernametextview.setText(username);
                    gradetextview.setText(grade);
                    birthdaytextview.setText(birthday);
                    gendertextview.setText(gender);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Sorry, there was an error :0", Toast.LENGTH_SHORT).show();
            }
        });






    }
}