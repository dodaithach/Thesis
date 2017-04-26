package k2013.fit.hcmus.thesis.id539621.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import k2013.fit.hcmus.thesis.id539621.R;

/**
 * Created by cpu60011-local on 25/04/2017.
 */

public class GameSelectionAdapter extends RecyclerView.Adapter<GameSelectionAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mLevel;
        public TextView mTime;
        public TextView mBackgroundSound;
        public TextView mDistractingSound;
        public Button mButton;
        public View mDivider;

        public ViewHolder(View itemView) {
            super(itemView);

            mLevel = (TextView) itemView.findViewById(R.id.gameselection_recycleritem_level);
            mTime = (TextView) itemView.findViewById(R.id.gameselection_recycleritem_time);
            mBackgroundSound = (TextView) itemView.findViewById(R.id.gameselection_recycleritem_backgroundsound);
            mDistractingSound = (TextView) itemView.findViewById(R.id.gameselection_recycleritem_distractingsound);
            mButton = (Button) itemView.findViewById(R.id.gameselection_recycleritem_btn);
            mDivider = itemView.findViewById(R.id.gameselection_recycleritem_divider);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_game_selection, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.mLevel.getContext();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/pacifico-regular.ttf");
        holder.mLevel.setTypeface(tf);

        if (position == 4) {
            holder.mDivider.setVisibility(View.GONE);
        }

        holder.mLevel.setText("" + (position + 1));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
