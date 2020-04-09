package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;

    private TextView username;
    private TextView password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if user doesn't enter jhu address
                if (!checkFields()) {
                    return;
                }

                // go to main activity
                Intent main_intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main_intent);

            }
        });
    }

    /*
     * Checks if the information inputted is valid
     * @return true if valid, false otherwise
     */
    public boolean checkFields() {
        String emailtxt = username.getText().toString();
        String pswd = password.getText().toString();
        if (emailtxt.indexOf("@jhu.edu") < 0) {
            Toast.makeText(getBaseContext(),
                    "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pswd.length() < 0) {
            // needs to be changed later for verification
            // rn only error that occurs is if password field is empty
            Toast.makeText(getBaseContext(),
                    "Wrong Password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
