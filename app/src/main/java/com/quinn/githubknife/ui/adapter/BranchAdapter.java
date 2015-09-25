package com.quinn.githubknife.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.httpknife.github.Branch;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Quinn on 9/25/15.
 */
public class BranchAdapter extends
        RecyclerView.Adapter<BranchAdapter.ViewHolder>{

    private List<Branch> dataItems;
    private RecycleItemClickListener itemClickListener;

    public BranchAdapter(List<Branch> dataItems){
        this.dataItems = dataItems;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent
                .getContext());
        final View sView = mInflater.inflate(R.layout.item_userslist, parent,
                false);
        return new ViewHolder(sView,itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.name.setText(dataItems.get(position).getLogin());
//        holder.avatar.setImageResource(R.mipmap.ic_headset);
//        imageLoader.displayImage(dataItems.get(position).getAvatar_url(),holder.avatar,option,animateFirstListener);
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecycleItemClickListener mItemClickListener;

        public CircleImageView avatar;
        public TextView name;


        public ViewHolder(View view,RecycleItemClickListener itemClickListener){
            super(view);
            avatar = (CircleImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
            mItemClickListener = itemClickListener;
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
