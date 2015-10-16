package com.quinn.githubknife.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.quinn.iconlibrary.icons.OctIcon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.widget.AnimateFirstDisplayListener;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.BitmapUtils;
import com.quinn.githubknife.utils.TimeUtils;
import com.quinn.httpknife.github.Event;
import com.quinn.httpknife.github.GithubConstants;
import com.quinn.httpknife.payload.IssuePayload;
import com.quinn.httpknife.payload.MenberPayload;

import java.util.List;

/**
 * Created by Quinn on 7/25/15.
 */
public class EventAdapter extends
        RecyclerView.Adapter<EventAdapter.ViewHolder>{


    private Context context;

    private List<Event> dataItems;
    private ImageLoader imageLoader;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions option;
    private RecycleItemClickListener itemClickListener;


    public EventAdapter(Context context,List<Event> dataItems){
        this.context = context;
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
        return new ViewHolder(sView,itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = dataItems.get(position);
        imageLoader.displayImage(event.getActor().getAvatar_url(), holder.avatar, option, animateFirstListener);
        holder.happenTime.setText(TimeUtils.getRelativeTime(event.getCreated_at()));
        //getEventTypeIcon(holder.eventType, event.getType());

       // holder.event.setText(setItemTextAndIcon(position));
        setItemTextAndIcon(holder.event,holder.eventType,position);


    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }


    public void setItemTextAndIcon(TextView tv,ImageView img,int position){
        Event event = dataItems.get(position);
        String eventType = event.getType();
        if(eventType.equals(GithubConstants.WATCH_EVENT)){
            tv.setText(Html.fromHtml(event.getActor().getLogin() + " <b>starred</b> " + event.getRepo().getName()));
            BitmapUtils.setIconFont(context, img, OctIcon.STAR, R.color.theme_color);
        }else if(eventType.equals(GithubConstants.ForkEvent)){
            tv.setText(Html.fromHtml(event.getActor().getLogin() + " <b>forked</b> " + event.getRepo().getName()));
            BitmapUtils.setIconFont(context, img, OctIcon.FORK, R.color.theme_color);
        }else if(eventType.equals(GithubConstants.CreateEvent)){
            tv.setText(Html.fromHtml(event.getActor().getLogin() + " <b>created repo</b> " + event.getRepo().getName()));
            BitmapUtils.setIconFont(context, img, OctIcon.REPO, R.color.theme_color);
        }else if(eventType.equals(GithubConstants.PullRequestEvent)){
            tv.setText(Html.fromHtml(event.getActor().getLogin() + " <b>opened pull request</b> " + event.getRepo().getName()));
            BitmapUtils.setIconFont(context, img, OctIcon.PUSH, R.color.theme_color);
        }else if(eventType.equals(GithubConstants.MemberEvent)){
            MenberPayload payload = (MenberPayload)dataItems.get(position).getPayload();
            tv.setText(Html.fromHtml(event.getActor().getLogin() + " <b> add " + payload.getMember().getLogin()  + " to </b> " + event.getRepo().getName()));
            BitmapUtils.setIconFont(context, img, OctIcon.PERSON, R.color.theme_color);
        }else if(eventType.equals(GithubConstants.IssuesEvent)){
            IssuePayload payload = (IssuePayload)dataItems.get(position).getPayload();
            tv.setText(Html.fromHtml(event.getActor().getLogin() + " <b> " + payload.getAction()  + " issue </b> " + event.getRepo().getName() + "#" + payload.getIssue().getNumber()));
            if(payload.getAction().equals("opened")){
                BitmapUtils.setIconFont(context, img, OctIcon.ISSUE_OPNE, R.color.theme_color);
            }else if(payload.getAction().equals("closed")){
                BitmapUtils.setIconFont(context, img, OctIcon.ISSUE_CLOSE, R.color.theme_color);
            }
        }else if(eventType.equals(GithubConstants.PublicEvent)){
            tv.setText( Html.fromHtml(event.getActor().getLogin() + "<strong> made </strong>" + event.getRepo().getName() +" <strong> public </strong>" ));
            BitmapUtils.setIconFont(context, img, OctIcon.REPO, R.color.theme_color);
        }else {
            tv.setText("Unknown event type");   // I will add more eventtype later
        }
    }

    public void getEventTypeIcon(ImageView img,String eventType) {
        if (eventType.equals(GithubConstants.WATCH_EVENT)) {
            BitmapUtils.setIconFont(context,img, OctIcon.STAR,R.color.theme_color);
        } else if (eventType.equals(GithubConstants.ForkEvent)) {
            BitmapUtils.setIconFont(context,img, OctIcon.FORK,R.color.theme_color);
        } else if (eventType.equals(GithubConstants.CreateEvent)) {
            BitmapUtils.setIconFont(context,img, OctIcon.REPO,R.color.theme_color);
        } else if(eventType.equals(GithubConstants.PullRequestEvent)){
            BitmapUtils.setIconFont(context,img, OctIcon.PUSH,R.color.theme_color);
        } else if(eventType.equals(GithubConstants.MemberEvent)){
            BitmapUtils.setIconFont(context,img, OctIcon.PUSH,R.color.theme_color);
        }else if(eventType.equals(GithubConstants.IssuesEvent)){
            BitmapUtils.setIconFont(context,img, OctIcon.PUSH,R.color.theme_color);
        }else {
            BitmapUtils.setIconFont(context,img, OctIcon.STAR,R.color.theme_color);
        }
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecycleItemClickListener mItemClickListener;

        public ImageView avatar;
        public ImageView eventType;
        public TextView event;
        public TextView happenTime;

        public ViewHolder(View view,RecycleItemClickListener itemClickListener){
            super(view);
            avatar = (ImageView) view.findViewById(R.id.avatar);
            eventType = (ImageView) view.findViewById(R.id.eventType);
            happenTime = (TextView) view.findViewById(R.id.happenTime);
            event = (TextView) view.findViewById(R.id.event);
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

