package com.example.nestly;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GridFragment extends Fragment {

    private Context myContext;
    private ProfileAdapter myAdapter;
    private GridView grid;
    private MainActivity main;

    private FirebaseDatabase myBase;
    private DatabaseReference dbref;
    private DatabaseReference profilesRef;
    private ValueEventListener listener;
    private ArrayList<User> profiles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grid, container, false);
        main = (MainActivity) getActivity();
        myContext = getActivity().getApplicationContext();
        grid = root.findViewById(R.id.grid);

        profiles = new ArrayList<User>();
        User joe = new User("joe123", "password");
        joe.setName("Joe");

        // Firebase database and references
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        profilesRef = dbref.child("profiles");

        myAdapter = new ProfileAdapter(myContext, R.layout.profile_layout, profiles);

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myAdapter.notifyDataSetChanged();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    HashMap<String, String> curUser = (HashMap<String, String>) snap.getValue();
                    profiles.add(new User(curUser.get("name"), curUser.get("username")));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        profilesRef.addListenerForSingleValueEvent(listener);


        grid.setAdapter(myAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main.viewProfile(position);
            }
        });

        return root;
    }

}
