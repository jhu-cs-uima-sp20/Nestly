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
    private ArrayList<String> block_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grid, container, false);
        main = (MainActivity) getActivity();
        myContext = getActivity().getApplicationContext();
        grid = root.findViewById(R.id.grid);

        profiles = new ArrayList<>();
        block_list = new ArrayList<>();
        block_list.add("sdfasdfs");

        // FireBase database and references
        myBase = FirebaseDatabase.getInstance();
        dbref = myBase.getReference();
        final SharedPreferences myPrefs =
                PreferenceManager.getDefaultSharedPreferences(myContext.getApplicationContext());
        username = myPrefs.getString("email", "uh oh");

        if(username!="uh oh") {
        getBlocked();
        profilesRef = dbref.child("profiles");
        year = myPrefs.getString("year","uh oh");





        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String filter = myPrefs.getString("filter", "none");
                String myfilter = myPrefs.getString(filter, "none");
                myAdapter.notifyDataSetChanged();
                profiles.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    HashMap<String, Object> curUserMap = (HashMap<String, Object>) snap.getValue();
                    assert curUserMap != null;
                    String checkUser = (String) curUserMap.get("username");
                    String password = (String) curUserMap.get("password");
                    Boolean hidden = (Boolean) (curUserMap.get("hidden").equals(true));
                    String userYear = (String) curUserMap.get("year");
                    String checkFilter = (String) curUserMap.get(filter);
                    if (checkFilter == null)
                        checkFilter = "none";

                    assert checkUser != null;
                    
                    if (userYear == null)
                        userYear = year;
                    if (!(checkUser.equals(username)) && !hidden && !block_list.contains(checkUser)
                            && checkFilter.equals(myfilter)) {

                        if(year.equals("Junior") || year.equals("Senior")) {
                            if(userYear.equals("Junior") || userYear.equals("Senior")) {
                                profiles.add(new User(checkUser, password));
                            }
                        }


                        else if(year.equals(userYear)) {

                            profiles.add(new User(checkUser, password));
                        }
                    }
                }

                myAdapter.notifyDataSetChanged();
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
                p_editor.commit();
                main.viewProfile(position);
            }
        });}

        return root;
    }

    private void getBlocked() {
        final SharedPreferences myPrefs =
                PreferenceManager.getDefaultSharedPreferences(myContext.getApplicationContext());
        String user = myPrefs.getString("email", "uh oh");
        user = user.substring(0, user.indexOf('@'));
        profilesRef = dbref.child("profiles").child(user).child("blocked");
        listener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myAdapter.notifyDataSetChanged();
                profiles.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String blocked_user = (String) snap.getValue();
                    assert blocked_user != null;
                    int index = blocked_user.indexOf('@');
                    if (index > -1) {
                        block_list.add(blocked_user);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        profilesRef.addListenerForSingleValueEvent(listener);
        return;
    }

}
