package com.example.nestly;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.android.gms.tasks.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GridFragment extends Fragment {

    private Context myContext;
    private ProfileAdapter myAdapter;
    private GridView grid;
    private MainActivity myMain;

    private ArrayList<User> profiles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_grid, container, false);

        myMain = (MainActivity) getActivity();
        myContext = getActivity().getApplicationContext();
        grid = root.findViewById(R.id.grid);

        profiles = new ArrayList<User>();
        profiles.add(new User("bob"));
        profiles.add(new User("bob"));
        profiles.add(new User("bob"));
        profiles.add(new User("bob"));
        profiles.add(new User("bob"));

        myAdapter = new ProfileAdapter(myContext, R.layout.profile_layout, profiles);

        return root;
    }

}
