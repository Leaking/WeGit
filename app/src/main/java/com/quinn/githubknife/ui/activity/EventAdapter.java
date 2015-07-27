package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quinn.githubknife.R;
import com.quinn.httpknife.github.Event;

import java.util.List;

/**
 * Created by Quinn on 7/25/15.
 */
public class EventAdapter extends
        RecyclerView.Adapter<EventAdapter.ViewHolder>{

    public final static String[] EVENT_TYPE_ARRAY = {"WatchEvent","ForkEvent","CreateEvent","PushEvent"};

    private List<Event> dataItems;
    private ImageLoader imageLoader;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions option;
    private Context context;


    public EventAdapter(List<Event> dataItems){
        this.dataItems = dataItems;
        this.imageLoader = ImageLoader.getInstance();
        this.option = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent
                .getContext());
        final View sView = mInflater.inflate(R.layout.item_eventlist, parent,
                false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = dataItems.get(position);
        imageLoader.displayImage(event.getActor().getAvatar_url(),holder.avatar,option,animateFirstListener);
        holder.happenTime.setText(event.getCreated_at().toString());
        holder.eventType.setText(getEventTypeIcon(event.getType()));
        if(!holder.event.equals(EVENT_TYPE_ARRAY[3])){
            holder.event.setText(Html.fromHtml(event.getActor().getLogin() + " <b>" + getPureEventType(event.getType()) + "</b> " + event.getRepo().getName()));
        }else{
            holder.event.setText(Html.fromHtml(event.getActor().getLogin() + " <b>" + getPureEventType(event.getType()) + "</b> " + event.getRepo().getName()));

        }



    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }


    public String getPureEventType(String eventType){
        if(eventType.equals(EVENT_TYPE_ARRAY[0])){
            return "starred";
        }else if(eventType.equals(EVENT_TYPE_ARRAY[1])){
            return "forked";
        }else if(eventType.equals(EVENT_TYPE_ARRAY[2])){
            return "created repo";
        }else{
            return ",,";
        }
    }

    public int getEventTypeIcon(String eventType) {
        if (eventType.equals(EVENT_TYPE_ARRAY[0])) {
            return R.string.icon_star;
        } else if (eventType.equals(EVENT_TYPE_ARRAY[1])) {
            return R.string.icon_fork;
        } else if (eventType.equals(EVENT_TYPE_ARRAY[2])) {
            return R.string.icon_repo;
        } else if(eventType.equals(EVENT_TYPE_ARRAY[3])){
            return R.string.icon_star;
        } else {
            return R.string.icon_push;
        }
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView avatar;
        public TextView eventType;
        public TextView event;
        public TextView happenTime;

        public ViewHolder(View view){
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            eventType = (TextView) view.findViewById(R.id.eventType);
            happenTime = (TextView) view.findViewById(R.id.happenTime);
            event = (TextView) view.findViewById(R.id.event);

            Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(),"octicons.ttf");
            eventType.setTypeface(typeface);
        }
    }



}
