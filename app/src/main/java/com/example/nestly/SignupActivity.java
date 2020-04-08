package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView name;
    private TextView email;
    private TextView password;
    private Button signup;
    private Spinner major;
    private Spinner year;
    private Spinner gender;
    private ArrayAdapter<CharSequence> adapter1;
    private ArrayAdapter<CharSequence> adapter2;
    private ArrayAdapter<CharSequence> adapter3;

    private boolean checkedYear;
    private boolean checkedMajor;
    private boolean checkedGender;

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Signup");

        // Firebase database and reference
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        //get ids
        name = (TextView) findViewById(R.id.name_txt);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signup_btn);
        major = (Spinner) findViewById(R.id.major);
        year = (Spinner) findViewById(R.id.year);
        gender = (Spinner) findViewById(R.id.gender);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkValid())
                    return;

                savePreferences();
                // move to habits portion of the quiz
                Intent habits_intent = new Intent(getApplicationContext(), HabitsActivity.class);
                startActivity(habits_intent);
            }
        });

        //set up year spinner
        adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year_options, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter1);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();

                peditor.putInt("year_index", position);
                checkedYear = true;
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //set up gender spinner
        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter2);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();

                peditor.putInt("gender_index", position);
                checkedGender = true;
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // set up major spinner
        adapter3 = ArrayAdapter.createFromResource(this,
                R.array.major_options, android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        major.setAdapter(adapter3);
        major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor peditor = savePrefs.edit();

                peditor.putInt("major_index", position);
                checkedMajor = true;
                peditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void savePreferences() {
        Context context= getApplicationContext();
        SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor peditor = savePrefs.edit();
        String user = email.getText().toString();
        user = user.substring(0, user.indexOf('@'));
        String pswd = password.getText().toString();

        // create new user
        User main = new User(user, pswd);
        main.setName(name.getText().toString());

        // add to firebase

        peditor.putString("name", name.getText().toString());
        peditor.putString("email", email.getText().toString());
        peditor.putString("password", password.getText().toString());
        peditor.commit();
    }

    /*
     * Checks if the information inputted is valid
     * @return true if valid, false otherwise
     */
    public boolean checkValid() {
        Context context= getApplicationContext();
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
        } else if(jhu_email.indexOf("@jh.edu") < 0) {
            Toast.makeText(getBaseContext(),
                    "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() == 0) {
            Toast.makeText(getBaseContext(),
                    "Please Enter Password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (y == -1 || m == -1 || g == -1) {
            Toast.makeText(getBaseContext(),
                    "Fields Missing!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) { }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }


}
