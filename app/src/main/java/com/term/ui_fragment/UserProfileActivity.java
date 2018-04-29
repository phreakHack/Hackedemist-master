package com.term.ui_fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.term.R;

public class UserProfileActivity extends AppCompatActivity {

    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        animate();

        scoreTextView = findViewById(R.id.t1);

        int score = getSharedPreferences(getPackageName(),MODE_PRIVATE).getInt("game_points",300);

        scoreTextView.setText("Points: " + score);

    }

    void animate(){

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.blink);
        animation.setStartOffset(200);

        findViewById(R.id.t1).setAnimation(animation);
        findViewById(R.id.t2).setAnimation(animation);

    }

    public void shareLink(View view){

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("https://play.google.com/store/apps/details?id="+getPackageName()));
        startActivity(Intent.createChooser(sharingIntent,"Share using"));

    }
}
