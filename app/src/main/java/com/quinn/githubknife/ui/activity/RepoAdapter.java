package com.quinn.githubknife.ui.activity;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.httpknife.github.Repository;

import java.util.List;

/**
 * Created by Quinn on 7/23/15.
 */
public class RepoAdapter extends
        RecyclerView.Adapter<RepoAdapter.ViewHolder>{
    private List<Repository> dataItems;
    private ImageLoader imageLoader;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions option;
    private RecycleItemClickListener itemClickListener;


    public RepoAdapter(List<Repository> dataItems){
        this.dataItems = dataItems;
        imageLoader = ImageLoader.getInstance();
        option = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent
                .getContext());
        final View sView = mInflater.inflate(R.layout.item_reposlist, parent,
                false);
        return new ViewHolder(sView,itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repository repo = dataItems.get(position);
        holder.name.setText(repo.getName());
        holder.starSum.setText(""+repo.getStargazers_count());
        holder.forkSum.setText(""+repo.getForks_count());
        holder.language.setText(repo.getLanguage());
        if(repo.isFork()){
            holder.iconType.setText(R.string.icon_fork);
        }else{
            holder.iconType.setText(R.string.icon_repo);
        }
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
        public TextView iconFork;
        public TextView forkSum;
        public TextView iconStar;
        public TextView starSum;
        public TextView description;
        public TextView language;


        public ViewHolder(View view,RecycleItemClickListener itemClickListener){
            super(view);
            this.mItemClickListener = itemClickListener;
            name = (TextView) view.findViewById(R.id.repoName);
            iconType = (TextView) view.findViewById(R.id.iconType);
            iconFork = (TextView) view.findViewById(R.id.iconFork);
            forkSum = (TextView) view.findViewById(R.id.forkSum);
            iconStar = (TextView) view.findViewById(R.id.iconStar);
            starSum = (TextView) view.findViewById(R.id.starSum);
            description = (TextView) view.findViewById(R.id.description);
            language = (TextView) view.findViewById(R.id.language);

            Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(),"octicons.ttf");
            iconType.setTypeface(typeface);
            iconStar.setTypeface(typeface);
            iconFork.setTypeface(typeface);
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
