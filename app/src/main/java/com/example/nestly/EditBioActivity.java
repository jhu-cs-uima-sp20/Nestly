package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBioActivity extends AppCompatActivity {
    private EditText bio;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);
        setTitle("Edit Bio");

        Context context= getApplicationContext();
        SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);

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
