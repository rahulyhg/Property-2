package com.pivotalsoft.property;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends Activity {
    Boolean flag;
    private static int SPLASH_TIME_OUT =3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        final SharedPreferences.Editor editor = pref .edit();

        flag = pref .getBoolean("flag", false);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);


              /*  if (flag) {
                    ///second time activity

                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();

                }else{
                    //first time
                    editor.putBoolean("flag", true);
                    editor.apply();

                    Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }*/


            }
        }, SPLASH_TIME_OUT);
    }

}
