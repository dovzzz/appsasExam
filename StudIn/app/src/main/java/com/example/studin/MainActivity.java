package com.example.studin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.studin.database.AppActivity;
import com.example.studin.database.AppDatabase;
import com.example.studin.database.EventTable;
import com.example.studin.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public AppDatabase db;

    public List<EventTable> eventsFiltered;
    public boolean isSearching;
    private Spinner spinner;

    public MenuItem searchItem;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db = AppActivity.getDatabase();
        eventsFiltered = new ArrayList<>();
        isSearching = false;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_exportData) // , R.id.nav_settings
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Type here to search...");
        spinner = (Spinner) findViewById(R.id.calendarDropDown);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                eventsFiltered.clear();
                for (EventTable event1 : db.eventDAO().getAllTasks1()) {
                    if (event1.getStringAll().toLowerCase().contains(s.toLowerCase())) {
                        eventsFiltered.add(event1);
                    }
                }

                searchItem.collapseActionView();
                isSearching = true;

                //spinner = (Spinner) findViewById(R.id.calendarDropDown);
                spinner.setVisibility(View.VISIBLE);
                spinner.setSelection(0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //spinner = (Spinner) findViewById(R.id.calendarDropDown);
                spinner.setVisibility(View.INVISIBLE);
                spinner.setSelection(1);
                return false;
            }
        });

        return true;
    }

    public void hideSearch(){
        searchItem.collapseActionView();
        searchItem.setVisible(false);
    }
    public void unhideSearch(){
        searchItem.setVisible(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_settings:
//                Toast.makeText(this, "settings",
//                        Toast.LENGTH_LONG).show();
//                return true;

            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("About us");
                builder.setMessage("We are \n\nDominykas Savickas\nKarolis Stanevičius\n" +
                        "Dovilė Zinkutė\n\nfrom group – IFZm-9\n" +
                        "and this is our project for module " +
                        "T120B169 Fundamentals of App Development!");

                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
