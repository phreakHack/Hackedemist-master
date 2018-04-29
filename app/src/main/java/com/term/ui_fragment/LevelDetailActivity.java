package com.term.ui_fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.term.gui.LoginSuccess;
import com.term.R;

import java.util.ArrayList;

public class LevelDetailActivity extends AppCompatActivity {

    public static ArrayList<String> questions = new ArrayList<>();
    public static ArrayList<String> answers = new ArrayList<>();

    public static String ID;

    FirebaseDatabase database;
    DatabaseReference questionDatabaseReference;
    DatabaseReference answerDatabaseReference;

    LevelDetailFragment levelDetailFragment;

    public static String question = "Loading...";
    public static String answer = "abcdefghij12345";
    public static String hint = "Loading...";
    public static String hint2 = "Loading...";


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LevelListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Level",LevelListActivity.levelChosen);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_detail);

        getSupportActionBar().hide();


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        }

        ID = getIntent().getStringExtra("ID");

        levelDetailFragment = (LevelDetailFragment) getSupportFragmentManager().findFragmentById(R.id.level_detail_activity_detail_fragment);

        database = FirebaseDatabase.getInstance();

        questionDatabaseReference = database.getReference("Questions").child(LevelListActivity.levelChosen).child(ID);

        questionDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    question = dataSnapshot.child("Question").getValue().toString();
                    answer = dataSnapshot.child("Answer").getValue().toString();
                    hint = dataSnapshot.child("Hint 1").getValue().toString();
                    hint2 = dataSnapshot.child("Hint 2").getValue().toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
                //Call Refresh method in fragment

                levelDetailFragment.refreshFragment();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
