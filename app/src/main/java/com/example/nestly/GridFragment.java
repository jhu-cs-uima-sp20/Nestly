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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
    static ArrayList<User> profiles;

    private String gender;
    private String major;

    private int my;
    private int check;

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
                String filter = myPrefs.getString("filter", "none");
                String myfilter = myPrefs.getString(filter, "none"); //major, gender, etc.
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    HashMap<String, Object> curUserMap = (HashMap<String, Object>) snap.getValue();
                    assert curUserMap != null;
                    String checkUser = (String) curUserMap.get("username");
                    String password = (String) curUserMap.get("password");
                    Boolean hidden = (Boolean) (curUserMap.get("hidden").equals(true));
                    String userYear = (String) curUserMap.get("year");
                    gender = (String) curUserMap.get("gender");
                    major = (String) curUserMap.get("major");
                    String checkFilter = (String) curUserMap.get(filter);
                    ArrayList<String> habits_answers= (ArrayList<String>) curUserMap.get("habits_answers");
                    ArrayList<String> situations_answers= (ArrayList<String>) curUserMap.get("situations_answers");
                    if (checkFilter == null)
                        checkFilter = "none";

                    assert checkUser != null;

                    if (userYear == null)
                        userYear = year;
                    if (!hidden && !block_list.contains(checkUser)) {

//                        if ((filter.equals("gender") || filter.equals("major"))
//                                && checkFilter.equals(myfilter)) {
//
//                            if (year.equals("Junior") || year.equals("Senior")) {
//                                if (userYear.equals("Junior") || userYear.equals("Senior")) {
//                                    User temp = new User(checkUser, password);
//                                    temp.setHabits_answers(habits_answers);
//                                    temp.setSituations_answers(situations_answers);
//                                    profiles.add(temp);
//                                }
//                            } else if (year.equals(userYear)) {
//                                User temp = new User(checkUser, password);
//                                temp.setHabits_answers(habits_answers);
//                                temp.setSituations_answers(situations_answers);
//                                profiles.add(temp);
//                            }
//                        } else
                        if (filter.equals("introvert")) {
                            checkFilter = habits_answers.get(0);
                            myfilter = myPrefs.getString("intro/extrovert", "introvert");
                            if (checkFilter.equals(myfilter)) {
                                if (year.equals("Junior") || year.equals("Senior")) {
                                    if (userYear.equals("Junior") || userYear.equals("Senior")) {
                                        User temp = new User(checkUser, password);
                                        temp.setHabits_answers(habits_answers);
                                        temp.setSituations_answers(situations_answers);
                                        temp.setGender(gender);
                                        temp.setMajor(major);
                                        profiles.add(temp);
                                    }
                                } else if (year.equals(userYear)) {
                                    User temp = new User(checkUser, password);
                                    temp.setHabits_answers(habits_answers);
                                    temp.setSituations_answers(situations_answers);
                                    temp.setGender(gender);
                                    temp.setMajor(major);
                                    profiles.add(temp);
                                }
                            }
                        } else if (filter.equals("sleep") || filter.equals("wake") ||
                                filter.equals("time_spent") || filter.equals("people_over")) {
                            if (filter.equals("sleep")) {
                                checkFilter = habits_answers.get(9);
                                myfilter = myPrefs.getString("sleep_time", "12am");
                            } else if (filter.equals("wake")) {
                                checkFilter = habits_answers.get(8);
                                myfilter = myPrefs.getString("wakeUp_time", "8am");
                            } else if (filter.equals("people_over")) {
                                checkFilter = habits_answers.get(10);
                                myfilter = myPrefs.getString("bring_friends", "5");
                            } else {
                                checkFilter = habits_answers.get(7);
                                myfilter = myPrefs.getString("room_time", "5");
                            }

                            int mindex = myfilter.indexOf("m");
                            int cindex = checkFilter.indexOf("m");

                            if (mindex != -1) {
                                my = Integer.parseInt(myfilter.substring(0, mindex - 1));
                                if (myfilter.substring(mindex - 1, myfilter.length()).equals("pm")) {
                                    my = 0 + (12 - my);
                                }
                            } else {
                                mindex = myfilter.indexOf("+");
                                if (mindex != -1) {
                                    my = Integer.parseInt(myfilter.substring(0, mindex));
                                } else {
                                    my = Integer.parseInt(myfilter);
                                }
                            }
                            if (cindex != -1) {
                                check = Integer.parseInt(checkFilter.substring(0, cindex - 1));
                                if (checkFilter.substring(cindex - 1, checkFilter.length()).equals("pm")) {
                                    check = 0 + (12 - check);
                                }
                            } else {
                                cindex = checkFilter.indexOf("+");
                                if (cindex != -1) {
                                    check = Integer.parseInt(checkFilter.substring(0, cindex));
                                } else {
                                    check = Integer.parseInt(checkFilter);
                                }
                            }

                            if (year.equals("Junior") || year.equals("Senior")) {
                                if (userYear.equals("Junior") || userYear.equals("Senior")) {
                                    User temp = new User(checkUser, password);
                                    temp.setHabits_answers(habits_answers);
                                    temp.setSituations_answers(situations_answers);
                                    temp.setFilter(Math.abs(my - check));
                                    temp.setGender(gender);
                                    temp.setMajor(major);
                                    profiles.add(temp);
                                }
                            } else if (year.equals(userYear)) {
                                User temp = new User(checkUser, password);
                                temp.setHabits_answers(habits_answers);
                                temp.setSituations_answers(situations_answers);
                                temp.setFilter(Math.abs(my - check));
                                temp.setGender(gender);
                                temp.setMajor(major);
                                profiles.add(temp);
                            }
                        }
                        else {
                            if (year.equals("Junior") || year.equals("Senior")) {
                                if (userYear.equals("Junior") || userYear.equals("Senior")) {
                                    User temp = new User(checkUser, password);
                                    temp.setHabits_answers(habits_answers);
                                    temp.setSituations_answers(situations_answers);
                                    temp.setFilter(Math.abs(my - check));
                                    temp.setGender(gender);
                                    temp.setMajor(major);
                                    profiles.add(temp);
                                }
                            } else if (year.equals(userYear)) {
                                User temp = new User(checkUser, password);
                                temp.setHabits_answers(habits_answers);
                                temp.setSituations_answers(situations_answers);
                                temp.setFilter(Math.abs(my - check));
                                temp.setGender(gender);
                                temp.setMajor(major);
                                profiles.add(temp);
                            }
                        }
                    }

                }


                if (filter.equals("major")) {
                    for (Iterator<User> iterator = profiles.iterator(); iterator.hasNext(); ) {
                        User u = iterator.next();
                        if (!u.getMajor().equals(myPrefs.getString("major", "undeclared"))) {
                            iterator.remove();
                        }
                    }
                } else if (filter.equals("gender")) {
                    for (Iterator<User> iterator = profiles.iterator(); iterator.hasNext(); ) {
                        User u = iterator.next();
                        if (!u.getGender().equals(myPrefs.getString("gender", "Other"))) {
                            iterator.remove();
                        }
                    }
                }

                Collections.sort(profiles);

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
