package com.example.myapplication;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class CommonActivity extends AppCompatActivity {

    private Button startBtn, endBtn, backBtn;
    private ImageView imageView;

    private boolean activeStartBtn, activeEndBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_common);

        imageView = findViewById(R.id.anim_image_view);
        startBtn = findViewById(R.id.start);
        endBtn = findViewById(R.id.end);
        backBtn = findViewById(R.id.back);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);

        startBtn.setOnClickListener(v -> {
            imageView.startAnimation(animation);

            if (!activeStartBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_purple), ContextCompat.getColor(this, R.color.holo_orange), startBtn);

            if (activeEndBtn)
                animateButtonColor(ContextCompat.getColor(this, R.color.holo_orange), ContextCompat.getColor(this, R.color.holo_purple), endBtn);

            activeStartBtn = true;
            activeEndBtn = false;
        });

        endBtn.setOnClickListener(v -> {
            imageView.clearAnimation();

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
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animationSlideBottom = AnimationUtils.loadAnimation(CommonActivity.this, R.anim.slide_in_top);
                Animation animationSlideLeft = AnimationUtils.loadAnimation(CommonActivity.this, R.anim.slide_in_left);
                Animation animationSlideRight = AnimationUtils.loadAnimation(CommonActivity.this, R.anim.slide_in_right);
                Animation animationSlideTop = AnimationUtils.loadAnimation(CommonActivity.this, R.anim.slide_in_bottom);

                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(animationSlideTop);

                startBtn.setVisibility(View.VISIBLE);
                startBtn.startAnimation(animationSlideRight);

                endBtn.setVisibility(View.VISIBLE);
                endBtn.startAnimation(animationSlideLeft);

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