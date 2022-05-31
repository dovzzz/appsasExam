package com.example.practice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button _button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button
        _button = (Button) findViewById(R.id.button);
        _button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                // on Click
                Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                EditText editText = (EditText)findViewById(R.id.editTextInput);
                String tValue = editText.getText().toString();
                intent.putExtra("Data", tValue);
                startActivity(intent);
            }
        });

    }
}