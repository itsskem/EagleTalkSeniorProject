package com.example.eagletalk;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eagletalk.Model.Posts;
//import com.example.eagletalk.adapter.PostAdapter;
//import com.example.eagletalk.fragment.NotificationFragment;
//import com.example.eagletalk.fragment.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainScreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    private RecyclerView recyclerView;
    //  private PostAdapter postAdapter;
    private List<Posts> postLists;
    // AdapterPost adapterPosts;


    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef;

    String currentUserID;


    public void Feed(View view) {
        Intent feed = new Intent(getApplicationContext(), Feed.class);


        startActivity(feed);
    }

    public void goToFeed(View views) {
        Intent goToFeed = new Intent(getApplicationContext(), Feed.class);
        startActivity(goToFeed);
    }

    public void Search(View view) {
        Intent search = new Intent(getApplicationContext(), SearchUsers.class);


        startActivity(search);
    }

    public void goSearch(View views) {
        Intent goToSearch = new Intent(getApplicationContext(), SearchUsers.class);
        startActivity(goToSearch);
    }


    public void Add(View view) {
        Intent add = new Intent(getApplicationContext(), PostActivity.class);


        startActivity(add);
    }

    public void goToAdd(View views) {
        Intent goToAdd = new Intent(getApplicationContext(), PostActivity.class);
        startActivity(goToAdd);
    }


    public void goToProfile(View views) {
        Intent goToProfile = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(goToProfile);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}




