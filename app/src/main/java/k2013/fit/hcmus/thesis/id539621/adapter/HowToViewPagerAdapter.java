package k2013.fit.hcmus.thesis.id539621.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by thachdo on 5/12/2017.
 */

public class HowToViewPagerAdapter extends PagerAdapter {
    private int[] mColors;
    private int[] mImgs;
    private int[] mMsgs;

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        View view = inflater.inflate(R.layout.a_howto_viewpager, container, false);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
