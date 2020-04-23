package com.example.nestly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView name;
    private TextView email;
    private TextView password;
    private Button sign_up;
    private Spinner major;
    private Spinner year;
    private Spinner gender;
    private ArrayAdapter<CharSequence> adapter1;
    private ArrayAdapter<CharSequence> adapter2;
    private ArrayAdapter<String> adapter3;

    private boolean checkedYear;
    private boolean checkedMajor;
    private boolean checkedGender;

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;

    private DatabaseReference profilesRef;
    private ValueEventListener listener;

    /*
     * Checks if the information inputted is valid
     * @return true if valid, false otherwise
     */
    public boolean checkValid() {
        Context context = getApplicationContext();
        SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
        int y = savePrefs.getInt("year_index", -1);
        int m = savePrefs.getInt("major_index", -1);
        int g = savePrefs.getInt("gender_index", -1);
        String name_text = name.getText().toString();
        String jhu_email = email.getText().toString();
        String pass = password.getText().toString();
        if (name_text.length() == 0) {
            Toast.makeText(getBaseContext(),
                    "Missing Name!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!jhu_email.contains("@jhu.edu")) {
            Toast.makeText(getBaseContext(),
                    "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() == 0) {
            Toast.makeText(getBaseContext(),
                    "Please Enter Password!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkedGender || !checkedMajor || !checkedYear) {
            Toast.makeText(getBaseContext(),
                    "Fields Missing!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("SignUp");

        checkedYear = false;
        checkedMajor = false;
        checkedGender = false;

        // Firebase database and reference
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        profilesRef = dbref.child("profiles");

        //get ids
        name = findViewById(R.id.name_txt);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sign_up = findViewById(R.id.signup_btn);
        major = findViewById(R.id.major);
        year = findViewById(R.id.year);
        gender = findViewById(R.id.gender);


        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final ArrayList<User> profiles = new ArrayList<>();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    HashMap<String, String> curUserMap = (HashMap<String, String>) snap.getValue();
                    assert curUserMap != null;
                    String username = curUserMap.get("username");
                    String password = curUserMap.get("password");
                    String name = curUserMap.get("name");
                    User curUser = new User(username, password);
                    curUser.setName(name);
                    profiles.add(curUser);
                }

                sign_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!checkValid()) {
                            return;
                        }

                        for (User u : profiles) {
                            if (u.getUsername().equals(email.getText().toString())) {
                                Toast.makeText(getApplicationContext(),
                                        "Account already exists for this email!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        // add to firebase
                        savePreferences();

                        // move to habits portion of the quiz
                        Intent habits_intent = new Intent(getApplicationContext(), HabitsActivity.class);
                        startActivity(habits_intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        profilesRef.addListenerForSingleValueEvent(listener);


        //set up year spinner
        final String[] years = new String[]{"", "Freshman", "Sophomore", "Junior", "Senior"};
        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year_options, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter1);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor p_editor = savePrefs.edit();
                if (position > 0) {
                    checkedYear = true;
                } else {
                    checkedYear = false;
                    return;
                }
                p_editor.putString("year", years[position]);
                p_editor.putInt("year_index", position);
                p_editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set up gender spinner
        final String[] genderOptions = {"M", "F", "Other"};
        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter2);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();

                if (position > 0) {
                    checkedGender = true;
                } else {
                    checkedGender = false;
                    return;
                }
                peditor.putInt("gender_index", position);
                peditor.putString("gender", genderOptions[position]);
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set up major spinner
        final String[] majors = {"Major", "Undeclared", "Computer Science", "Neuroscience", "Political Science",
                "Mechanical Engineering", "International Studies", "Material Science",
                "Economics", "BME", "ChemBE", "Public Health", "Applied Math", "Writing Seminars"};
        adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, majors);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        major.setAdapter(adapter3);
        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();
                if (position > 0) {
                    checkedMajor = true;
                } else {
                    checkedMajor = false;
                    return;
                }
                peditor.putString("major", majors[position]);
                peditor.putInt("major_index", position);

                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void savePreferences() {
        Context context = getApplicationContext();
        SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor peditor = savePrefs.edit();
        String user = email.getText().toString();
        String pass = password.getText().toString();
        String myName = name.getText().toString();

        peditor.putString("name", myName);
        peditor.putString("email", email.getText().toString());
        peditor.putString("password", password.getText().toString());
        peditor.commit();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


}