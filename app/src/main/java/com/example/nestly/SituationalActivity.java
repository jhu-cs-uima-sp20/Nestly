package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SituationalActivity extends AppCompatActivity {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational);
        getActionBar().setTitle("Situations");
      
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
       
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor pe = sp.edit();

        //question 1
        final Spinner spinner = findViewById(R.id.s_spinner1);
        String[] question1 = new String[]{ "Ignore them; noise doesn’t bother you.",
                "Ask them to quiet down, but keep going.",
                "Have a stern talk about noise levels.",
                "Leave and go to the library."};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, question1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pe.putString("situation_1",spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //question 2
        final Spinner spinner2 = findViewById(R.id.s_spinner2);
        String[] question2 = new String[]{"That is not okay. This is a deal breaker.",
                "Forgive your roommate if they apologize.",
        "You don’t really care.",
                "Request that your roommate replace the item."};
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, question2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pe.putString("situation_2",spinner2.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //question 3
        final Spinner spinner3 = findViewById(R.id.s_spinner3);
        String[] question3 = new String[]{"Remind your roommate to do the cleaning.",
        "Have a talk about staying responsible.",
                "Clean everything yourself.",
        "Leave it; mess doesn’t bother you."};
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, question3);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pe.putString("situation_3",spinner3.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void gotoLongAnswer(View view) {
        Intent intent = new Intent(this, LongAnswerActivity.class);
        startActivity(intent);
    }
}
