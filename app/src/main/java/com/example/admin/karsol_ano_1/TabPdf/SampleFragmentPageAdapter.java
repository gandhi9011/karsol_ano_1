package com.example.admin.karsol_ano_1.TabPdf;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.example.admin.karsol_ano_1.R;

/**
 * Created by Admin on 19-07-2017.
 */
public class SampleFragmentPageAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    private int tabIcons[] = {R.drawable.english,R.drawable.gujrati};

    public SampleFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return tabIcons.length;
    }


    @Override
    public int getPageIconResId(int position) {
        return tabIcons[position];
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EnglishFrag();
            case 1:
                return new GujratiFrag();

        }

        return null;
    }
}
