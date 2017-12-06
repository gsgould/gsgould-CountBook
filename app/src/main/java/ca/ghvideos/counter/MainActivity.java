/*
        Activity Class MainActivity.Java. Draws in major feature support for the
        app selection and once timers are created the app allows for selection and Loading
        of said timers.
 */

package ca.ghvideos.counter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    Spinner titleSpinner;
    Timer timer = new Timer();
    ArrayList<Timer> mTimerList;

    String timeName = null;

    public Button loadButton;
    public Button newButton;
    boolean empty = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Log.e("MAIN","Gets this far");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadData();
        Log.e("MAIN","Gets this far2");
        initializeUI();
        Log.e("MAIN","Gets this far3");

        loadButton = (Button)findViewById(R.id.loadButton);
        newButton = (Button)findViewById(R.id.newButton);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (empty == false) {
                    Intent nextPage = new Intent(MainActivity.this, individualTimerActivity.class);
                    if (timeName != null) {
                        Toast.makeText(MainActivity.this, "Load:" + timeName, Toast.LENGTH_SHORT).show();

                        Log.e("ORIGINAL", "STRING:" + timeName);

                        int iend = timeName.indexOf(":");
                        String subString = null;
                        if (iend != -1) {
                            subString = timeName.substring(0, iend);
                        }

                        Log.e("ONCLICK", "STRING:" + subString);

                        nextPage.putExtra("timerName", subString);

                        startActivity(nextPage);
                    } else {
                        Toast.makeText(MainActivity.this, "You have no timers", Toast.LENGTH_SHORT).show();
                    }
                } else {
                       Toast.makeText(MainActivity.this, "You have no timers, please press 'NEW'", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MAIN","Checkpoint 3.0");
                Intent nextPage = new Intent(MainActivity.this,individualTimerActivity.class);
                nextPage.putExtra("timerName","NEW");
                int size= -1;
                if (mTimerList != null) {
                    size = mTimerList.size();
                }
                mTimerList.add(new Timer(size+1,"NEW",6,0));
                saveData();
                startActivity(nextPage);

            }
        });




    }

    private void initializeUI() {
        Log.e("MAIN","Gets this far2.2");
        titleSpinner = (Spinner) findViewById(R.id.selectionSpinner);
        Log.e("MAIN","Gets this far2.3");

        ArrayAdapter<Timer> adapter = new ArrayAdapter<Timer>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, mTimerList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Log.e("MAIN","Gets this far2.4");

        if (mTimerList == null) {
            empty = true;
            return;
        }

        titleSpinner.setAdapter(adapter);
        Log.e("MAIN","Gets this far2.5");
        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                timeName = titleSpinner.getSelectedItem().toString();
                Toast.makeText(MainActivity.this,"Selected: "+timeName,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("timer list", null);
        Type type = new TypeToken<ArrayList<Timer>>() {
        }.getType();
        mTimerList = gson.fromJson(json, type);

        if (mTimerList == null) {
            mTimerList = new ArrayList<Timer>();
            empty = true;
        } else {
            empty = false;
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mTimerList);
        editor.putString("timer list", json);
        editor.apply();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        empty = true;
        loadData();
        initializeUI();
    }
}
