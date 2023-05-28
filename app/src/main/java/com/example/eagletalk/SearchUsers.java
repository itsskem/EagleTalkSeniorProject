package com.example.eagletalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchUsers extends AppCompatActivity {

    public void goBack(View view){
        Intent back = new Intent(getApplicationContext(), MainScreen.class);
        startActivity(back);
    }

    DatabaseReference mref;
    private ListView listdata;
    private AutoCompleteTextView txtSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        mref = FirebaseDatabase.getInstance().getReference("Users");
        listdata= (ListView) findViewById(R.id.listData);
        txtSearch = (AutoCompleteTextView) findViewById(R.id.txtSearch);

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        mref.addListenerForSingleValueEvent(event);
    }

    private void populateSearch(DataSnapshot snapshot) {
        ArrayList<String> names = new ArrayList<>();
        if(snapshot.exists()){
            for(DataSnapshot ds:snapshot.getChildren()){
                String name = ds.child("fullName").getValue(String.class);
                names.add(name);

            }

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
            txtSearch.setAdapter(adapter);
            txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = txtSearch.getText().toString();
                    searchUser(name);
                }
            });
        }
    }

    private void searchUser(String name) {
        Query query = mref.orderByChild("fullName").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    ArrayList<String> listusers = new ArrayList<>();

                    for(DataSnapshot ds: snapshot.getChildren()){
                        User user = new User(ds.child("fullName").getValue(String.class), ds.child("username").getValue(String.class));
                        listusers.add(user.getFullname() + "\n" + user.getName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listusers);
                    listdata.setAdapter(adapter);

                } else {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    class User{

        public User(String fullname, String name){
            this.name = name;
            this.fullname = fullname;
        }

        public User(){}

        public String getName(){
            return name;
        }

        public String getFullname(){
            return fullname;
        }

        public String name;
        public String fullname;

    }
}