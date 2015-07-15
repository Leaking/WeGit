package com.quinn.githubknife.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.bean.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Quinn on 7/15/15.
 */
public class DrawerAdapter extends BaseAdapter {

    private final String[] DATA_ARRAY = {"","","",""};
    private final Context context;
    private final User user;
    private final LayoutInflater inflater;

    private List<String> data;


    public DrawerAdapter(Context context,User user){
        this.context = context;
        this.user = user;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data  = Arrays.asList(context.getResources().getStringArray(R.array.drawerListData));
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_drawerlist, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView
                    .findViewById(R.id.drawerItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(data.get(position));
        return convertView;
    }

    public static class ViewHolder{
        TextView text;
    }

}
