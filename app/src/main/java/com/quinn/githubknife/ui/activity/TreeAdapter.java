package com.quinn.githubknife.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.quinn.iconlibrary.icons.OctIcon;
import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.BitmapUtils;
import com.quinn.httpknife.github.TreeItem;

import java.util.List;

/**
 * Created by Quinn on 7/19/15.
 */
public class TreeAdapter extends
        RecyclerView.Adapter<TreeAdapter.ViewHolder>{

    private List<TreeItem> dataItems;
    private RecycleItemClickListener itemClickListener;

    public TreeAdapter(List<TreeItem> dataItems){
        this.dataItems = dataItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent
                .getContext());
        final View sView = mInflater.inflate(R.layout.item_treelist, parent,
                false);
        return new ViewHolder(sView,itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TreeItem treeItem = dataItems.get(position);
        holder.treeItemName.setText(treeItem.getPath());
        if(treeItem.getMode().equals(TreeItem.MODE_BLOB)){
            BitmapUtils.setIconFont(holder.treeItemImg.getContext(), holder.treeItemImg, OctIcon.FILE,R.color.theme_color);
        }else{
            BitmapUtils.setIconFont(holder.treeItemImg.getContext(), holder.treeItemImg, OctIcon.FOLDER,R.color.theme_color);
        }
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecycleItemClickListener mItemClickListener;

        public ImageView treeItemImg;
        public TextView treeItemName;


        public ViewHolder(View view,RecycleItemClickListener itemClickListener){
            super(view);
            treeItemImg = (ImageView) view.findViewById(R.id.treeItemImg);
            treeItemName = (TextView) view.findViewById(R.id.treeItemName);
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
