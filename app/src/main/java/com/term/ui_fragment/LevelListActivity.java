package com.term.ui_fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.term.R;
import com.term.gui.LoginSuccess;

import java.util.ArrayList;

public class LevelListActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressBar progressBar;

    LevelListFragment levelListFragment;

    public static ArrayList<String> QuestionsIDs = new ArrayList<>();

    public static int totalQuestions = 0;

    public static String levelChosen = "Easy";

    private void setValueEventListeners(){

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                QuestionsIDs.clear();

                for(DataSnapshot d : dataSnapshot.getChildren()){
                    QuestionsIDs.add(d.getKey());
                }

                totalQuestions = Integer.parseInt(Long.toString(dataSnapshot.getChildrenCount()));

                levelListFragment.refreshRecyclerView();

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(LevelListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);
        getSupportActionBar().hide();

        levelChosen = getIntent().getStringExtra("Level");

        levelListFragment = (LevelListFragment) getSupportFragmentManager().findFragmentById(R.id.level_list_activity_list_fragment);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Questions").child(levelChosen);

        progressBar = (ProgressBar) findViewById(R.id.level_list_activity_progress_bar);

        setValueEventListeners();

        levelListFragment.refreshLevel();

    }
}
