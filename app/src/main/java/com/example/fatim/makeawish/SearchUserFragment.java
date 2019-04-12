package com.example.fatim.makeawish;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SearchUserFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
       // return inflater.inflate(R.layout.search_users_layout, viewGroup, false);
        final View view = inflater.inflate(R.layout.search_users_layout, viewGroup, false);
       Button btnSearchUser = (Button) view.findViewById(R.id.searchbtnUser);
       btnSearchUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

        return view;

    }
}
