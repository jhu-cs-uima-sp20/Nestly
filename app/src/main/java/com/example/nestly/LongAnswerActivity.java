package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LongAnswerActivity extends AppCompatActivity {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_answer);
        setTitle("Long Answer");
      
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        final String [] long_answers = new String[4];

        Button button = findViewById(R.id.long_answer_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = findViewById(R.id.long_answer1);
                long_answers[0]=editText1.getText().toString();
                EditText editText2 = findViewById(R.id.long_answer2);
                long_answers[1]=editText2.getText().toString();
                EditText editText3 = findViewById(R.id.long_answer3);
                long_answers[2]=editText3.getText().toString();
                EditText editText4 = findViewById(R.id.long_answer4);
                long_answers[3]=editText4.getText().toString();

                for(String s: long_answers) {
                    if(s.equals("")) {
                        Toast.makeText(getApplicationContext(), "You have not completed this page", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                // Go to profile grid, set boolean for being logged in
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor peditor = sp.edit();
                peditor.putBoolean("loggedIn", true);

                // put answers in SharedPreferences
                peditor.putString("longAnswer1", long_answers[0]);
                peditor.putString("longAnswer2", long_answers[1]);
                peditor.putString("longAnswer3", long_answers[2]);
                peditor.putString("longAnswer4", long_answers[3]);
                peditor.commit();

                //TODO: add user to firebase
                // create new user
                String user = sp.getString("email", "ERROR");
                String pswd = sp.getString("password", "ERROR");
                String myName = sp.getString("name", "ERROR");
                User mainUser = new User(user, pswd);
                mainUser.setName(myName);
                mainUser.setLong_answers(long_answers);

                // add situation_answers
                String[] situation_answers = new String[6];
                for (int i = 0; i < situation_answers.length; i++) {
                    String key = "situation" + (i+1);
                    sp.getString(key, "N/A");
                }
                mainUser.setSituations_answers(situation_answers);

                // add habit answers
                String[] habit_answers = new String[11];
                habit_answers[0] = sp.getString("intro/extrovert", "Both");

                // add to firebase
                DatabaseReference profilesRef = dbref.child("profiles").push();
                // make child with key username, make its value the User class

                profilesRef.setValue(mainUser);
                Intent main_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main_intent);
            }
        });

    }

    public void gotoHome(View view) {

        SharedPreferences myPrefs =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor peditor = myPrefs.edit();
        peditor.putBoolean("loggedIn", true);
        peditor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
