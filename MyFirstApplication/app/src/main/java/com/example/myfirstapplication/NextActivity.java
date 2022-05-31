package com.example.myfirstapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class NextActivity extends AppCompatActivity {

    Button backMain;
    Button randomNumber;
    Button getFromTxtFile;
    Button saveToTxtFile;
    EditText fileText;

    // write to file ========================================
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();

        backMain = findViewById(R.id.buttonBackMain);
        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // finish activity (returns to parent activity)
            }
        });


        randomNumber = findViewById(R.id.buttonRandom);
        randomNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomInt = (int) (Math.random() * 1000);
                Toast.makeText(NextActivity.this, randomInt + "", Toast.LENGTH_SHORT).show();
            }
        });


        // data (string) from Main activity
        TextView textView = findViewById(R.id.textViewFromMain);
        String data = getIntent().getStringExtra("Data");
        textView.setText(data);


        fileText = findViewById(R.id.editTextFile);
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String file_s = path + "/" + "pasiruosimas.txt";
        File file_f = new File(file_s);
        Charset charset = StandardCharsets.UTF_8;
        // ==================================================================================
        // ==================================================================================
        // get text from .txt file
        getFromTxtFile = findViewById(R.id.buttonGet);
        getFromTxtFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    byte[] encoded = Files.readAllBytes(Paths.get(file_f.getPath()));
                    String str = charset.decode(ByteBuffer.wrap(encoded)).toString();

                    if (str.length() == 0) {
                        Toast.makeText(NextActivity.this, "Faile nieko neparašyta :(", Toast.LENGTH_SHORT).show();
                    } else {
                        fileText.setText(str);
                    }

                    Toast.makeText(NextActivity.this, "Read: " + file_s, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(NextActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        // ==================================================================================
        // ==================================================================================
        // save text to .txt file
        saveToTxtFile = findViewById(R.id.buttonSave);
        saveToTxtFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileText.getText().toString().length() != 0) {
                    verifyStoragePermissions(NextActivity.this);
                    OutputStream fos = null;

                    try {
                        file_f.createNewFile();
                        fos = new FileOutputStream(file_f);
                        fos.write(fileText.getText().toString().getBytes(charset));

                        Toast.makeText(NextActivity.this, "Write: " + file_f.getPath(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(NextActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (Exception e) {
                                Toast.makeText(NextActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(NextActivity.this, "Nieko neparašyta :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ==================================================================================
        // ==================================================================================

    }


    // ==================================================================================
    // ==================================================================================
    // Permissions - to write to file
    public static void verifyStoragePermissions(Activity activity) {
        // check if write permission is true
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // ask user for the permission
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
