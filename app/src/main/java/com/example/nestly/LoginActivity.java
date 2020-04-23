package com.example.nestly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;

    private TextView username;
    private TextView password;
    private Button login;

    private DatabaseReference profilesRef;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        profilesRef = dbref.child("profiles");



        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<User> profiles = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {


                    HashMap<String, Object> curUserMap = (HashMap<String, Object>) snap.getValue();
                    assert curUserMap != null;
                    String username = (String) curUserMap.get("username");
                    String password = (String) curUserMap.get("password");
                    String name = (String) curUserMap.get("name");
                    String year = (String) curUserMap.get("year");
//                    List<String> habits = (List<String>) curUserMap.get("habit_answers");
//                    List<String> situations = (List<String>) curUserMap.get("situation_answers");
//                    List<String> long_answers = (List<String>) curUserMap.get("long_answers");
//                    List<String> favorites = (List<String>) curUserMap.get("favorites");

                    User curUser = new User(username, password);
                    curUser.setName(name);
                    curUser.setYear(year);
//                    curUser.setFavorites(favorites);
//                    curUser.setHabits_answers(habits);
//                    curUser.setSituations_answers(situations);
//                    curUser.setLong_answers(long_answers);

                    profiles.add(curUser);
                }




                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if user doesn't enter jhu address
                        if (!checkFields()) {
                            return;
                        }

                        for (User u : profiles) {


                            if (u.getUsername().equals(username.getText().toString())) {



                                if (u.getPassword().equals(password.getText().toString())) {
                                    // go to main activity

                                    SharedPreferences savePrefs =
                                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor p_editor = savePrefs.edit();
                                    p_editor.putString("name", u.getName());
                                    p_editor.putString("email", u.getUsername());
                                    p_editor.putString("password", u.getPassword());
                                    p_editor.putBoolean("loggedIn", true);
                                    p_editor.putString("year", u.getYear());

                                    // Add Habits to SharedPreferences

//                                    List<String> habits = u.getHabits_answers();
//                                    p_editor.putString("intro/extrovert", habits.get(0));
//                                    for (int i = 1; i <= 6; i++) {
//                                        String key = "check" + i;
//                                        if (habits.get(i).equals("checked"))
//                                            p_editor.putBoolean(key, true);
//                                        else
//                                            p_editor.putBoolean(key, false);
//                                    }

                                    // add number of favorite users
                                    //p_editor.putInt("numFavorites", u.getFavorites().size());



                                    // Add Situational Answers to SharedPreferences

                                    // Add Long Answers
                                    p_editor.commit();

                                    Intent main_intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(main_intent);


                                    return;
                                }
                                else {
                                    Toast.makeText(getBaseContext(),
                                            "Password incorrect!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        // User not found
                        Toast.makeText(getBaseContext(),
                                "User not found!", Toast.LENGTH_SHORT).show();

                    }
                });



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        profilesRef.addListenerForSingleValueEvent(listener);






    }

    /*
     * Checks if the information inputted is valid
     * @return true if valid, false otherwise
     */
    public boolean checkFields() {
        String emailText = username.getText().toString();
        String pass = password.getText().toString();
        if (!emailText.contains("@jhu.edu")) {
            Toast.makeText(getBaseContext(),
                    "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() < 1) {
            // needs to be changed later for verification
            // rn only error that occurs is if password field is empty
            Toast.makeText(getBaseContext(),
                    "Invalid Password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
