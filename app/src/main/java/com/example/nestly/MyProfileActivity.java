package com.example.nestly;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView profilePic;
    private ImageButton uploadPic;
    private ImageButton bio_btn;
    private TextView my_name;
    private TextView my_major;
    private TextView my_year;
    private TextView my_bio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        profilePic = (ImageView)findViewById(R.id.profilePic);
        uploadPic = (ImageButton)findViewById(R.id.uploadPic);
        bio_btn = (ImageButton) findViewById(R.id.bio_btn);

        my_name = (TextView) findViewById(R.id.my_name);
        my_major = (TextView) findViewById(R.id.my_major);
        my_year = (TextView) findViewById(R.id.my_year);
        my_bio = (TextView) findViewById(R.id.my_bio);

        Context context= getApplicationContext();
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String name = myPrefs.getString("name", "John Doe");
        String year = myPrefs.getString("year", "2022");
        String major = myPrefs.getString("major", "Undeclared");
        String bio = myPrefs.getString("bio", "[insert bio here]");

        my_name.setText(name);
        my_major.setText(major);
        my_year.setText("Class Of " + year);
        my_bio.setText(bio);

        uploadPic.setOnClickListener(this);
        bio_btn.setOnClickListener(bioListener);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Tag One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tag Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("");

        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tag Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("");
        tabHost.addTab(spec);

        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#A5C5EA"));
        tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#F2ABAB"));
        tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#F0EAA8"));


    }

    private View.OnClickListener bioListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent bio_intent = new Intent(getApplicationContext(), EditBioActivity.class);
            startActivity(bio_intent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            profilePic.setImageURI(selectedImage);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.uploadPic:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
        }

    }

    @Override
    public void onResume() {
        Context context= getApplicationContext();
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String bio = myPrefs.getString("bio", "[insert bio here]");
        my_bio.setText(bio);
        super.onResume();
    }

}
