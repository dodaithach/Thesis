package k2013.fit.hcmus.thesis.id539621.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cpu60011-local on 05/07/2017.
 */

public class MusicRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    public static interface OnRecyclerItemClickListener {
        public void onItemClick(View v, int position);
    }

    private OnRecyclerItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public MusicRecyclerItemClickListener(Context context, final RecyclerView recyclerView,
                                          OnRecyclerItemClickListener listener) {
        mListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());

        if (child != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(child, rv.getChildPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
