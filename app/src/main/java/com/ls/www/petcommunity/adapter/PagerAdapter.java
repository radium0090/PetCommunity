package com.ls.www.petcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ls.www.petcommunity.fragment.TopicTabFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int tabNum;

    public PagerAdapter(FragmentManager fm, int tabNum) {
        super(fm);
        this.tabNum = tabNum;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                TopicTabFragment tab1 = new TopicTabFragment();
                return tab1;
            case 1:
                TopicTabFragment tab2 = new TopicTabFragment();
//                HotTabFragment tab2=new HotTabFragment();
                return tab2;
            case 2:
                TopicTabFragment tab3 = new TopicTabFragment();
//                TabFragment3 tab3=new TabFragment3();
                return tab3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabNum;
    }
}
