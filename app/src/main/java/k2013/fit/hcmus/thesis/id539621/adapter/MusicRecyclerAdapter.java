package k2013.fit.hcmus.thesis.id539621.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by cpu60011-local on 05/07/2017.
 */

public class MusicRecyclerAdapter extends RecyclerView.Adapter<MusicRecyclerAdapter.MusicViewHolder> {
    public static class MusicViewHolder extends RecyclerView.ViewHolder {
        public TextView mSongTitle;
        public View mDivider;

        public MusicViewHolder(View itemView) {
            super(itemView);

            mSongTitle = (TextView) itemView.findViewById(R.id.a_music_recycleritem_song_title);
            mDivider = itemView.findViewById(R.id.a_music_recycleritem_divider);
        }
    }

    private String[] mSongTitles;
    private int mSelectedIdx = -1;

    public MusicRecyclerAdapter(String[] songTitles) {
        mSongTitles = songTitles;
    }

    public void setSelectedIdx(int idx) {
        mSelectedIdx = idx;
    }

    public int getSelectedIdx() {
        return mSelectedIdx;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_music_recycleritem, parent, false);

        return new MusicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        holder.mSongTitle.setText(mSongTitles[position]);
        Log.d("mylog", mSongTitles[position]);

        if (position == mSelectedIdx) {
            holder.mSongTitle.setTextColor(holder.mSongTitle.getResources().getColor(R.color.themeAccent));
            holder.mSongTitle.setTypeface(null, Typeface.BOLD);
        } else {
            holder.mSongTitle.setTextColor(holder.mSongTitle.getResources().getColor(android.R.color.primary_text_light));
            holder.mSongTitle.setTypeface(null, Typeface.NORMAL);
        }

        if (position == mSongTitles.length - 1) {
            holder.mDivider.setVisibility(View.INVISIBLE);
        } else {
            holder.mDivider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mSongTitles.length;
    }
}
