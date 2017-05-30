package com.example.as.dieta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    Animation anim;
    ImageView imageView;

    //TextView tw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        tw = (TextView)findViewById(R.id.textViewSplash);
        final TypeWriter tw = (TypeWriter) findViewById(R.id.tv);


        tw.setText("123");
        tw.setCharacterDelay(30);
        tw.animateText("A.Szewczyk, K.Walag");


       // RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_splash);
        imageView=(ImageView)findViewById(R.id.imageViewSplash); //declaring imageView to show animation
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); //creating animation
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                // HomeActivity.class is the activity to go after splash.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //imageView.startAnimation(anim);
        imageView.startAnimation(anim);
    }
}