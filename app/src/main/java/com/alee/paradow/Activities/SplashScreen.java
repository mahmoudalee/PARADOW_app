package com.alee.paradow.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.alee.paradow.R;

public class SplashScreen extends AppCompatActivity {

    Animation anim;
    ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        imageView=(ImageView)findViewById(R.id.imageView2); // Declare an imageView to show the animation.

        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
// HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);



//
//        EasySplashScreen easySplashScreen = new EasySplashScreen(SplashScreen.this).withFullScreen()
//                .withTargetActivity(LoginActivity.class).
////                        withFullScreen().
//                        withSplashTimeOut(1500).
////                        withBackgroundResource(R.drawable.splash).
//                        withBackgroundColor(Color.parseColor("#F5F5F5")).
//                        withLogo(R.drawable.logo).
//                        withBeforeLogoText("PARADOW");
////                        withFooterText("Developed by \n  Stone Team");
//
//        easySplashScreen.getBeforeLogoTextView().setTextColor(Color.BLACK);
////        easySplashScreen.getFooterTextView().setTextColor(Color.WHITE);
////        easySplashScreen.getFooterTextView().setPaddingRelative(0,0,0,50);
//
//        View view = easySplashScreen.create();
//
//        setContentView(view);

    }



}
