package com.example.nestly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class ProfileAdapter extends ArrayAdapter<User> {

    private int myResource;

    public ProfileAdapter(Context myContext, int myResource, List<User> profiles) {
        super(myContext, myResource, profiles);
        this.myResource = myResource;
    }

    @NonNull
    @Override
    public View getView(int pos, View myConvertView, @NonNull ViewGroup myParent) {

        ConstraintLayout profileView;
        User curProfile = getItem(pos);

        if (myConvertView == null) {
            profileView = new ConstraintLayout(getContext());
            String myInflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(myInflater);
            li.inflate(this.myResource, profileView, true);
        }
        else {
            profileView = (ConstraintLayout) myConvertView;
        }

        TextView username = profileView.findViewById(R.id.username);
        assert curProfile != null;
        username.setText(curProfile.getUsername());
        TextView name = profileView.findViewById(R.id.given_name);
        name.setText(curProfile.getName());
        TextView matching = profileView.findViewById(R.id.percent_val);
        matching.setText(curProfile.getMatching());
        return profileView;
    }
}
