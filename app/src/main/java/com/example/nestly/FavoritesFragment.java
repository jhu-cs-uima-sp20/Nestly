package com.example.nestly;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {

    private Context myContext;
    private ProfileAdapter myAdapter;
    private GridView grid;
    private MainActivity main;

    private ArrayList<User> profiles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grid, container, false);
        main = (MainActivity) getActivity();
        myContext = getActivity().getApplicationContext();
        grid = root.findViewById(R.id.grid);

        profiles = new ArrayList<>();

        myAdapter = new ProfileAdapter(myContext, R.layout.profile_layout, profiles);
        grid.setAdapter(myAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                main.viewProfile(position);
            }
        });

        return root;
    }

}
