package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;

public class EditBioActivity extends AppCompatActivity {
    private EditText bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bio);
        setTitle("Edit Bio");

        Context context= getApplicationContext();
        SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);

        bio = (EditText) findViewById(R.id.editText);
        String input = savePrefs.getString("bio", "");
        if (input.length() > 0) {
            bio.setText(input);
        } else {
            bio.setHint("[Insert Bio Here]");
        }
    }

    @Override
    public void onBackPressed() {
        Context context= getApplicationContext();
        SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor peditor = savePrefs.edit();
        peditor.putString("bio", bio.getText().toString());
        peditor.commit();

    }
}
