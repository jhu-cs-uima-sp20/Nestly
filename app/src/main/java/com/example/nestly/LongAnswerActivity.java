package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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

        final List<String> long_answers = new ArrayList<>();

        Button button = findViewById(R.id.long_answer_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = findViewById(R.id.long_answer1);
                long_answers.add(editText1.getText().toString());
                EditText editText2 = findViewById(R.id.long_answer2);
                long_answers.add(editText2.getText().toString());
                EditText editText3 = findViewById(R.id.long_answer3);
                long_answers.add(editText3.getText().toString());
                EditText editText4 = findViewById(R.id.long_answer4);
                long_answers.add(editText4.getText().toString());

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
                peditor.putString("longAnswer1", long_answers.get(0));
                peditor.putString("longAnswer2", long_answers.get(1));
                peditor.putString("longAnswer3", long_answers.get(2));
                peditor.putString("longAnswer4", long_answers.get(3));
                peditor.commit();

                // add user to firebase
                String user = sp.getString("email", "ERROR");
                String pswd = sp.getString("password", "ERROR");
                String myName = sp.getString("name", "ERROR");
                User mainUser = new User(user, pswd);
                mainUser.setName(myName);
                mainUser.setLong_answers(long_answers);

                // add situation_answers
                List<String> situation_answers = new ArrayList<>();
                for (int i = 1; i <= 6; i++) {
                    String key = "situation" + (i);
                    String value = sp.getString(key, "N/A");
                    situation_answers.add(value);
                }
                mainUser.setSituations_answers(situation_answers);

                // add habit answers
                List<String> habit_answers = new ArrayList<>();
                habit_answers.add(sp.getString("intro/extrovert", "Both"));
                for (int i = 1; i <= 6; i++) {
                    String key = "check" + i;
                     if(sp.getBoolean(key, false)){
                         habit_answers.add("checked");
                     } else {
                         habit_answers.add("unchecked");
                     }
                }
                habit_answers.add(sp.getString("room_time", "5"));
                habit_answers.add(sp.getString("wakeUp_time", "8am"));
                habit_answers.add(sp.getString("sleep_time", "11pm"));
                habit_answers.add(sp.getString("bring_friends", "1"));
                mainUser.setHabits_answers(habit_answers);

                // add to firebase
                DatabaseReference profilesRef = dbref.child("profiles").push();
                // make child with key username, make its value the User class
                profilesRef.setValue(mainUser);

                // go to main activity stage
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
