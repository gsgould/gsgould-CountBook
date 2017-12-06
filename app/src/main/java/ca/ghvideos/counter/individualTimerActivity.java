/*
        IndividualTimerActivity is the view class that visualizes a timer. You may add, subtract
        reset, change comments / name / base_count off of this class. It is the primary
        activity class for interacting with Timer objects.
 */

package ca.ghvideos.counter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class individualTimerActivity extends AppCompatActivity {

    Date currentDate = new Date();

    Button buttonAdd, buttonSubtract, buttonReset, buttonSave, buttonDelete;
    TextView textViewCurrentCount, textViewDate;
    EditText editName, editInitial, editComments;
    ArrayList<Timer> mTimerList;
    Timer specificTimer;
    boolean isNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_timer);


        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonSubtract = (Button)findViewById(R.id.buttonSubtract);
        buttonReset = (Button)findViewById(R.id.buttonReset);
        textViewCurrentCount = (TextView)findViewById(R.id.textViewCurrentCount);
        textViewDate = (TextView)findViewById(R.id.textViewDate);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        editName = (EditText)findViewById(R.id.editName);
        editInitial = (EditText)findViewById(R.id.editInitial);
        editComments = (EditText)findViewById(R.id.editComments);

        Log.d("individualTimerActivity","Started On Create");
        specificTimer = loadData();
        populate(specificTimer);


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimerList.remove(mTimerList.indexOf(specificTimer));
                Toast.makeText(individualTimerActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                saveData();
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                int initialCount;
                if (editInitial.getText().length() == 0) {
                    initialCount = 0;
                }
                else initialCount = Integer.parseInt(editInitial.getText().toString());
                int currentCount = Integer.parseInt(textViewCurrentCount.getText().toString());
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countValue = textViewCurrentCount.getText().toString();
                int intCountValue = Integer.parseInt(countValue);
                intCountValue++;

                textViewCurrentCount.setText(String.valueOf(intCountValue));
            }
        });

        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countValue = textViewCurrentCount.getText().toString();
                int intCountValue = Integer.parseInt(countValue);
                intCountValue--;

                textViewCurrentCount.setText(String.valueOf(intCountValue));
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countValue = textViewCurrentCount.getText().toString();
                int intCountValue = Integer.parseInt(countValue);
                intCountValue = Integer.parseInt(editInitial.getText().toString());
                textViewCurrentCount.setText(String.valueOf(intCountValue));
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specificTimer.setCurrent_count(Integer.parseInt(textViewCurrentCount.getText().toString()));
                specificTimer.setComments(editComments.getText().toString());
                if (checks()) {
                    specificTimer.setInitial_count(Integer.parseInt(editInitial.getText().toString()));
                    specificTimer.setName(editName.getText().toString());
                    saveData();
                    Toast.makeText(individualTimerActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }

    private void saveData() {
        setDate(currentDate);
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mTimerList);
        editor.putString("timer list", json);
        editor.apply();
    }

    private Timer loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("timer list", null);
        Type type = new TypeToken<ArrayList<Timer>>() {
        }.getType();
        Timer currentTimer = null;
        mTimerList = gson.fromJson(json, type);

        if (mTimerList == null) {
            //Date date = new Date();
            mTimerList = new ArrayList<>();
            mTimerList.add(new Timer(0,"NEW",0,0));
            currentTimer = mTimerList.get(0);
        }
        else {
            for (Timer i : mTimerList) {
                if (i.getName().equals(getIntent().getExtras().getString("timerName"))) {
                    currentTimer = i;

                    Log.e("LOADDATA()","i:"+mTimerList.indexOf(i));
                }
            }
        }

        return currentTimer;
    }

    private void populate(Timer currentTimer) {

        editName.setText(currentTimer.getName());
        editInitial.setText(String.valueOf(currentTimer.getInitial_count()));

        textViewCurrentCount.setText(String.valueOf(currentTimer.getCurrent_count()));

        if (currentTimer.getDate() == null) {
            setDate(currentDate);
        } else {
            Date newDate = currentTimer.getDate();
            setDate(newDate);
        }


        if (currentTimer.getComments() != null) {
            editComments.setText(currentTimer.getComments());
        }
    }

    private void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yy");
        String dateString = sdf.format(date);
        specificTimer.setDate(date);
        textViewDate.setText(dateString);
    }

    private boolean checks() {
        if (editName.getText().length() == 0) {
            Toast.makeText(individualTimerActivity.this,"You must set a Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            if (editInitial.getText().length() == 0) {
                Toast.makeText(individualTimerActivity.this,"You must set an initial value",Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }

    }


}
