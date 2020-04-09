package com.example.nestly;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences myPrefs;
    private FirebaseDatabase myBase;
    private DatabaseReference dbref;
    private NavigationView navView;
    private View navHeader;
    private DrawerLayout myDrawerLayout;
    private Toolbar myBar;
    private ActionBarDrawerToggle toggle;


    private ArrayList<User> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean loggedIn = myPrefs.getBoolean("loggedIn", false);

        if (!loggedIn) {
            startActivity(new Intent(this, StartActivity.class));
            SharedPreferences.Editor peditor = myPrefs.edit();
            peditor.putBoolean("loggedIn", true);
            peditor.commit();
        }

        // Firebase database and reference
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        DatabaseReference ref = dbref.child("test");
        User testUser = new User("testname", "testpswd");
        ref.push().setValue(testUser);

        profiles = new ArrayList<User>();
        User joe = new User("joe123", "password");
        joe.setName("Joe");
        profiles.add(joe);
        profiles.add(joe);
        profiles.add(joe);
        profiles.add(joe);

        // Set action bar title
        myBar = findViewById(R.id.main_bar);
        myBar.setTitle("Home");

        // Navigation View
        navView = findViewById(R.id.nav_view);
        navHeader = navView.getHeaderView(0);
        navView.setNavigationItemSelectedListener(this);
        navView.bringToFront();
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, myDrawerLayout, myBar,
               0, 0);
        toggle.setDrawerIndicatorEnabled(true);
        myDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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
            // Do nothing
        }
        else if (myID == R.id.my_profile_tab) {
            startActivity(new Intent(this, MyProfileActivity.class));
        }
        else if (myID == R.id.favorites_tab) {
            // Change to Favorites fragment
            FavoritesFragment myFavsFrag = new FavoritesFragment();
            tr.replace(R.id.home_frag, myFavsFrag);
            tr.addToBackStack(null);
            tr.commit();
        }
        else if (myID == R.id.logout_tab) {
            SharedPreferences.Editor peditor = myPrefs.edit();
            peditor.putBoolean("loggedIn", false);
            peditor.commit();
            startActivity(new Intent(this, StartActivity.class));
        }
        else if (myID == R.id.hide_acc_tab) {

        }
        else if (myID == R.id.delete_acc_tab) {

        }

        myDrawerLayout.closeDrawers();
        return true;
    }

    void viewProfile(int pos) {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }
}

