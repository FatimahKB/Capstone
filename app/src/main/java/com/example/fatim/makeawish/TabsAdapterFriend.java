package com.example.fatim.makeawish;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapterFriend  extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapterFriend(FragmentManager fm, int NoofTabs){
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
            case 1:
                FriendPrivateFragment privateLists = new FriendPrivateFragment();
                return privateLists;
            case 0:
                FriendPublicFragment publicList = new FriendPublicFragment();
                return publicList;
            default:
                return null;
        }
    }
}
