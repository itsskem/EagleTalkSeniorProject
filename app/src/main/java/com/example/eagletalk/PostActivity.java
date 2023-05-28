package com.example.eagletalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.text.TextUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

//import com.example.eagletalk.Model.Posts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private Toolbar aToolbar;
    private ImageButton selectPostImage;
    private Button UpdatePostButton;
    private EditText PostDescription, PostUsername;

    private final static int Gallery_Pick = 1;
    private Uri imageUri;
    private String Description, Username, current_username;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef, usernameReference, mref;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_user_id;


    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("User Posts");
    private FirebaseAuth auth;

   private StorageTask mUploadTask;

    private ProgressBar loadingBar;

    public void goBack(View view) {
        Intent back = new Intent(getApplicationContext(), MainScreen.class);
        startActivity(back);
    }



   // @SuppressLint("MissingInflatedId")
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

      //  FirebaseUser firebaseUser = auth.getCurrentUser();
      //  assert firebaseUser != null;



      //  usernameReference = FirebaseDatabase.getInstance().getReference().child("Users").child("username");


      //  auth = FirebaseAuth.getInstance();

        mref = FirebaseDatabase.getInstance().getReference("Users");

        selectPostImage = (ImageButton) findViewById(R.id.imageUploaderButton);
        UpdatePostButton = (Button) findViewById(R.id.postButton);
        PostDescription = (EditText) findViewById(R.id.thePostDescription);
        PostUsername = (EditText) findViewById(R.id.thePostUsername);


       // auth = FirebaseAuth.getInstance();
      //  String current_user_name = auth.getCurrentUser().getUsername().toString();


        //    auth = FirebaseAuth.getInstance();
      //  current_user_id = auth.getCurrentUser().getUid();

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
      //  PostsImageReference = FirebaseDatabase.getInstance().getReference();

        // PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");




        //    loadingBar = new ProgressBar(this);

       selectPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenGallery();
            }
        });


        UpdatePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidatePostInfo();
            }
        });
    }

    private void ValidatePostInfo() {

     /*   ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                VerifyUserName(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }; */

     //   mref.addListenerForSingleValueEvent(event);


        Description = PostDescription.getText().toString();
        Username = PostUsername.getText().toString();


       if(imageUri == null){
            Toast.makeText(this, "Please select post image...", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please write something about the image...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Username)) {
            Toast.makeText(this, "Please enter your username!", Toast.LENGTH_SHORT).show();

        } else {
            StoringImageToFirebaseStorage();
        }


    }

    private void StoringImageToFirebaseStorage() {




        postRandomName = Username + " " + Description;

        StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + postRandomName + ".jpg");

        mUploadTask = fileReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){

                    //   downloadUrl = filePath.getDownloadUrl().toString();
                    //task.getResult().getStorage().getDownloadUrl().toString(),

                  /*  Posts model = new Posts(uri.toString());
                    String modelId = root.push().getKey();
                    root.child(modelId).setValue(model); */
                    Toast.makeText(PostActivity.this, "Upload successful!", Toast.LENGTH_LONG).show();
                    Upload upload = new Upload(PostUsername.getText().toString().trim(), PostDescription.getText().toString().trim(),
                            task.getResult().getStorage().getDownloadUrl().toString());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);


                }
                else {
                    String message = task.getException().getMessage();
                    Toast.makeText(PostActivity.this, "Sorry, error :/" + message, Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

  /*  private void VerifyUserName(DataSnapshot snapshot) {
        if(snapshot.exists()){
            for(DataSnapshot ds:snapshot.getChildren()){
                String name = ds.child("username").getValue(String.class);
                if(Username != name){
                    Toast.makeText(this, "Lol. Enter your real user name", Toast.LENGTH_LONG).show();

                }

            } */






       /* addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PostActivity.this, "Image uploaded successfully to storage:)", Toast.LENGTH_SHORT).show();
                Upload upload = new Upload(PostDescription.getText().toString().trim(),
                        taskSnapshot.getDownloadURL().toString());
                String uploadId = mDatabaseRef.push().getKey();
                mDatabaseRef.child(uploadId).setValue(upload);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
                    }
                }); */


    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== Gallery_Pick && resultCode==RESULT_OK&& data!=null && data.getData() != null){
            imageUri = data.getData();
            selectPostImage.setImageURI(imageUri);

        }
    }
}







