package umuc.com.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

/*File: Long_Term.java
* Author: Team Bucket List
* Date: 15 April 2016
* Purpose: Sets correct layout view for long-term menu. New Long-Term Goals can be added by
*          selecting floating action button. Extends goals class to handle navigation menu options.
*/

public class Long_Term extends Goals {

    // On Create method sets activity layout for long-term menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_term);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating action button to add a new goal, on click listener calls popup method
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.plus);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Selected Add", "New long-term goal will be created");
                displayCal();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Sets navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(Long_Term.this);

        // Writes text file data to array
        writeArray("longterm.txt", long_termArray);
        // Updates goals list
        updateGoalList(long_termArray);
    }

    // Updates goal group list view
    // Note: Had to recreate new array with only non-null elements. Feeding array otherwise to
    //       ListView was crashing the app
    public void updateGoalList(String[] goals) {
        int count = 0;

        for (int i = 0; i < goals.length; i++) {
            if (goals[i] != null) {
                count++;
            }
        }
        // Creates new string array
        String goals1[] = new String[count];

        for (int i = 0 ; i < goals.length ; i++)
            if (goals[i] != null) {
                goals1[i] = goals[i];
            }

        final ArrayAdapter<String> theAdapter =
                new ArrayAdapter<String>(this, R.layout.view_goals, R.id.goalTextView, goals1);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(theAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String goalPicked = "You selected " +
                        String.valueOf(adapterView.getItemAtPosition(position));


                Toast.makeText(Long_Term.this, goalPicked, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Displays date picker to keep track of deadlines for goals
    public void displayCal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);
        //final DatePicker dp = (DatePicker) findViewById(R.id.dp);

        builder.setTitle("             Set a Deadline for" + "\n                 your new Goal");
        builder.setView(picker);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String month = String.valueOf(picker.getMonth() + 1);
                String day = String.valueOf(picker.getDayOfMonth());
                String year = String.valueOf(picker.getYear());
                dateData = month + "-" + day + "-" + year;
                displayPopup();
            }
        });
        builder.show();
    }

    // Creates and displays alert dialog builder (popup to enter goal name)
    public void displayPopup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Long_Term.this);
        final EditText inputField = new EditText(Long_Term.this);
        builder.setTitle("Making a New Long Term Goal!");
        builder.setMessage("What is your new bucket list item?");
        builder.setView(inputField);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Log.d("Long_Term Goals", goalData = inputField.getText().toString());
                newGoalData = (goalData + "~" + dateData);

                Toast.makeText(getApplicationContext(), "Goal (" + goalData + ") " +
                        "Saved", Toast.LENGTH_LONG).show();

                // Write goal and date string data to file long_term.txt
                writeData("longterm.txt", newGoalData);

                // Writes text file data to array
                writeArray("longterm.txt", long_termArray);
                // Updates goals list
                updateGoalList(long_termArray);

            }
        });
        // *** Clear date variable at this point
        builder.setNegativeButton("Cancel", null ); //{ dateData = "";}
        builder.create().show();
    }
}