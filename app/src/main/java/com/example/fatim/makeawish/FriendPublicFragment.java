package com.example.fatim.makeawish;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriendPublicFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.friend_public_layout, viewGroup, false);
        return view;

    }
}
