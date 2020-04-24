package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditBioActivity extends AppCompatActivity {
    private EditText bio;
    private Button save;

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);
        setTitle("Edit Bio");

        Context context= getApplicationContext();
        SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);

        // get firebase user reference
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor peditor = sp.edit();
        String user_email = sp.getString("email", "jhed@jhu.edu");
        final String jhed = user_email.substring(0,user_email.indexOf('@'));

        bio = findViewById(R.id.editText);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context= getApplicationContext();
                SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor p_editor = savePrefs.edit();
                p_editor.putString("bio", bio.getText().toString());
                p_editor.commit();

                DatabaseReference ref = dbref.child("profiles").child(jhed);
                HashMap<String, Object> b = new HashMap<>();
                b.put("bio", bio.getText().toString());
                ref.updateChildren(b);

                EditBioActivity.super.onBackPressed();
            }
        });
        String input = savePrefs.getString("bio", "");
        if (input.length() > 0) {
            bio.setText(input);
        } else {
            bio.setHint("[Insert Bio Here]");
        }
    }

}
