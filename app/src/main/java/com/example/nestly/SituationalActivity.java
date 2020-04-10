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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SituationalActivity extends AppCompatActivity {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;
    final String[] situations_answers = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational);
        setTitle("Situations");
      
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        //question 1
        final Spinner spinner1 = findViewById(R.id.s_spinner1);
        String[] question1 = new String[]{ "Ignore them; noise doesn’t bother you.",
                "Ask them to quiet down, but keep going.",
                "Have a stern talk about noise levels.",
                "Leave and go to the library."};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, question1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                situations_answers[0]=spinner1.getSelectedItem().toString();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor peditor = sp.edit();
                peditor.putString("situation1", situations_answers[0]);
                peditor.commit();
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
                situations_answers[1]=spinner2.getSelectedItem().toString();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor peditor = sp.edit();
                peditor.putString("situation2", situations_answers[1]);
                peditor.commit();
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
                situations_answers[2]=spinner3.getSelectedItem().toString();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor peditor = sp.edit();
                peditor.putString("situation3", situations_answers[2]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //question 4
        final Spinner spinner4 = findViewById(R.id.s_spinner4);
        String[] question4 = new String[]{"Sit them down and have a serious talk." +
                "Send them a quick text." ,
                "Mention it over dinner one day." ,
                "You don’t tell them."};
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, question4);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                situations_answers[3]=spinner4.getSelectedItem().toString();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor peditor = sp.edit();
                peditor.putString("situation4", situations_answers[3]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //question 5
        final Spinner spinner5 = findViewById(R.id.s_spinner5);
        String[] question5 = new String[]{"Ask your roommate to wash their dishes." ,
                "Those are your dishes." ,
                "Wash all of them, even the ones that are not yours.",
                "Have a meeting about chore assignments."};
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, question5);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter);
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                situations_answers[4]=spinner5.getSelectedItem().toString();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor peditor = sp.edit();
                peditor.putString("situation5", situations_answers[4]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //question 6
        final Spinner spinner6 = findViewById(R.id.s_spinner6);
        String[] question6 = new String[]{"Stay home and watch a movie in bed.",
                "Go out partying.",
                "Invite friends over to chill at your place.",
                "Scream and break a few things."};
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, question6);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter);
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                situations_answers[5]=spinner6.getSelectedItem().toString();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor peditor = sp.edit();
                peditor.putString("situation6", situations_answers[5]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void gotoLongAnswer(View view) {
        for(String s: situations_answers) {
            if(s.equals("")) {
                Toast.makeText(getApplicationContext(), "You have not completed this page", Toast.LENGTH_LONG).show();
                return;
            }
        }
        // go to long answers portion of the quiz
        Intent intent = new Intent(this, LongAnswerActivity.class);
        startActivity(intent);

    }
}
