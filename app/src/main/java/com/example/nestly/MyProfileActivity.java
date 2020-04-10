package com.example.nestly;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView profilePic;
    private ImageButton uploadPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        profilePic = (ImageView)findViewById(R.id.profilePic);
        uploadPic = (ImageButton)findViewById(R.id.uploadPic);

        uploadPic.setOnClickListener(this);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //tab 1 -- change later into colors
        TabHost.TabSpec spec = tabHost.newTabSpec("Tag One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tab One");
        tabHost.addTab(spec);

        //tab2 -- change later into colors
        spec = tabHost.newTabSpec("Tag Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tab Two");
        tabHost.addTab(spec);

        //tab3 -- change later into colors
        spec = tabHost.newTabSpec("Tag Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tab Three");
        tabHost.addTab(spec);

    }

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
}
