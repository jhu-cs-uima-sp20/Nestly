package com.example.nestly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceManager;

import android.util.Log;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
                    myAdapter.notifyDataSetChanged();
                    profiles.clear();
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        HashMap<String, Object> curUserMap = (HashMap<String, Object>) snap.getValue();
                        assert curUserMap != null;
                        String checkUser = (String) curUserMap.get("username");
                        String password = (String) curUserMap.get("password");
                        Boolean hidden = (Boolean) (curUserMap.get("hidden").equals(true));
                        String userYear = (String) curUserMap.get("year");
                        ArrayList<String> habits_answers= (ArrayList<String>) curUserMap.get("habits_answers");
                        ArrayList<String> situations_answers= (ArrayList<String>) curUserMap.get("situations_answers");

                        assert checkUser != null;

                        if (userYear == null)
                            userYear = year;
                        if (!hidden && !block_list.contains(checkUser)) {

                            if(year.equals("Junior") || year.equals("Senior")) {
                                if(userYear.equals("Junior") || userYear.equals("Senior")) {
                                    User temp = new User(checkUser, password);
                                    temp.setHabits_answers(habits_answers);
                                    temp.setSituations_answers(situations_answers);
                                    profiles.add(temp);
                                }
                            }


                            else if(year.equals(userYear)) {
                                User temp = new User(checkUser, password);
                                temp.setHabits_answers(habits_answers);
                                temp.setSituations_answers(situations_answers);
                                profiles.add(temp);
                            }
                        }
                    }
                    calculate_percentage(profiles);
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
            });
        }

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

    private void calculate_percentage(ArrayList<User> profiles) {
         List<String> h1 = Arrays.asList(getResources().getStringArray(R.array.identify_as));
         List<String> h2 = Arrays.asList(getResources().getStringArray(R.array.wake_times));
         List<String> h3 = Arrays.asList(getResources().getStringArray(R.array.time_in_room));
         List<String> h4 = Arrays.asList(getResources().getStringArray(R.array.sleep_times));
         List<String> h5 = Arrays.asList(getResources().getStringArray(R.array.frequency));
         List<String> q1 = Arrays.asList(getResources().getStringArray(R.array.q1));
         List<String> q2 = Arrays.asList(getResources().getStringArray(R.array.q2));
         List<String> q3 = Arrays.asList(getResources().getStringArray(R.array.q3));
         List<String> q4 = Arrays.asList(getResources().getStringArray(R.array.q4));
         List<String> q5 = Arrays.asList(getResources().getStringArray(R.array.q5));
         List<String> q6 = Arrays.asList(getResources().getStringArray(R.array.q6));
        List<String> user_habits = new ArrayList<>();
        List<String> user_situations = new ArrayList<>();
        for(User user : profiles) {
            if(user.getUsername().equals(username)) {
                user_habits = user.getHabits_answers();
                user_situations = user.getSituations_answers();
                profiles.remove(user);
                break;
            }
        }
        for(User user : profiles) {
            int diff = 0;
            List<String> habits = user.getHabits_answers();
            List<String> situations = user.getSituations_answers();
            diff = diff + Math.abs(h1.indexOf(habits.get(0))-h1.indexOf(user_habits.get(0)));
            diff = diff + Math.abs(h2.indexOf(habits.get(7))-h2.indexOf(user_habits.get(7)));
            diff = diff + Math.abs(h3.indexOf(habits.get(8))-h3.indexOf(user_habits.get(8)));
            diff = diff + Math.abs(h4.indexOf(habits.get(9))-h4.indexOf(user_habits.get(9)));
            diff = diff + Math.abs(h5.indexOf(habits.get(10))-h5.indexOf(user_habits.get(10)));
            diff = diff + Math.abs(q1.indexOf(situations.get(0))-q1.indexOf(user_situations.get(0)));
            diff = diff + Math.abs(q2.indexOf(situations.get(1))-q2.indexOf(user_situations.get(1)));
            diff = diff + Math.abs(q3.indexOf(situations.get(2))-q3.indexOf(user_situations.get(2)));
            diff = diff + Math.abs(q4.indexOf(situations.get(3))-q4.indexOf(user_situations.get(3)));
            diff = diff + Math.abs(q5.indexOf(situations.get(4))-q5.indexOf(user_situations.get(4)));
            diff = diff + Math.abs(q6.indexOf(situations.get(5))-q6.indexOf(user_situations.get(5)));
            user.setMatching(Math.round(((80.0-diff)/80.0)*100)+"%");
        }
    }

}
