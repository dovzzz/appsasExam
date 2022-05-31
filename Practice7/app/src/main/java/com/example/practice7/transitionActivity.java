package com.example.practice7;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class transitionActivity extends AppCompatActivity {

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card2); // R.layout.activity_transition
        findViews();
        loadAnimations();
        changeCameraDistance();

        mSetRightOut.setTarget(mCardFrontLayout);
        mSetLeftIn.setTarget(mCardBackLayout);
        mSetRightOut.start();
        mSetLeftIn.start();
        mIsBackVisible = true;
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.card_anim1);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.card_anim2);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

}
