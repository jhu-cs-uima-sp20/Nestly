package com.example.nestly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;


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
    private String user;

    private Uri selectedImage;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profilePic = findViewById(R.id.profilePic);
        uploadPic = findViewById(R.id.uploadPic);
        bio_btn = findViewById(R.id.bio_btn);

        my_name = findViewById(R.id.my_name);
        my_major = findViewById(R.id.my_major);
        my_year = findViewById(R.id.my_year);
        my_bio = findViewById(R.id.my_bio);

        Context context= getApplicationContext();
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String name = myPrefs.getString("name", "John Doe");
        String year = myPrefs.getString("year", "2022");
        String major = myPrefs.getString("major", "Undeclared");
        String bio = myPrefs.getString("bio", "[insert bio here]");
        String email = myPrefs.getString("email", "jhed@jhu.edu");
        int i = email.indexOf('@');
        user = email.substring(0, i);

        my_name.setText(name);
        my_major.setText(major);
        my_year.setText(year);
        my_bio.setText(bio);

        uploadPic.setOnClickListener(this);
        bio_btn.setOnClickListener(bioListener);

        TabHost tabHost = findViewById(R.id.tabHost);
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
            selectedImage = data.getData();
            profilePic.setImageURI(selectedImage);

            uploadImage();
        }
    }

    private String getImageExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImage() {
        if (selectedImage != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + user + "." + getImageExtension(selectedImage));
            ref.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MyProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            DatabaseReference r = dbref.child("profiles").child(user);
                            HashMap<String, Object> im = new HashMap<>();
                            im.put("url", taskSnapshot.getDownloadUrl().toString());
                            r.updateChildren(im);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MyProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.uploadPic) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
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
