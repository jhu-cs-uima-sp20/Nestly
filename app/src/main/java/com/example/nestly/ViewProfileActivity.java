package com.example.nestly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ViewProfileActivity extends AppCompatActivity {
    private Menu menu;
    private FirebaseDatabase myBase;
    private DatabaseReference dbref;
    private ValueEventListener listener;

    private TextView view_name;
    private TextView view_major;
    private TextView view_year;
    private TextView view_bio;

    private String name;
    private String major;
    private String userYear;
    private String userBio;
    private String userUrl;

    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        // setup Firebase
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor peditor = sp.edit();


        view_name = findViewById(R.id.view_name);
        view_major = findViewById(R.id.view_major);
        view_year = findViewById(R.id.view_year);
        view_bio = findViewById(R.id.view_bio);
        profilePic = findViewById(R.id.profile_pic);

        // email being viewed reference
        DatabaseReference reference =
                FirebaseDatabase.getInstance().getReference().child("profiles");

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                    String view_email = sp.getString("view_email", "jhed@jhu.edu");
                    view_email = view_email.substring(0,view_email.indexOf('@'));

                    Context context = getApplicationContext();

                    final HashMap<String, Object> allUsers = (HashMap<String, Object>) dataSnapshot.getValue();
                    HashMap<String, Object> curUserMap = (HashMap<String, Object>) allUsers.get(view_email);
                    assert curUserMap != null;
                    name= (String) curUserMap.get("name");
                    major = (String) curUserMap.get("major");
                    userYear = (String) curUserMap.get("year");
                    userBio = (String) curUserMap.get("bio");
                    userUrl = (String) curUserMap.get("url");
                    view_name.setText(name);
                    view_year.setText(userYear);
                    view_major.setText(major);
                    view_bio.setText(userBio);

                    if (userUrl == null || userUrl.equals("")) {
                        userUrl = "https://firebasestorage.googleapis.com/v0/b/nestly-database.appspot.com/o/images%2Fdefault.webp?alt=media&token=a5b1a8b3-82ef-4126-b011-74a008cad6bb";
                    }

                    Picasso.with(context)
                        .load(userUrl)
                        .fit()
                        .centerCrop()
                        .into(profilePic);

                    ArrayList<String> habits_answers= (ArrayList<String>) curUserMap.get("habits_answers");
                    ArrayList<String> situations_answers= (ArrayList<String>) curUserMap.get("situations_answers");
                    ArrayList<String> long_answers= (ArrayList<String>) curUserMap.get("long_answers");


                ImageButton favButton = (ImageButton) findViewById(R.id.fav_button);

                favButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                        String view_email = sp.getString("view_email", "jhed@jhu.edu");
                        String username = sp.getString("email", "jhed@jhu.edu");


                        String view_username = view_email.substring(0, view_email.indexOf('@'));


                        int i = username.indexOf('@');
                        final String user = username.substring(0, i);

                        // reference to my favorites
                        DatabaseReference f =
                                dataSnapshot.child(user).child("favorites").getRef();


                        HashMap<String, Object> myMap = (HashMap<String, Object>) allUsers.get(user);
                        HashMap<String, Object> favsMap = (HashMap<String, Object>) myMap.get("favorites");


                        if (favsMap == null || favsMap.get(view_username) == null) {
                            f.child(view_username).setValue(true);
                            Toast.makeText(getBaseContext(), "Favorited User", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        boolean wasFaved = (boolean) favsMap.get(view_username);

                        if (!wasFaved) {
                            f.child(view_username).setValue(true);
                            Toast.makeText(getBaseContext(), "Favorited User", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            f.child(view_username).setValue(false);
                            Toast.makeText(getBaseContext(),
                                    "Unfavorited User", Toast.LENGTH_SHORT).show();
                        }




                    }
                });






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        reference.addListenerForSingleValueEvent(listener);




        // Set action bar title
        getSupportActionBar().setTitle("");

        TabHost tabHost = findViewById(R.id.tabHost2);
        tabHost.setup();

        //tab 1 -- change later into colors
        TabHost.TabSpec spec = tabHost.newTabSpec("Tag One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("");
        tabHost.addTab(spec);

        //tab2 -- change later into colors
        spec = tabHost.newTabSpec("Tag Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("");
        tabHost.addTab(spec);

        //tab3 -- change later into colors
        spec = tabHost.newTabSpec("Tag Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("");
        tabHost.addTab(spec);

        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#A5C5EA"));
        tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#F2ABAB"));
        tabHost.getTabWidget().getChildAt(2).setBackgroundColor(Color.parseColor("#F0EAA8"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.block) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor peditor = sp.edit();
            String view_email = sp.getString("view_email", "jhed@jhu.edu");
            String username = sp.getString("email", "jhed@jhu.edu");
            int i = username.indexOf('@');
            final String user = username.substring(0, i);

            DatabaseReference f =
                    FirebaseDatabase.getInstance().getReference().child("profiles").child(user).child("blocked");
            HashMap<String, Object> f1 = new HashMap<>();
            int count = sp.getInt("numblocked", 1);
            String key = count + "";
            count++;
            peditor.putInt("numblocked", count);
            f1.put(key, view_email);
            f.updateChildren(f1);
            peditor.commit();


            i = view_email.indexOf('@');
            view_email = view_email.substring(0, i);
            DatabaseReference f2 =
                    FirebaseDatabase.getInstance().getReference().child("profiles").child(view_email).child("blocked");
            final long[] num = new long[1];
            f2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    num[0] = dataSnapshot.getChildrenCount();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            HashMap<String, Object> f12 = new HashMap<>();
            f12.put((num[0]+1)+"", user);
            f2.updateChildren(f12);
            Toast.makeText(getBaseContext(),
                    "User Blocked!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
