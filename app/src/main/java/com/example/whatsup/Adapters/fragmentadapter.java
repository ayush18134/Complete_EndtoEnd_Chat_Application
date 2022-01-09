package com.example.whatsup.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsup.Fragments.ChatFragment;
import com.example.whatsup.Fragments.ProfileFragment;

public class fragmentadapter extends FragmentPagerAdapter {

    public fragmentadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==1){
            return new ProfileFragment();
        }
        else{
            return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return "CHATS";
        else if(position==1) return "PROFILE";
        return "";
    }
}
