package com.example.myapplication;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private boolean activeRabbitBtn, activeCommonBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button rabbit = findViewById(R.id.rabbit);
        Button common = findViewById(R.id.common);



        rabbit.setOnClickListener(v -> {
            if (!activeRabbitBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_purple), ContextCompat.getColor(this, R.color.holo_orange), rabbit);

            if (activeCommonBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_orange), ContextCompat.getColor(this, R.color.holo_purple), common);

            Intent intent = new Intent(MainActivity.this, RabbitActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            
            activeRabbitBtn = true;
            activeCommonBtn = false;
        });

        common.setOnClickListener(v -> {
            if (!activeCommonBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_purple), ContextCompat.getColor(this, R.color.holo_orange), common);

            if (activeRabbitBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_orange), ContextCompat.getColor(this, R.color.holo_purple), rabbit);

            Intent intent = new Intent(MainActivity.this, CommonActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

            activeCommonBtn = true;
            activeRabbitBtn = false;
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animationSlideFirst = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_top);
                Animation animationSlideSecond = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_bottom);

                rabbit.setVisibility(View.VISIBLE);
                rabbit.startAnimation(animationSlideFirst);

                common.setVisibility(View.VISIBLE);
                common.startAnimation(animationSlideSecond);
            }
        }, 500);
    }

    private void animateButtonColor(int startColor, int endColor, Button btn) {
        ValueAnimator colorAnimation = ValueAnimator.ofArgb(startColor, endColor);
        colorAnimation.setDuration(500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                btn.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }
}