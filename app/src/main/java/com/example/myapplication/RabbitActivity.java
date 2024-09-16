package com.example.myapplication;

import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RabbitActivity extends AppCompatActivity {

    private Button startBtn, endBtn, backBtn;
    private ImageView animationIV;
    private AnimationDrawable frameAnimation;
    private boolean activeStartBtn, activeEndBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rabbit);

        animationIV = findViewById(R.id.anim_image_view);
        startBtn = findViewById(R.id.start);
        endBtn = findViewById(R.id.end);
        backBtn = findViewById(R.id.back);

        frameAnimation = (AnimationDrawable) animationIV.getDrawable();

        startBtn.setOnClickListener(v -> {
            if (!frameAnimation.isRunning())
                frameAnimation.start();

            if (!activeStartBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_purple), ContextCompat.getColor(this, R.color.holo_orange), startBtn);

            if (activeEndBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_orange), ContextCompat.getColor(this, R.color.holo_purple), endBtn);

            activeStartBtn = true;
            activeEndBtn = false;
        });

        endBtn.setOnClickListener(v -> {
            if (frameAnimation.isRunning())
                frameAnimation.stop();

            if (!activeEndBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_purple), ContextCompat.getColor(this, R.color.holo_orange), endBtn);

            if (activeStartBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_orange), ContextCompat.getColor(this, R.color.holo_purple), startBtn);

            activeStartBtn = false;
            activeEndBtn = true;
        });

        backBtn.setOnClickListener(v -> {
            super.onBackPressed();
            backBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.holo_orange));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animationSlideBottom = AnimationUtils.loadAnimation(RabbitActivity.this, R.anim.slide_in_top);
                Animation animationSlideLeft = AnimationUtils.loadAnimation(RabbitActivity.this, R.anim.slide_in_left);
                Animation animationSlideRight = AnimationUtils.loadAnimation(RabbitActivity.this, R.anim.slide_in_right);
                Animation animationSlideTop = AnimationUtils.loadAnimation(RabbitActivity.this, R.anim.slide_in_bottom);

                animationIV.setVisibility(View.VISIBLE);
                animationIV.startAnimation(animationSlideTop);

                startBtn.setVisibility(View.VISIBLE);
                startBtn.startAnimation(animationSlideLeft);

                endBtn.setVisibility(View.VISIBLE);
                endBtn.startAnimation(animationSlideRight);

                backBtn.setVisibility(View.VISIBLE);
                backBtn.startAnimation(animationSlideBottom);
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
