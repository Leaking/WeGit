package com.quinn.githubknife.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quinn.githubknife.R;
import com.quinn.httpknife.github.User;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DrawerFragment extends Fragment implements AdapterView.OnItemClickListener {

    private NavigationDrawerSelectCallbacks selectCallbacks;
    private NaviagtionDawerCloseCallbacks closeCallbacks;


    @Bind(R.id.drawerList)
    ListView listview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, view);
        listview.setAdapter(new DrawerAdapter(this.getActivity(),new User()));
        View header = getActivity().getLayoutInflater().inflate(R.layout.header_drawerlist, listview, false);
        listview.addHeaderView(header);
        listview.setOnItemClickListener(this);
        return view;
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            selectCallbacks = (NavigationDrawerSelectCallbacks)activity;
            closeCallbacks = (NaviagtionDawerCloseCallbacks) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement NavigationDrawerSelectCallbacks and NaviagtionDawerCloseCallbacks");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectCallbacks.onNavigationDrawerItemSelected(position);
        closeCallbacks.onNavigationDrawerClose();
    }


    public static interface NavigationDrawerSelectCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }

    public static interface  NaviagtionDawerCloseCallbacks {
        void onNavigationDrawerClose();
    }



}
