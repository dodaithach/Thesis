package k2013.fit.hcmus.thesis.id539621.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by thachdo on 5/12/2017.
 */

public class HowToViewPagerAdapter extends PagerAdapter {
    private int[] mImgs = { R.drawable.a_howto_headphone,
                            R.drawable.a_howto_listen,
                            R.drawable.a_howto_headphone,
                            R.drawable.a_howto_headphone,
                            R.drawable.a_howto_headphone,
                            R.drawable.a_howto_headphone,
                            R.drawable.a_howto_headphone };

    private int[] mQuotes = { R.string.a_howto_quote_1,
                                R.string.a_howto_quote_2,
                                R.string.a_howto_quote_3,
                                R.string.a_howto_quote_4,
                                R.string.a_howto_quote_5,
                                R.string.a_howto_quote_6,
                                R.string.a_howto_quote_7 };

    private int[] mDetails = { R.string.a_howto_detail_1,
                                R.string.a_howto_detail_2,
                                R.string.a_howto_detail_3,
                                R.string.a_howto_detail_4,
                                R.string.a_howto_detail_5,
                                R.string.a_howto_detail_6,
                                R.string.a_howto_detail_7 };

    @Override
    public int getCount() {
        return mImgs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        View view = inflater.inflate(R.layout.a_howto_viewpager, container, false);

        ImageView img = (ImageView) view.findViewById(R.id.a_howto_viewpager_img);
        img.setBackgroundResource(mImgs[position]);

        TextView quote = (TextView) view.findViewById(R.id.a_howto_viewpager_quote);
        quote.setText(mQuotes[position]);

        TextView detail = (TextView) view.findViewById(R.id.a_howto_viewpager_detail);
        detail.setText(mDetails[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
