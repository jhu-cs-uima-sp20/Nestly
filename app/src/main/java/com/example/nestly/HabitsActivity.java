package com.example.nestly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HabitsActivity extends AppCompatActivity {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;
    final String[] habits_answers = new String[11];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Habits");
        setContentView(R.layout.activity_habits);
      
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();




        //who do you identify as
        final Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.identify_as, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                habits_answers[0]=spinner.getSelectedItem().toString();
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();
                peditor.putString("intro/extrovert", habits_answers[0]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //checkboxes
        final CheckBox cb1= findViewById(R.id.checkbox1);
        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb1.isChecked()) {
                    Context context= getApplicationContext();
                    SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor peditor = savePrefs.edit();
                    peditor.putBoolean("check1", true);
                    peditor.commit();
                    habits_answers[1]=0+"";
                }
            }
        });
        final CheckBox cb2= findViewById(R.id.checkbox2);
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb2.isChecked()) {
                    Context context= getApplicationContext();
                    SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor peditor = savePrefs.edit();
                    peditor.putBoolean("check2", true);
                    peditor.commit();
                    habits_answers[2]=0+"";
                }
            }
        });
        final CheckBox cb3= findViewById(R.id.checkbox3);
        cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb3.isChecked()) {
                    Context context= getApplicationContext();
                    SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor peditor = savePrefs.edit();
                    peditor.putBoolean("check3", true);
                    peditor.commit();
                    habits_answers[3]=0+"";
                }
            }
        });
        final CheckBox cb4= findViewById(R.id.checkbox4);
        cb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb4.isChecked()) {
                    Context context= getApplicationContext();
                    SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor peditor = savePrefs.edit();
                    peditor.putBoolean("check4", true);
                    peditor.commit();
                    habits_answers[4]=0+"";
                }
            }
        });
        final CheckBox cb5= findViewById(R.id.checkbox5);
        cb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb5.isChecked()) {
                    Context context= getApplicationContext();
                    SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor peditor = savePrefs.edit();
                    peditor.putBoolean("check5", true);
                    peditor.commit();
                    habits_answers[5]=0+"";
                }
            }
        });
        final CheckBox cb6= findViewById(R.id.checkbox6);
        cb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb6.isChecked()) {
                    Context context= getApplicationContext();
                    SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor peditor = savePrefs.edit();
                    peditor.putBoolean("check6", true);
                    peditor.commit();
                    habits_answers[6]=0+"";
                }
            }
        });

        //time spend in room
        final Spinner spinner2 = findViewById(R.id.spinner2);
        Integer[] items = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                habits_answers[7] = spinner2.getSelectedItem().toString();
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();
                peditor.putString("room_time", habits_answers[7]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //wake up time
        final Spinner spinner3 = findViewById(R.id.spinner3);
        String[] wake_times = new String[]{"4am", "5am", "6am", "7am", "8am", "9am", "10am", "11am", "12pm", "1pm", "2pm"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wake_times);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter2);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               habits_answers[8]= spinner3.getSelectedItem().toString();
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();
                peditor.putString("wakeUp_time", habits_answers[8]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //sleep time
        final Spinner spinner4 = findViewById(R.id.spinner4);
        String[] sleep_times = new String[]{"8pm", "9pm", "10pm", "11pm", "12am", "1am", "2am", "3am", "4am", "5am", "6am"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sleep_times);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                habits_answers[9]=spinner4.getSelectedItem().toString();
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();
                peditor.putString("sleep_time", habits_answers[9]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //bring friends
        final Spinner spinner5 = findViewById(R.id.spinner5);
        String [] frequency = new String[]{"1","2","3","5","6","7","8","9","10+"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, frequency);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter3);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                habits_answers[10]=spinner5.getSelectedItem().toString();
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();
                peditor.putString("bring_friends", habits_answers[10]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void gotoSituations(View view) {
        /*for(String s: habits_answers) {

            if(s.equals("")) {
                Toast.makeText(getApplicationContext(), "You have not completed this page", Toast.LENGTH_LONG).show();
                return;
            }
        }
        */

        Intent intent = new Intent(this, SituationalActivity.class);
        startActivity(intent);
    }
}
