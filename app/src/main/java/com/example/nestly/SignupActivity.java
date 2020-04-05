package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        year.setPrompt("Year");

        //set up gender spinner
        adapter2 = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter2);

        // set up major spinner
        adapter3 = ArrayAdapter.createFromResource(this,
                R.array.major_options, android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        major.setAdapter(adapter3);

        // database
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

    }

    /*
     * Checks if the information inputted is valid
     * @return true if valid, false otherwise
     */
    public boolean checkValid() {
        // if the user puts in an incorrect JHU email
        String jhu_email = email.getText().toString();
        String pass = password.getText().toString();
        if(jhu_email.indexOf("@jh.edu") < 0) {
            Toast.makeText(getBaseContext(),
                    "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() == 0) {
            Toast.makeText(getBaseContext(),
                    "Please Enter Password!", Toast.LENGTH_SHORT).show();
            return false;
        } /*else if (!checkedMajor || !checkedYear || !checkedGender) {
            Toast.makeText(getBaseContext(),
                    "Fields Missing!", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        // checks if field was selected
        if (parent.equals(adapter1)) {
            checkedYear = true;
        } else if (parent.equals(adapter2)) {
            checkedGender = true;
        } else if (parent.equals(adapter3)) {
            checkedMajor = true;
        }

        // add sharing preferences here
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }


}
