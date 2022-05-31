package com.example.practice7;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NextActivity extends AppCompatActivity {

    Button explodeBtn;
    Button slideBtn;
    Button fadeBtn;
    Button otherBtn;
    Button cardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        explodeBtn = (Button) findViewById(R.id.buttonExplode);
        slideBtn = (Button) findViewById(R.id.buttonSlide);
        fadeBtn = (Button) findViewById(R.id.buttonFade);
        otherBtn = findViewById(R.id.buttonOther);
        cardBtn = findViewById(R.id.buttonCard);

        explodeBtn.setOnClickListener(explosionBtnClick);
        slideBtn.setOnClickListener(slideBtnClick);
        fadeBtn.setOnClickListener(fadeBtnClick);
        otherBtn.setOnClickListener(otherBtnClick);
        cardBtn.setOnClickListener(cardBtnClick);
    }

    View.OnClickListener explosionBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(NextActivity.this, transitionActivity.class);
            getWindow().setExitTransition(new Explode()); // Explode | Slide | Fade
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NextActivity.this).toBundle());
        }
    };

    View.OnClickListener slideBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(NextActivity.this, transitionActivity.class);

            // slide down by default
//            getWindow().setExitTransition(new Slide()); // Explode | Slide | Fade
//            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NextActivity.this).toBundle());
            // slide left or right
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    };

    View.OnClickListener fadeBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(NextActivity.this, transitionActivity.class);
            getWindow().setExitTransition(new Fade()); // Explode | Slide | Fade
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NextActivity.this).toBundle());
        }
    };

    View.OnClickListener otherBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(NextActivity.this, transitionActivity.class);

            startActivity(intent);
            overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
        }
    };

    View.OnClickListener cardBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(NextActivity.this, transitionActivity.class);

            startActivity(intent);
            overridePendingTransition(R.anim.rotate_out_180, R.anim.rotate_in_180);
        }
    };

}
