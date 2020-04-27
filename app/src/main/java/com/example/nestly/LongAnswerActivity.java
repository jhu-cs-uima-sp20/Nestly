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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongAnswerActivity extends AppCompatActivity {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_answer);
        setTitle("Long Answer");
      
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        final List<String> long_answers = new ArrayList<String>();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isEdit = sp.getBoolean("edit", false);

        if (isEdit) {
            editText1 = findViewById(R.id.long_answer1);
            editText2 = findViewById(R.id.long_answer2);
            editText3 = findViewById(R.id.long_answer3);
            editText4 = findViewById(R.id.long_answer4);

            editText1.setText(sp.getString("longAnswer1", ""));
            editText2.setText(sp.getString("longAnswer2", ""));
            editText3.setText(sp.getString("longAnswer3", ""));
            editText4.setText(sp.getString("longAnswer4", ""));
        }


        Button button = findViewById(R.id.long_answer_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText1 = findViewById(R.id.long_answer1);
                long_answers.add(editText1.getText().toString());
                editText2 = findViewById(R.id.long_answer2);
                long_answers.add(editText2.getText().toString());
                editText3 = findViewById(R.id.long_answer3);
                long_answers.add(editText3.getText().toString());
                editText4 = findViewById(R.id.long_answer4);
                long_answers.add(editText4.getText().toString());

                for(String s: long_answers) {
                    if(s.equals("")) {
                        Toast.makeText(getApplicationContext(), "You have not completed this page", Toast.LENGTH_LONG).show();
                        long_answers.removeAll(long_answers);
                        return;
                    }
                }

                // Go to profile grid, set boolean for being logged in
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor p_editor = sp.edit();
                p_editor.putBoolean("loggedIn", true);

                // put answers in SharedPreferences
                p_editor.putString("longAnswer1", long_answers.get(0));
                p_editor.putString("longAnswer2", long_answers.get(1));
                p_editor.putString("longAnswer3", long_answers.get(2));
                p_editor.putString("longAnswer4", long_answers.get(3));
                p_editor.commit();

                // add user to FireBase
                String user = sp.getString("email", "ERROR");
                String pass = sp.getString("password", "ERROR");
                String myName = sp.getString("name", "ERROR");
                String year = sp.getString("year", "ERROR");
                String major = sp.getString("major","ERROR");
                String gender = sp.getString("gender","ERROR");
                User mainUser = new User(user, pass);
                mainUser.setName(myName);
                mainUser.setLong_answers(long_answers);
                mainUser.setYear(year);
                mainUser.setMajor(major);
                mainUser.setGender(gender);

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

                // if is in Edit mode
                boolean isEdit = sp.getBoolean("edit", false);
                if (isEdit) {
                    String username = sp.getString("email", "jhed@jhu.edu");
                    username = username.substring(0, username.indexOf('@'));
                    DatabaseReference profilesRef = dbref.child("profiles").child(username);
                    profilesRef.child("long_answers").setValue(long_answers);
                    profilesRef.child("situations_answers").setValue(situation_answers);
                    profilesRef.child("habits_answers").setValue(habit_answers);
                    profilesRef.child("year").setValue(year);
                    profilesRef.child("password").setValue(pass);
                    profilesRef.child("name").setValue(myName);
                    profilesRef.child("major").setValue(major);
                    profilesRef.child("gender").setValue(gender);


                    // go to main activity stage
                    Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                    startActivity(intent);
                    return;
                }



                List<String> blocked = new ArrayList<String>();
                blocked.add("none");
                mainUser.setFavorites(blocked);

                // add to FireBase
                DatabaseReference profilesRef = dbref.child("profiles");
                HashMap<String, Object> addProfile = new HashMap<>();
                int i = user.indexOf('@');
                user = user.substring(0,i);
                addProfile.put(user, mainUser);

                p_editor.putString("my_jhed", user);
                        //.push();
                // make child with key username, make its value the User class
                //profilesRef.setValue(mainUser);
                profilesRef.updateChildren(addProfile);

                DatabaseReference ref1 = dbref.child("profiles").child(user);
                HashMap<String, Object> fav = new HashMap<>();
                fav.put("blocked",blocked);
                ref1.updateChildren(fav);

                // go to main activity stage
                Intent main_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main_intent);
            }
        });

    }

    public void gotoHome(View view) {

        SharedPreferences myPrefs =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor p_editor = myPrefs.edit();
        p_editor.putBoolean("loggedIn", true);
        p_editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
