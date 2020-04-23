package com.example.nestly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        // setup Firebase
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

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
        this.menu = menu;
//        MenuItem email = menu.getItem(R.id.socials);
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        email.setTitle(sp.getString("view_email", "jhed@jhu.edu"));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.block) {
            Toast.makeText(getBaseContext(),
                    "User Blocked!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.add_favorite) {
            // verify if user has already been favorited
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor peditor = sp.edit();
            //final
            String view_email = sp.getString("view_email", "jhed@jhu.edu");
            String username = sp.getString("email", "jhed@jhu.edu");
            int i = username.indexOf('@');
            final String user = username.substring(0, i);
            DatabaseReference profilesRef = dbref.child(user).child("favorites");

            /*
            // checks if the user has already been added to favorites
            listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i = 0;
                    String key = i + "";
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        HashMap<String, Object> favs = (HashMap<String, Object>) snap.getValue();
                        assert favs != null;
                        String view_name = (String) favs.get(key);
                        if (view_name.equals(view_email)){
                            DatabaseReference myFav =
                                    FirebaseDatabase.getInstance()
                                            .getReference().child("profiles").child(user).child("favorites").child(key);
                            myFav.removeValue();
                            Toast.makeText(getBaseContext(),
                                    "Unfavorited User!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        i++;
                        key = i + "";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            profilesRef.addListenerForSingleValueEvent(listener);

             */


            DatabaseReference f =
                    FirebaseDatabase.getInstance().getReference().child("profiles").child(user).child("favorites");
            HashMap<String, Object> f1 = new HashMap<>();
            int count = sp.getInt("numFavs", 1);
            String key = count + "";
            count++;
            peditor.putInt("numFavs", count);
            f1.put(key, view_email);
            f.updateChildren(f1);
            Toast.makeText(getBaseContext(),
                    "Favorited User!", Toast.LENGTH_SHORT).show();
            peditor.commit();

            return true;
        } else if (id == R.id.socials) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
