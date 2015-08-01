package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.presenter.RepoPresenter;
import com.quinn.githubknife.presenter.RepoPresenterImpl;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.utils.L;
import com.quinn.githubknife.utils.ToastUtils;
import com.quinn.githubknife.view.RepoView;
import com.quinn.httpknife.github.Repository;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepoActivity extends BaseActivity implements RepoView{

    private static final String TAG = RepoActivity.class.getSimpleName();

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

    StarState starState;
    enum StarState {
        UNKNOWN,
        STARRED,
        UNSTARRED
    }



    private Repository repo;
    private RepoPresenter presenter;

    public static void launch(Context context, Bundle bundle){
        Intent intent = new Intent(context,RepoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        repo = (Repository) bundle.getSerializable("repo");
        toolbar.setTitle(repo.getName());
        toolbar.setSubtitle(repo.getOwner().getLogin());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repoName.setText(repo.getName());
        description.setText(repo.getDescription());
        starNum.setText("" + repo.getStargazers_count());
        forkNum.setText("" + repo.getForks_count());
        Typeface typeface = Typeface.createFromAsset(getAssets(),"octicons.ttf");
        starIcon.setTypeface(typeface);
        forkIcon.setTypeface(typeface);
        forkIcon.setText(getResources().getString(R.string.icon_fork) + " Fork");
        starIcon.setText(getResources().getString(R.string.icon_star) + " Star");
        starState = StarState.UNKNOWN;
        presenter = new RepoPresenterImpl(this,this);
        presenter.hasStar(repo.getOwner().getLogin(),repo.getName());


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


    @OnClick(R.id.iconStar)
    void star(){
        L.i(TAG,"Click star icon");
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        switch (starState){
            case UNSTARRED:
                L.i(TAG, "try to star " + repo.getName());
                builder.setTitle("Star A Repo");
                builder.setMessage("Sure to Star " + repo.getName() + "?");
                builder.setPositiveButton("Star", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.star(repo.getOwner().getLogin(), repo.getName());
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
                break;
            case STARRED:
                L.i(TAG, "try to unstar " + repo.getName());
                builder.setTitle("UNStar A Repo");
                builder.setMessage("Sure to unStar " + repo.getName() + "?");
                builder.setPositiveButton("UnStar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.unStar(repo.getOwner().getLogin(), repo.getName());
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
                break;
            case UNKNOWN:
                break;
        }
    }


    @Override
    public void onError(String msg) {
        ToastUtils.showMsg(this,msg);
    }

    @Override
    public void setStarState(boolean isStar) {
        if(isStar) {
            starIcon.setText(getResources().getString(R.string.icon_star) + " unStar");
            starState = StarState.STARRED;
        }
        else {
            starIcon.setText(getResources().getString(R.string.icon_star) + " Star");
            starState = StarState.UNSTARRED;
        }
    }
}
