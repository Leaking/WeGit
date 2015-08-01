package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.httpknife.github.Github;
import com.quinn.httpknife.github.GithubImpl;
import com.quinn.httpknife.github.Repository;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RepoActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.repoName)
    TextView repoName;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.numStar)
    TextView starNum;
    @Bind(R.id.numFork)
    TextView forkNum;
    @Bind(R.id.iconStar)
    TextView starIcon;
    @Bind(R.id.iconFork)
    TextView forkIcon;


    private Repository repo;
    private Github github;


    public static void launch(Context context, Bundle bundle){
        Intent intent = new Intent(context,RepoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reop);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        repo = (Repository) bundle.getSerializable("repo");
        toolbar.setTitle(repo.getName());
        toolbar.setSubtitle(repo.getOwner().getLogin());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        github = new GithubImpl(this);

        repoName.setText(repo.getName());
        description.setText(repo.getDescription());
        starNum.setText("" + repo.getStargazers_count());
        forkNum.setText("" + repo.getForks_count());
        Typeface typeface = Typeface.createFromAsset(getAssets(),"octicons.ttf");
        starIcon.setTypeface(typeface);
        forkIcon.setTypeface(typeface);
        starIcon.setText(R.string.icon_star);
        forkIcon.setText(R.string.icon_fork);

        forkIcon.setText(forkIcon.getText() + " Fork");
        starIcon.setText(starIcon.getText() + " Star");



    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
