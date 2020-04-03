package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView name;
    private TextView email;
    private TextView password;
    private ImageButton signup;
    private Spinner year;
    private Spinner gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get ids
        name = (TextView) findViewById(R.id.name_txt);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        signup = (ImageButton) findViewById(R.id.signup_btn);
        year = (Spinner) findViewById(R.id.year);
        gender = (Spinner) findViewById(R.id.gender);

        //set up year spinner
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year_options, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter1);

        //set up gender spinner
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.gender_options, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter2);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }


}
