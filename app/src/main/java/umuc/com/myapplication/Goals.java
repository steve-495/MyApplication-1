package umuc.com.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

/*File: Goals.java
* Author: Team Bucket List
* Date: 15 April 2016
* Purpose: Super class that handles navigation menu items selected. Calls the appropriate
*          activities based on menu options selected. Handles basic navigation based items as well.
*/

public class Goals extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // On Create method sets activity layout for default menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating action button to add a new goal, on click listener calls popup method
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.plus);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Goal", "New goal will be created");
                displaycal();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Sets navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Handles drawer open & close options
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Displays date picker to keep track of deadlines for goals
    public void displaycal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DatePicker picker = new DatePicker(this);
        final EditText DateInput = new EditText(Goals.this);


        builder.setTitle("Set your Deadline for your new Goal");
        builder.setView(picker);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                displayPopup();
                Log.d("Budgeting Goals", DateInput.getText().toString());
            }
        });
        builder.show();
    }

    // Creates and displays alert dialog builder
    public void displayPopup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Goals.this);
        final EditText inputField = new EditText(Goals.this);
        builder.setTitle("Making a New Goal!");
        builder.setMessage("What is your new bucket list item?");
        builder.setView(inputField);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displayPopup();
                Log.d("Goals",inputField.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel",null);

        builder.create().show();
    }

    // Starts activities for different menu options based on item selected
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_personal) {
            Log.d("PersonalActivity", "Hit personal button");
            Intent personal_intent = new Intent(Goals.this, Personal.class);
            startActivity(personal_intent);
        }
        else if (id == R.id.nav_physical) {
            Log.d("Physical Activity", "Hit physical button");
            Intent physical_intent = new Intent(Goals.this, Physical.class);
            startActivity(physical_intent);
        }
        else if (id == R.id.nav_spiritual) {
            Log.d("Spiritual Activity", "Hit spiritual button");
            Intent spiritual_intent = new Intent(Goals.this, Spiritual.class);
            startActivity(spiritual_intent);
        }
        else if (id == R.id.nav_longterm){
            Log.d("Long-Term Activity", "Hit long term button");
            Intent long_term_intent = new Intent(Goals.this, Long_Term.class);
            startActivity(long_term_intent);
        }
        else if (id == R.id.nav_budgeting){
            Log.d("Budgeting Activity", "Hit budgeting button");
            Intent budgeting_intent = new Intent(Goals.this, Budgeting.class);
            startActivity(budgeting_intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}