package com.example.practice12;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class NextActivity extends AppCompatActivity {

    Button pictureBtn;
    LinearLayout linearLayout;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String filePath;
    File mediaFile;
    Uri picUri;

    String filePath1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);


        pictureBtn = findViewById(R.id.buttonPic);
        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        linearLayout = findViewById(R.id.linearLayout);
        filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/picNext.jpg";
        filePath1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/picNext1.jpg";
        showPic();
    }

    // ==================================================================================
    // ==================================================================================
    // call hardware camera to take picture
    private void dispatchTakePictureIntent() {
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mediaFile = new File(filePath); //Environment.getExternalStorageDirectory().getAbsolutePath()
                picUri = Uri.fromFile(mediaFile);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(this, "No camera on device", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    // ==================================================================================
    // ==================================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Image has been saved to:\n" + filePath, Toast.LENGTH_LONG).show();
                showPic();
                copyImg(mediaFile, filePath1);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Image taking cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to take image", Toast.LENGTH_LONG).show();
            }
        }
    }

    // ==================================================================================
    // ==================================================================================
    // display image 2*2 show 4 images image view
    public void showPic() {
        linearLayout.removeAllViews();

        // LinearLayout Setup
        // yra vertical linear layout (xml), i ji dedami du horizontal linear layout su dviais img
        LinearLayout linearLayouth= new LinearLayout(this);
        linearLayouth.setOrientation(LinearLayout.HORIZONTAL);
        linearLayouth.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayouth1= new LinearLayout(this);
        linearLayouth1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayouth1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

//        ViewGroup.LayoutParams params = linearLayouth.getLayoutParams();
//        params.height = 300;
//        linearLayouth.setLayoutParams(params);
//        linearLayouth1.setLayoutParams(params);


        Uri u = Uri.parse(filePath);
        // ImageView setup image resource, get image from Uri
        ImageView imageView = new ImageView(this);
        ImageView imageView1 = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        ImageView imageView3 = new ImageView(this);
        imageView.setImageURI(u); // imageView.setImageResource(R.drawable.play);
        imageView1.setImageURI(u);
        imageView2.setImageURI(u);
        imageView3.setImageURI(u);

//        imageView.setForegroundGravity(Gravity.BOTTOM);
        imageView.setPadding(6, 6, 6, 6);
        imageView1.setPadding(6, 6, 6, 6);
        imageView2.setPadding(6, 6, 6, 6);
        imageView3.setPadding(6, 6, 6, 6);


        // adding image view to layout
        linearLayouth.addView(imageView);
        linearLayouth.addView(imageView1);
        linearLayouth1.addView(imageView2);
        linearLayouth1.addView(imageView3);

        linearLayout.addView(linearLayouth);
        linearLayout.addView(linearLayouth1);
    }

    // ==================================================================================
    // ==================================================================================
    // make copy of a pic img make more pics in one shot
    private void copyImg(File origin, String pathCopy){
        try {
            OutputStream out;

            File mediaFile1 = new File(pathCopy); // pathCopy.createNewFile();
            out = new FileOutputStream(mediaFile1);

            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(origin));
            byte[] file = new byte[(int) origin.length()];
            buf.read(file, 0, file.length);
            buf.close();

            out.write(file);
            out.close();
        } catch (Exception e) {
        }
    }

    // ==================================================================================
    // ==================================================================================

}
