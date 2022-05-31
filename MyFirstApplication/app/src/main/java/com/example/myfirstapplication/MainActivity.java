package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button writeMyName;
    Button nextActivity;
    Button menuBtn;
    EditText buttonName;
    Chip chip;

    // SENSORIAI SENSORS ====================================
    private SensorManager sensorManager;
    private Sensor sensor;
    private float sensorValue;
    // ======================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonName = findViewById(R.id.editTextButton);


        writeMyName = findViewById(R.id.button);
        writeMyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonName.getText().toString().length() == 0) {
                    // snackbar
                    Snackbar sb = Snackbar.make(view, "Write the name!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    // snackbar background color
                    sb.setBackgroundTint(getResources().getColor(R.color.purple_200));
                    sb.show();
                } else {
                    writeMyName.setText(buttonName.getText().toString());
                }
            }
        });


        nextActivity = findViewById(R.id.buttonNextActivity);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NextActivity.class);

                // send text to next activity (other activity)
                EditText editText = (EditText) findViewById(R.id.editTextButton);
                String tValue = editText.getText().toString();
                intent.putExtra("Data", tValue);

                startActivity(intent);
                //Toast.makeText(MainActivity.this, "You went to the next activity!", Toast.LENGTH_SHORT).show();
            }
        });


        chip = findViewById(R.id.chip4);
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chip.isChecked()) {
                    //Toast.makeText(MainActivity.this, "cheked", Toast.LENGTH_SHORT).show();
                    // activity background color
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.yellow_light));
                } else {
                    //Toast.makeText(MainActivity.this, "not cheked", Toast.LENGTH_SHORT).show();
                    getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.cyan_light));
                }
            }
        });


        // context menu
        menuBtn = findViewById(R.id.buttonMenu);
        registerForContextMenu(menuBtn);


        // SENSORIAI SENSORS    =================================
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // sensor type
        // pressure - TYPE_PRESSURE
        // ambient temperature - TYPE_AMBIENT_TEMPERATURE (aplinkos)
        // temperature - (not supported)
        // light - TYPE_LIGHT
        // humidity - TYPE_RELATIVE_HUMIDITY (etc.)
        // ======================================================


        // check if connected to wifi
        WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            Toast.makeText(this, "wifi", Toast.LENGTH_SHORT).show();
        }
        // check if connected to mobile cellular internet
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo().isConnected() && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE) {
            Toast.makeText(this, "mobile", Toast.LENGTH_SHORT).show();
        }

    }

    // ==================================================================================
    // ==================================================================================
    // toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu, menu); // automatiškai sukuriamas .xml failas
        return true;
    }
    // toolbar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.option1:
                // baterijos lygis procentais baterija
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = this.registerReceiver(null, ifilter);

                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//                float batteryPct = level * 100 / (float) scale;
                Toast.makeText(this, "Baterija: " + level, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.option2:
                // sensoriaus reiksme sensorius sensors
                if (sensor == null) {
                    Toast.makeText(this, "No such a sensor or not supported!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Sensorius: " + sensorValue, Toast.LENGTH_SHORT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ==================================================================================
    // ==================================================================================
    // context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "A");
        menu.add(0, v.getId(), 0, "B");
        menu.add(0, v.getId(), 0, "C");
        menu.add(0, v.getId(), 0, "D");
    }
    // context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    // ==================================================================================
    // ==================================================================================

    // ==================================================================================
    // ==================================================================================

    // ==================================================================================
    // ==================================================================================

    // ==================================================================================
    // ==================================================================================
    // ==================================================================================
    // SENSORIAI SENSORS
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        sensorValue = event.values[0];
        // Do something with this sensor data.
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    // ==================================================================================
    // ==================================================================================

}
