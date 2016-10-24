package com.quinn.githubknife.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.quinn.githubknife.GithubApplication;
import com.quinn.githubknife.R;
import com.quinn.githubknife.ui.BaseActivity;
import com.quinn.githubknife.ui.widget.SettingLabel;
import com.quinn.githubknife.utils.ChannelUtils;
import com.quinn.githubknife.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class SettingActivity extends BaseActivity {

    //feature branch
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.version)
    SettingLabel version;

    public static void launch(Context context){
        Intent intent = new Intent(context,SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_activity_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        version.setValue(((GithubApplication) getApplication()).getAPKVersion().getVersionName());
        version.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String channelName = ChannelUtils.getChannelName(getApplicationContext());
                ToastUtils.showMsg(getApplicationContext(), channelName, Toast.LENGTH_LONG);
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.author)
    void author(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",app.getUser());
        UserInfoActivity.launch(this,bundle);
    }

    @OnClick(R.id.repo)
    void repo(){

    }

}
