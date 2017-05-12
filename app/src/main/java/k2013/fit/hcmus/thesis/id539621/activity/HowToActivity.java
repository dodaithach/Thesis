package k2013.fit.hcmus.thesis.id539621.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
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
    private Button mBtnSkip;
    private Button mBtnAction;

    private HowToViewPagerAdapter mAdapter;

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

        mBtnAction.setOnClickListener(mOnNext);

        if (isOpened) {
            Intent intent = getIntent();
            boolean isFromHelp = intent.getBooleanExtra(getString(R.string.a_howto_intent_from_help), false);

            if (!isFromHelp) {
                launchMainActivity();
            } else {
                mBtnSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                mAdapter = new HowToViewPagerAdapter();
                mViewPager.setAdapter(mAdapter);
                mViewPager.setOnPageChangeListener(mPageChangeListener);

                addBottomDots(0);
            }
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(getString(R.string.sp_is_opened), true);
            editor.commit();

            mBtnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchMainActivity();
                }
            });

            mAdapter = new HowToViewPagerAdapter();
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOnPageChangeListener(mPageChangeListener);

            addBottomDots(0);
        }
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    private final View.OnClickListener mOnNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int nextPage = (mViewPager.getCurrentItem() + 1) % mAdapter.getCount();
            mViewPager.setCurrentItem(nextPage, true);
        }
    };

    private final View.OnClickListener mOnGotIt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            boolean isFromHelp = intent.getBooleanExtra(getString(R.string.a_howto_intent_from_help), false);

            if (!isFromHelp) {
                launchMainActivity();
            }

            finish();
        }
    };

    private final ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == mAdapter.getCount() - 1) {
                mBtnAction.setOnClickListener(mOnGotIt);
                mBtnAction.setText(getString(R.string.a_howto_btn_gotit));
                mBtnSkip.setVisibility(View.GONE);
            } else {
                mBtnAction.setOnClickListener(mOnNext);
                mBtnAction.setText(getString(R.string.a_howto_btn_next));
                mBtnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void addBottomDots(int curPage) {
        TextView[] dots = new TextView[mAdapter.getCount()];

        mDotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.themeDark));
            mDotsLayout.addView(dots[i]);
        }

        dots[curPage].setTextColor(getResources().getColor(R.color.themeLight));
    }
}
