package com.example.lab1;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class TabNavigation  extends FragmentPagerAdapter{
    private final String[] tabTitles = new String[]{"List", "Blank", "Profile"};

    public TabNavigation(Context context, @NonNull FragmentManager fragmentManager) {
        super(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WelcomeFragment();
            case 1:
                return new BlankFragment();
            case 2:
                return new ProfileEditor();
        }
        return new BlankFragment();
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
