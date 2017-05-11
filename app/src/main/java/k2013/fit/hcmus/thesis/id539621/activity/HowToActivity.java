package k2013.fit.hcmus.thesis.id539621.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;
import k2013.fit.hcmus.thesis.id539621.adapter.HowToViewPagerAdapter;

/**
 * Created by thachdo on 5/11/2017.
 */

public class HowToActivity extends BaseActivity {
    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private TextView[] mDots;
    private int[] mHowToLayouts;
    private Button mBtnSkip;
    private Button mBtnAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_howto);

        mViewPager = (ViewPager) findViewById(R.id.howto_viewpager);
        mDotsLayout = (LinearLayout) findViewById(R.id.howto_dots);
        mBtnAction = (Button) findViewById(R.id.howto_btnAction);
        mBtnSkip = (Button) findViewById(R.id.howto_btnSkip);

        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        boolean isOpened = sp.getBoolean(getString(R.string.sp_is_opened), false);

        if (isOpened) {

        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(getString(R.string.sp_is_opened), true);
        }

        HowToViewPagerAdapter adapter = new HowToViewPagerAdapter();
        mViewPager.setAdapter(adapter);
    }
}
