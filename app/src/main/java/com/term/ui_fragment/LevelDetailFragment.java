package com.term.ui_fragment;

import android.animation.Animator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.term.Manifest;
import com.term.R;
import com.term.app.TermuxActivity;
import com.term.gui.LoginSuccess;

import static com.term.ui_fragment.LevelListActivity.QuestionsIDs;

public class LevelDetailFragment extends Fragment {

    private Context context;

    TextView question,answer,hint;
    Button termuxButton;
    Button submitButton;
    EditText answerEditText;
    Button getHintButton;
    TextView pointsTextView;
    CardView questionCardView;

    int gamePoints = 0;
    int hintsTaken = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.level_detail_fragment,container,false);

        question = (TextView) v.findViewById(R.id.level_detail_fragment_question_text_view);

        termuxButton = (Button) v.findViewById(R.id.level_detail_fragment_open_terminal_button);

        termuxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, TermuxActivity.class));

            }
        });

        answerEditText = (EditText) v.findViewById(R.id.level_detail_fragment_answer_edit_text);
        submitButton = (Button) v.findViewById(R.id.level_detail_fragment_submit_answer_button);
        getHintButton = (Button) v.findViewById(R.id.level_detail_fragment_hint_button);
        pointsTextView = (TextView) v.findViewById(R.id.level_detail_fragment_points_text_view);
        questionCardView = (CardView) v.findViewById(R.id.level_detail_fragment_question_card_view);

        setOnClickListeners();

        animate();

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);

        gamePoints = sharedPreferences.getInt("game_points",300);
        pointsTextView.setText("Points: " + String.valueOf(gamePoints));

        if(savedInstanceState!=null) {
            if (savedInstanceState.containsKey("hintsTaken")){

                hintsTaken = savedInstanceState.getInt("hintsTaken",0);

            }
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hintsTaken",hintsTaken);
    }

    void animate(){
        questionCardView.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.animcard));
    }

    public void refreshFragment(){

        //TODO:Create Hint TextView
        question.setText(LevelDetailActivity.question);

    }

    public void setOnClickListeners(){

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LevelDetailActivity.answer.matches(answerEditText.getText().toString())){

                    Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);

                    if(LevelListActivity.levelChosen.matches("Easy")) {

                        int easyLevel = preferences.getInt("easy_level", 0);

                        if (Integer.parseInt(LevelDetailActivity.ID) > easyLevel) {
                            preferences.edit().putInt("easy_level", Integer.parseInt(LevelDetailActivity.ID)).apply();
                            preferences.edit().putInt("game_points",gamePoints+50).apply();
                        }

                        if(Integer.parseInt(QuestionsIDs.get(QuestionsIDs.size()-1)) > Integer.parseInt(LevelDetailActivity.ID)){

                            MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.smallcrowd);
                            mediaPlayer.start();

                            new AlertDialog.Builder(context)
                                .setIcon(R.mipmap.logo)
                                .setTitle("Correct!")
                                .setMessage("Please proceed to the next level")
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent detailIntent = new Intent(context, LevelDetailActivity.class);
                                        detailIntent.putExtra("ID", QuestionsIDs.get(QuestionsIDs.indexOf(LevelDetailActivity.ID) + 1));
                                        startActivity(detailIntent);
                                    }
                                })
                                .setNegativeButton("Stay Here",null)
                                .show();


                        }else{

                            new  AlertDialog.Builder(context)
                                .setIcon(R.mipmap.logo)
                                .setMessage("Congratulations! You've reached the end of " + LevelListActivity.levelChosen +" !")
                                .setTitle("You're on your way!")
                                .setPositiveButton("Go to next level", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                            Intent in = new Intent(context,LevelListActivity.class);
                                            in.putExtra("Level","Medium");
                                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);

                                    }
                                })
                                .setNegativeButton("Stay here",null)
                                .show();

                            MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.smallcrowd);
                            mediaPlayer.start();

                        }

                    }

                    if(LevelListActivity.levelChosen.matches("Medium")) {

                        int mediumLevel = preferences.getInt("medium_level", 0);

                        if (Integer.parseInt(LevelDetailActivity.ID) > mediumLevel) {
                            preferences.edit().putInt("medium_level", Integer.parseInt(LevelDetailActivity.ID)).apply();
                            preferences.edit().putInt("game_points",gamePoints+50).apply();
                        }

                        if(Integer.parseInt(QuestionsIDs.get(QuestionsIDs.size()-1)) > Integer.parseInt(LevelDetailActivity.ID)){

                            MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.smallcrowd);
                            mediaPlayer.start();

                            new AlertDialog.Builder(context)
                                .setIcon(R.mipmap.logo)
                                .setTitle("Correct!")
                                .setMessage("Please proceed to the next level")
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent detailIntent = new Intent(context, LevelDetailActivity.class);
                                        detailIntent.putExtra("ID", QuestionsIDs.get(QuestionsIDs.indexOf(LevelDetailActivity.ID) + 1));
                                        startActivity(detailIntent);
                                    }
                                })
                                .setNegativeButton("Stay Here",null)
                                .show();
                        }else {
                            new AlertDialog.Builder(context)
                                .setIcon(R.mipmap.logo)
                                .setMessage("Congratulations! You've reached the end of " + LevelListActivity.levelChosen + " !")
                                .setTitle("You're on your way!")
                                .setPositiveButton("Go to next level", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                            Intent in = new Intent(context,LevelListActivity.class);
                                            in.putExtra("Level","Hard");
                                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);

                                    }
                                })
                                .setNegativeButton("Stay here",null)
                                .show();

                            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.smallcrowd);
                            mediaPlayer.start();
                        }
                    }

                    if(LevelListActivity.levelChosen.matches("Hard")) {

                        int hardLevel = preferences.getInt("hard_level", 0);

                        if (Integer.parseInt(LevelDetailActivity.ID) > hardLevel) {
                            preferences.edit().putInt("hard_level", Integer.parseInt(LevelDetailActivity.ID)).apply();
                            preferences.edit().putInt("game_points",gamePoints+50).apply();
                        }

                        if(Integer.parseInt(QuestionsIDs.get(QuestionsIDs.size()-1)) > Integer.parseInt(LevelDetailActivity.ID)){

                            MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.smallcrowd);
                            mediaPlayer.start();

                            new AlertDialog.Builder(context)
                                .setIcon(R.mipmap.logo)
                                .setTitle("Correct!")
                                .setMessage("Please proceed to the next level")
                                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent detailIntent = new Intent(context, LevelDetailActivity.class);
                                        detailIntent.putExtra("ID", QuestionsIDs.get(QuestionsIDs.indexOf(LevelDetailActivity.ID) + 1));
                                        startActivity(detailIntent);
                                    }
                                })
                                .setNegativeButton("Stay Here",null)
                                .show();
                        }else{
                            new  AlertDialog.Builder(context)
                                .setIcon(R.mipmap.logo)
                                .setMessage("Congratulations! You've reached the end of " + LevelListActivity.levelChosen +" !")
                                .setTitle("You're on your way!")
                                .setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent in = new Intent(context, LoginSuccess.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(in);
                                    }
                                })
                                .show();

                            MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.smallcrowd);
                            mediaPlayer.start();

                        }
                    }

                }else{

                    Toast.makeText(context, "Try Again!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        getHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hintsTaken == 0){

                    new AlertDialog.Builder(context)
                        .setIcon(R.mipmap.hint)
                        .setTitle("Hint 1")
                        .setMessage(LevelDetailActivity.hint)
                        .setPositiveButton("OK",null)
                        .setMessage(Html.fromHtml("<font color='"+R.color.dialog_font_color+"'>"+LevelDetailActivity.hint+"</font>"))
                        .show()
                        .getWindow().setBackgroundDrawableResource(R.color.dialog_color);

                    hintsTaken++;

                    Toast.makeText(context, String.valueOf(gamePoints) , Toast.LENGTH_SHORT).show();

                }else if(hintsTaken == 1){

                    if(gamePoints>=100) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);

                        gamePoints = gamePoints - 100;
                        sharedPreferences.edit().putInt("game_points",gamePoints).apply();
                        pointsTextView.setText("Points: " + String.valueOf(gamePoints));

                        new AlertDialog.Builder(context)
                            .setIcon(R.mipmap.hint)
                            .setTitle("Hint 2")
                            .setMessage(LevelDetailActivity.hint2)
                            .setPositiveButton("OK", null)
                            .setMessage(Html.fromHtml("<font color='"+R.color.dialog_font_color+"'>"+LevelDetailActivity.hint2+"</font>"))
                            .show()
                            .getWindow().setBackgroundDrawableResource(R.color.dialog_color);

                        hintsTaken++;

                    }else{

                        new AlertDialog.Builder(context)
                            .setIcon(R.mipmap.hint)
                            .setTitle("Not enough points")
                            .setMessage("You do not possess the points required to access this.")
                            .setPositiveButton("OK", null)
                            .show()
                            .getWindow().setBackgroundDrawableResource(R.color.dialog_color);

                    }
                }else{

                    new AlertDialog.Builder(context)
                        .setIcon(R.mipmap.hint)
                        .setTitle("Hint 2")
                        .setMessage(LevelDetailActivity.hint2)
                        .setPositiveButton("OK", null)
                        .show()
                        .getWindow().setBackgroundDrawableResource(R.color.dialog_color);

                }

            }
        });

    }

}
