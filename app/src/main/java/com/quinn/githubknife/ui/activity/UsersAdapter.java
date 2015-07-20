package com.quinn.githubknife.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quinn.githubknife.R;
import com.quinn.httpknife.github.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Quinn on 7/19/15.
 */
public class UsersAdapter extends
        RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    private List<User> dataItems;
    private ImageLoader imageLoader;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions option;


    public UsersAdapter(List<User> dataItems){
        this.dataItems = dataItems;
        imageLoader = ImageLoader.getInstance();
        option = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).build();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent
                .getContext());
        final View sView = mInflater.inflate(R.layout.item_userslist, parent,
                false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(dataItems.get(position).getLogin());
        holder.avatar.setImageResource(R.mipmap.ic_headset);
        imageLoader.displayImage(dataItems.get(position).getAvatar_url(),holder.avatar,option,animateFirstListener);
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public CircleImageView avatar;
        public TextView name;


        public ViewHolder(View view){
            super(view);
            avatar = (CircleImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
        }


    }


}
