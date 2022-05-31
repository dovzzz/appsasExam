package com.example.practice7;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawView drawView;

    private Button triangleBtn;
    private Button circleBtn;
    private Button rectangleBtn;

    Button raideBtn;
    Button customBtn;
    Button activityBtn;

    private Button clearBtn;
    private CheckBox fillFlagCB;

    private Boolean fillFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = (DrawView) findViewById(R.id.drawView);

        triangleBtn = (Button) findViewById(R.id.buttonTriangle);
        circleBtn = (Button) findViewById(R.id.buttonCircle);
        rectangleBtn = (Button) findViewById(R.id.buttonRectangle);

        raideBtn = findViewById(R.id.buttonRaide);
        customBtn = findViewById(R.id.buttonCustom);
        activityBtn = findViewById(R.id.buttonActivity);

        clearBtn = (Button) findViewById(R.id.buttonClear);
        fillFlagCB = (CheckBox) findViewById(R.id.fillFlagCb);


        // setOnClickListener
        triangleBtn.setOnClickListener(triangleBtnClick);
        circleBtn.setOnClickListener(circleBtnClick);
        rectangleBtn.setOnClickListener(rectangleBtnClick);

        raideBtn.setOnClickListener(raideBtnClick);
        customBtn.setOnClickListener(customBtnClick);
        activityBtn.setOnClickListener(activityBtnClick);

        clearBtn.setOnClickListener(clearBtnClick);
        fillFlagCB.setOnCheckedChangeListener(fillFlagCBChange);


        // pulse pulsating pulsing button
        ObjectAnimator pulseButton = ObjectAnimator.ofPropertyValuesHolder(
                activityBtn,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                PropertyValuesHolder.ofFloat("scaleY", 1.1f));
        pulseButton.setDuration(400);
        pulseButton.setRepeatCount(ObjectAnimator.INFINITE);
        pulseButton.setRepeatMode(ObjectAnimator.REVERSE);
        pulseButton.start();


        // rotate rotation rotating button
        ObjectAnimator rotateButton = ObjectAnimator.ofPropertyValuesHolder(
                raideBtn, PropertyValuesHolder.ofFloat("rotation", 0f, 360f));
        rotateButton.setDuration(3000);
        rotateButton.setRepeatCount(ObjectAnimator.INFINITE);
        rotateButton.start();

    }

    public void setFigure(final int figure, final boolean fillFlag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawView.setFigure(figure);
                drawView.setFillFlag(fillFlag);
                drawView.invalidate();
            }
        });
    }

    CompoundButton.OnCheckedChangeListener fillFlagCBChange =
            new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    fillFlag = isChecked;
                }
            };

    View.OnClickListener triangleBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setFigure(1, fillFlag);
        }
    };

    View.OnClickListener circleBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setFigure(2, fillFlag);
        }
    };

    View.OnClickListener rectangleBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setFigure(3, fillFlag);
        }
    };

    View.OnClickListener raideBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setFigure(4, fillFlag);
        }
    };

    View.OnClickListener customBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setFigure(5, fillFlag);
        }
    };

    View.OnClickListener activityBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, NextActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener clearBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setFigure(0, fillFlag);
        }
    };

}
