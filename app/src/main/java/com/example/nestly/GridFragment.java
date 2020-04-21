package com.example.nestly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    static ArrayList<User> profiles;

    private String username;
    private String year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grid, container, false);
        main = (MainActivity) getActivity();
        myContext = getActivity().getApplicationContext();
        grid = root.findViewById(R.id.grid);

        profiles = new ArrayList<>();
        profiles.clear();

        // FireBase database and references
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        profilesRef = dbref.child("profiles");

        final SharedPreferences myPrefs =
                PreferenceManager.getDefaultSharedPreferences(myContext.getApplicationContext());
        username = myPrefs.getString("email", "uh oh");
        year = myPrefs.getString("year","uh oh");

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myAdapter.notifyDataSetChanged();
                profiles.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    HashMap<String, Object> curUserMap = (HashMap<String, Object>) snap.getValue();
                    assert curUserMap != null;
                    String checkUser = (String) curUserMap.get("username");
                    String password = (String) curUserMap.get("password");
                    Boolean hidden = (Boolean) curUserMap.get("hidden");
                    String userYear = (String) curUserMap.get("year");

                    assert checkUser != null;
                    if (hidden == null)
                        hidden = false;
                    if (userYear == null)
                        userYear = year;
                    if (!checkUser.equals(username) && !hidden && userYear.equals(year)) {
                        profiles.add(new User(checkUser, password));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        profilesRef.addListenerForSingleValueEvent(listener);


        myAdapter = new ProfileAdapter(myContext, R.layout.profile_layout, profiles);
        grid.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get correct email information to display in profile
                SharedPreferences.Editor p_editor = myPrefs.edit();
                User viewProf = profiles.get(position);
                p_editor.putString("view_email", viewProf.getUsername());
                p_editor.putInt("view_position", position);
                p_editor.commit();
                main.viewProfile(position);
            }
        });

        return root;
    }

}
