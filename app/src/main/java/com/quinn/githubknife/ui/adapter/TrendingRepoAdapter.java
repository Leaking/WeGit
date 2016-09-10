package com.quinn.githubknife.ui.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.httpknife.github.Repository;
import com.quinn.httpknife.github.TrendingRepo;

import java.util.List;

/**
 * Created by Quinn on 9/10/16.
 */
public class TrendingRepoAdapter extends
        RecyclerView.Adapter<TrendingRepoAdapter.ViewHolder>{
    private List<TrendingRepo> dataItems;

    private RecycleItemClickListener itemClickListener;


    public TrendingRepoAdapter(List<TrendingRepo> dataItems){
        this.dataItems = dataItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent
                .getContext());
        final View sView = mInflater.inflate(R.layout.item_trending_reposlist, parent,
                false);
        return new ViewHolder(sView,itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrendingRepo repo = dataItems.get(position);
        holder.name.setText(repo.getName());
        int rsid = R.string.since_today;
        switch (repo.getSince_type()) {
            case SINCE_DAY:
                rsid = R.string.since_today;
                break;
            case SINCE_WEEK:
                rsid = R.string.since_weekly;
                break;
            case SINCE_MONTH:
                rsid = R.string.since_month;
                break;
        }
        holder.addStar.setText(repo.getAddStars() + " "+  holder.addStar.getContext().getString(rsid));
        holder.language.setText(repo.getLanguage());
        holder.iconType.setText(R.string.icon_repo);
        holder.description.setText(repo.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecycleItemClickListener mItemClickListener;

        public TextView name;
        public TextView iconType;
        public TextView addStar;
        public TextView description;
        public TextView language;


        public ViewHolder(View view,RecycleItemClickListener itemClickListener){
            super(view);
            this.mItemClickListener = itemClickListener;
            name = (TextView) view.findViewById(R.id.repoFullName);
            iconType = (TextView) view.findViewById(R.id.iconType);
            addStar = (TextView) view.findViewById(R.id.addStar);
            description = (TextView) view.findViewById(R.id.description);
            language = (TextView) view.findViewById(R.id.language);

            Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(),"octicons.ttf");
            iconType.setTypeface(typeface);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null){
                mItemClickListener.onItemClick(v,getPosition());
            }
        }
    }

    public void setOnItemClickListener(RecycleItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
