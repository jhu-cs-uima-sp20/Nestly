package com.example.nestly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitsFragmentUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitsFragmentUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView introvert;
    private TextView inTheRoom;
    private TextView timeSpent;
    private TextView bringFriends;
    private TextView wakeUp;
    private TextView sleep;

    private SharedPreferences myPrefs;

    public HabitsFragmentUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HabitsFragmentUser.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitsFragmentUser newInstance(String param1, String param2) {
        HabitsFragmentUser fragment = new HabitsFragmentUser();
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
        View v = inflater.inflate(R.layout.fragment_habits_user, container, false);

        Context context = getActivity();

        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        introvert = v.findViewById(R.id.introvert);
        inTheRoom = v.findViewById(R.id.inTheRoom);
        timeSpent = v.findViewById(R.id.timeSpent);
        bringFriends = v.findViewById(R.id.bringFriends);
        wakeUp = v.findViewById(R.id.wakeUp);
        sleep = v.findViewById(R.id.sleep);

        introvert.setText("I consider myself: " + myPrefs.getString("intro/extrovert", "introvert") + "ed");
        String listActivities = "";
        boolean activity = myPrefs.getBoolean("check1", false);
        if (activity) {
            listActivities += "do general homework, ";
        }
        activity = myPrefs.getBoolean("check2", false);
        if (activity) {
            listActivities += "hang out with friends, ";
        }
        activity = myPrefs.getBoolean("check3", false);
        if (activity) {
            listActivities += "cook food, ";
        }
        activity = myPrefs.getBoolean("check4", false);
        if (activity) {
            listActivities += "eat food, ";
        }
        activity = myPrefs.getBoolean("check5", false);
        if (activity) {
            listActivities += "study for exams, ";
        }
        activity = myPrefs.getBoolean("check6", false);
        if (activity) {
            listActivities += "throw parties, ";
        }

        inTheRoom.setText("I plan to: " + listActivities + " in the room");
        timeSpent.setText("Not including sleeping, I plan to spend an average of " + myPrefs.getString("room_time", "0") + " hours in the room");
        bringFriends.setText("I plan to bring friends over an average of " + myPrefs.getString("bring_friends", "0") + " times a week");
        wakeUp.setText("I usually wake up around " + myPrefs.getString("wakeUp_time", "8am"));
        sleep.setText("I usually sleep around " + myPrefs.getString("sleep_time", "12am"));

        return v;
    }
}
