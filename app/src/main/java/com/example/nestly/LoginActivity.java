package com.example.nestly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        profilesRef = dbref.child("profiles");



        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_btn);

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<User> profiles = new ArrayList<User>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {


                    HashMap<String, String> curUserMap = (HashMap<String, String>) snap.getValue();
                    String username = curUserMap.get("username");
                    String password = curUserMap.get("password");
                    String name = curUserMap.get("name");
                    User curUser = new User(username, password);
                    curUser.setName(name);
                    profiles.add(curUser);

                    Toast.makeText(getBaseContext(),
                            username, Toast.LENGTH_SHORT).show();
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
                                    SharedPreferences.Editor peditor = savePrefs.edit();
                                    peditor.putString("name", u.getName());
                                    peditor.putString("email", u.getUsername());
                                    peditor.putString("password", u.getPassword());
                                    peditor.putBoolean("loggedIn", true);
                                    peditor.commit();

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
        String emailtxt = username.getText().toString();
        String pswd = password.getText().toString();
        if (emailtxt.indexOf("@jhu.edu") < 0) {
            Toast.makeText(getBaseContext(),
                    "Invalid Email!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pswd.length() < 1) {
            // needs to be changed later for verification
            // rn only error that occurs is if password field is empty
            Toast.makeText(getBaseContext(),
                    "Invalid Password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
