package com.example.nestly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;
    private NavigationView navView;
    private View navHeader;
    private DrawerLayout myDrawerLayout;

    private ArrayList<User> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase database and reference
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();

        profiles = new ArrayList<User>();
        User joe = new User("joe123");
        joe.setName("Joe");
        profiles.add(joe);
        profiles.add(joe);
        profiles.add(joe);
        profiles.add(joe);

        // Set action bar title
        getSupportActionBar().setTitle("Home");

        // Navigation View
        navView = findViewById(R.id.nav_view);
        navHeader = navView.getHeaderView(0);
        navView.setNavigationItemSelectedListener(this);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Initialize grid view fragment
        GridFragment myGridFrag = new GridFragment();
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.replace(R.id.home_frag, myGridFrag);
        tr.addToBackStack(null);
        tr.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return true;
    }

    void viewProfile(int pos) {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }
}

