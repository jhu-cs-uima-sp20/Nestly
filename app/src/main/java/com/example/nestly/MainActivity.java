package com.example.nestly;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navView;
    private View navHeader;
    private DrawerLayout myDrawerLayout;
    private Toolbar myBar;
    private ActionBarDrawerToggle toggle;
    private TextView myName;
    private TextView myYear;
    private SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean loggedIn = myPrefs.getBoolean("loggedIn", false);

        if (!loggedIn) {
            startActivity(new Intent(this, StartActivity.class));
        }

        // Set action bar title
        myBar = findViewById(R.id.main_bar);
        myBar.setTitle("Home");
        setSupportActionBar(myBar);

        myDrawerLayout = findViewById(R.id.drawer_layout);

        // Navigation View
        navView = findViewById(R.id.nav_view);
        navHeader = navView.getHeaderView(0);
        navView.setNavigationItemSelectedListener(this);
        navView.bringToFront();

        toggle = new ActionBarDrawerToggle(this, myDrawerLayout, myBar,
               0, 0);
        toggle.setDrawerIndicatorEnabled(true);
        myDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // set to what the user entered during signup
        myName = navHeader.findViewById(R.id.my_name);
        myYear = navHeader.findViewById(R.id.my_year);
        String name = myPrefs.getString("name", "John Doe");
        String year = myPrefs.getString("year", "2022");
        myName.setText(name);
        myYear.setText("Class of " + year);

        // Initialize grid view fragment
        GridFragment myGridFrag = new GridFragment();
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.replace(R.id.home_frag, myGridFrag);
        tr.addToBackStack(null);
        tr.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int myID = item.getItemId();
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        if (myID == R.id.home_tab) {
            GridFragment myGridFrag = new GridFragment();
            tr.replace(R.id.home_frag, myGridFrag);
            tr.addToBackStack(null);
            tr.commit();
        }
        else if (myID == R.id.my_profile_tab) {
            startActivity(new Intent(this, MyProfileActivity.class));
        }
        else if (myID == R.id.favorites_tab) {
            // Change to Favorites fragment
            myBar.setTitle("Favorites");
            FavoritesFragment myFavsFrag = new FavoritesFragment();
            tr.replace(R.id.home_frag, myFavsFrag);
            tr.addToBackStack(null);
            tr.commit();
        }
        else if (myID == R.id.logout_tab) {
            SharedPreferences.Editor p_editor = myPrefs.edit();
            p_editor.putBoolean("loggedIn", false);
            p_editor.putString("name", null);
            p_editor.putString("email", null);
            p_editor.putString("password", null);
            p_editor.commit();
            startActivity(new Intent(this, StartActivity.class));
        }
        else if (myID == R.id.hide_acc_tab) {

        }
        else if (myID == R.id.delete_acc_tab) {
            String email = myPrefs.getString("email", "ERROR");
            // Remove this account from firebase

            String jhed = email.substring(0, email.indexOf('@'));
            DatabaseReference myAcc =
                    FirebaseDatabase.getInstance().getReference().child("profiles").child(jhed);
            myAcc.removeValue();

            SharedPreferences.Editor p_editor = myPrefs.edit();
            p_editor.putBoolean("loggedIn", false);
            p_editor.putString("name", null);
            p_editor.putString("email", null);
            p_editor.putString("password", null);
            p_editor.commit();
            startActivity(new Intent(this, StartActivity.class));
        }

        myDrawerLayout.closeDrawers();
        return true;
    }

    void viewProfile(int pos) {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.none) {
            //do nothing
        } else if (id == R.id.sleep_time) {
            //do something
        } else if (id == R.id.wake_time) {
            //sort by wake time
        } else if (id == R.id.situations) {

        } else if (id == R.id.time_spent) {

        }
        return super.onOptionsItemSelected(item);
    }
}

