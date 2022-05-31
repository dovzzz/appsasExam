package com.example.practice2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // TextView
        TextView textView = findViewById(R.id.textView);
        String data = getIntent().getStringExtra("Data");
        textView.setText(data);
    }
}