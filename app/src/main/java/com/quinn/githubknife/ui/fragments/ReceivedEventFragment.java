package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quinn.githubknife.presenter.ReceivedEventPresenterImpl;
import com.quinn.githubknife.ui.adapter.EventAdapter;
import com.quinn.githubknife.ui.activity.UserInfoActivity;
import com.quinn.githubknife.ui.widget.RecycleItemClickListener;
import com.quinn.githubknife.utils.ToastUtils;
import com.quinn.httpknife.github.Event;
import com.quinn.httpknife.github.GithubConstants;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quinn on 7/16/15.
 */
public class ReceivedEventFragment extends BaseFragment implements RecycleItemClickListener {
    private EventAdapter adapter;

    public static ReceivedEventFragment getInstance(String user){
        ReceivedEventFragment receivedEventFragment = new ReceivedEventFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user", user);
        receivedEventFragment.setArguments(bundle);
        return receivedEventFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReceivedEventPresenterImpl(this.getActivity(),this);
        dataItems = new ArrayList<Event>();
        adapter = new EventAdapter(this.getActivity(),dataItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void setItems(List<?> items) {
        super.setItems(items);
        for(Object repo:items){
            dataItems.add((Event) repo);
        }
        loading = false;
        if(items.size() < GithubImpl.DEFAULT_PAGE_SIZE)
            haveMore = false;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        intoItem(position);
    }

    @Override
    public void intoItem(final int position) {
        Event event = (Event)dataItems.get(position);
        if(event.getOrg() != null && event.getType().equals(GithubConstants.MemberEvent)){
            ToastUtils.showMsg(this.getActivity(),"Orgnization...");
        }else {
            User user = (User) event.getActor();
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            UserInfoActivity.launch(ReceivedEventFragment.this.getActivity(), bundle);
        }
    }
}
