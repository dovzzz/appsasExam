package com.example.practice3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button generateRandomNumberBtn;
    EditText insertRandomNumberEditText;
    int randomNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        Button generateRandomNumberBtn = (Button) findViewById(R.id.generateRandomNumberBtn);
        generateRandomNumberBtn.setOnClickListener(generateRandomNumberOnClick);
        EditText insertRandomNumberEditText = (EditText) findViewById(R.id.editText);
        insertRandomNumberEditText.addTextChangedListener(insertRandomNumberTextWatcher);
    }

    View.OnClickListener generateRandomNumberOnClick =new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            randomNumber = (int) (Math.random() * 1000);
            Log.i("randomNumber", String.valueOf(randomNumber));
            Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(randomNumber), Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    TextWatcher insertRandomNumberTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() > 0) {
                if (Integer.valueOf(s.toString()) == randomNumber) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
            } else {
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}