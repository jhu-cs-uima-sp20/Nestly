package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class ViewProfileActivity extends AppCompatActivity {
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

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
            SharedPreferences savePrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int position = savePrefs.getInt("view_position", -1);

            //if unable to add to favorites
            if (position == -1)
                return false;

            // gets user from gridfragments and adds to favorites fragment
            User fav = GridFragment.profiles.get(position);
            boolean isFav = fav.isFavorite();

            // user is added/removed from Favorites
            if (!isFav){
                FavoritesFragment.profiles.add(fav);
                Toast.makeText(getBaseContext(),
                        "Added to Favorites!", Toast.LENGTH_SHORT).show();
            } else {
                FavoritesFragment.profiles.remove(fav);
                Toast.makeText(getBaseContext(),
                        "Removed from Favorites!", Toast.LENGTH_SHORT).show();
            }


            return true;
        } else if (id == R.id.socials) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
