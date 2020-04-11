package com.example.nestly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

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

        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        myName = (TextView) navHeader.findViewById(R.id.my_name);
        myYear = (TextView) navHeader.findViewById(R.id.my_year);
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
            // Do nothing
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
            SharedPreferences.Editor peditor = myPrefs.edit();
            peditor.putBoolean("loggedIn", false);
            peditor.putString("name", null);
            peditor.putString("email", null);
            peditor.putString("password", null);
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

        if (id == R.id.search_icon) {
            // do something here
        }
        else if (id == R.id.filter_icon) {
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }
}

