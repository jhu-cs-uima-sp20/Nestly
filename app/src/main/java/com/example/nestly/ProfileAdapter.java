package com.example.nestly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class ProfileAdapter extends ArrayAdapter<User> {

    private int myResource;

    public ProfileAdapter(Context myContext, int myResource, List<User> profiles) {
        super(myContext, myResource, profiles);
        this.myResource = myResource;
    }

    @Override
    public View getView(int pos, View myConvertView, ViewGroup myParent) {

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

        TextView username = (TextView) profileView.findViewById(R.id.username);
        username.setText(curProfile.getUsername());

        return profileView;
    }
}
