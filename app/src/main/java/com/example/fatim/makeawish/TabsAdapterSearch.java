package com.example.fatim.makeawish;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabsAdapterSearch extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapterSearch(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                SearchUserFragment user = new SearchUserFragment();
                return user;
            case 1:
                SearchItmesFragment items = new SearchItmesFragment();
                return items;
            case 2:
                SearchCharityFragment charity = new SearchCharityFragment();
                return charity;
            default:
                return null;
        }
    }
}
