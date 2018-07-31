package com.ls.www.petcommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ls.www.petcommunity.fragment.HotTabFragment;

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
                HotTabFragment tab1 = new HotTabFragment();
                return tab1;
            case 1:
                HotTabFragment tab2 = new HotTabFragment();
//                TabFragment2 tab2=new TabFragment2();
                return tab2;
            case 2:
                HotTabFragment tab3 = new HotTabFragment();
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
