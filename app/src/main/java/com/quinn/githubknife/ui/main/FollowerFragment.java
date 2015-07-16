package com.quinn.githubknife.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.utils.L;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubImpl;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Quinn on 7/15/15.
 */
public class FollowerFragment extends Fragment {

    Github github;

    @Bind(R.id.friend)
    TextView txt;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("come onCreate FollowerFragment");

        github = new GithubImpl(this.getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.i("===============0000");

            }
        });
        L.i("come into FollowerFragment");

        return view;
    }
}
