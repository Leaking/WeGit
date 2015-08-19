package com.quinn.githubknife.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.quinn.iconlibrary.icons.OctIcon;
import com.quinn.githubknife.R;
import com.quinn.githubknife.utils.BitmapUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Quinn on 8/19/15.
 */
public class SearchDescriptionFragment extends Fragment {


    @Bind(R.id.searchImg)
    ImageView img;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_search_description, container, false);
        ButterKnife.bind(this, view);
        BitmapUtils.setIconFont(this.getActivity(),img, OctIcon.SEARCH,R.color.theme_color);



        return view;
    }

}
