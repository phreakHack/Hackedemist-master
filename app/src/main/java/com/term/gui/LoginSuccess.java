package com.term.gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.term.R;
import com.term.ui_fragment.LevelListActivity;
import com.term.ui_fragment.LoginActivity;
import com.term.ui_fragment.UserProfileActivity;

import static android.graphics.Color.rgb;

public class LoginSuccess extends AppCompatActivity {

    private TextView textView;
    public static String username ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(rgb(255,255,255)));

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){

            Intent in = new Intent(this,LoginActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);

        }

        animate();

        // Points are locally stored.
        // On login, system will try to retrieve a sharedPreference to check if the login is the first one.
        SharedPreferences preferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
        boolean isFirstLogin = preferences.getBoolean("is_first_login",true);

        if(isFirstLogin){

            preferences.edit().putInt("game_points",300).apply();
            preferences.edit().putBoolean("is_first_login",false).apply();

        }

        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String abc = Config.KEY_USERNAME1 ;
        Log.i("Login_user",abc) ;
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(Config.USERNAME_SHARED_PREF,abc);
        username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF," ");

        //Showing the current logged in email to textview
       // textView.setText("WELCOME " + username);
    }
    public void openEasyLevel(View view){
        Intent intentEasy = new Intent(this,LevelListActivity.class);
        intentEasy.putExtra("Level","Easy");
        startActivity(intentEasy);
    }
    public void openMediumLevel(View view){
        Intent intentMedium = new Intent(this,LevelListActivity.class);
        intentMedium.putExtra("Level","Medium");
        startActivity(intentMedium);
    }
    public void openHardLevel(View view){
        Intent intentHard = new Intent(this,LevelListActivity.class);
        intentHard.putExtra("Level","Hard");
        startActivity(intentHard);
    }
    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                //Getting out sharedpreferences
                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();

                //Puting the value false for loggedin
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                //Putting blank value to email
                editor.putString(Config.USERNAME_SHARED_PREF, "");

                //Saving the sharedpreferences
                editor.apply();

                Log.i("LOG OUT","User : "+ username +" has Logged Out") ;
                //Starting login activity
            }
        });

        alertDialogBuilder.setNegativeButton("No",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked

            LoginActivity.signOut();

            Intent intent=new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }else if(id == R.id.profilePage){

            Intent intent=new Intent(this,UserProfileActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    void animate(){

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animcard);
        animation.setStartOffset(200);

        findViewById(R.id.buttonEasy).setAnimation(animation);
        findViewById(R.id.buttonMedium).setAnimation(animation);
        findViewById(R.id.buttonHard).setAnimation(animation);

    }

}
