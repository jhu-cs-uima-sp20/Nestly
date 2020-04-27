package com.example.nestly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ValueEventListener listener;

    private TextView introvert;
    private TextView inTheRoom;
    private TextView timeSpent;
    private TextView bringFriends;
    private TextView wakeUp;
    private TextView sleep;

    private SharedPreferences myPrefs;

    public HabitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitsFragment newInstance(String param1, String param2) {
        HabitsFragment fragment = new HabitsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_habits, container, false);

        Context context = getActivity();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor peditor = sp.edit();
        String view_email = sp.getString("view_email", "jhed@jhu.edu");
        view_email = view_email.substring(0,view_email.indexOf('@'));

        introvert = v.findViewById(R.id.introvert);
        inTheRoom = v.findViewById(R.id.inTheRoom);
        timeSpent = v.findViewById(R.id.timeSpent);
        bringFriends = v.findViewById(R.id.bringFriends);
        wakeUp = v.findViewById(R.id.wakeUp);
        sleep = v.findViewById(R.id.sleep);

        DatabaseReference reference =
                FirebaseDatabase.getInstance().getReference().child("profiles").child(view_email);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> curUserMap = (HashMap<String, Object>) dataSnapshot.getValue();
                assert curUserMap != null;
                ArrayList<String> habits_answers= (ArrayList<String>) curUserMap.get("habits_answers");
                introvert.setText("I consider myself: " + habits_answers.get(0) + "ed");
                String listActivities = "";
                String[] activities = new String[]{"do general homework","hang out with friends","cook food","eat food","study for exams","throw parties"};
                for(int i = 0; i < 6;i++) {
                    if(habits_answers.get(i+1).equals("checked")) {
                        listActivities = listActivities + activities[i] + ", ";
                    }
                }
                if (!listActivities.equals("")) {
                    listActivities = listActivities.substring(0, listActivities.length() - 2);
                }
                inTheRoom.setText("I plan to: " + listActivities + " in the room");
                timeSpent.setText("Not including sleeping, I plan to spend an average of " + habits_answers.get(7) + " hours in the room");
                bringFriends.setText("I plan to bring friends over an average of " + habits_answers.get(10) + " times a week");
                wakeUp.setText("I usually wake up around " + habits_answers.get(9));
                sleep.setText("I usually sleep around " + habits_answers.get(8));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(listener);


        return v;
    }
}
