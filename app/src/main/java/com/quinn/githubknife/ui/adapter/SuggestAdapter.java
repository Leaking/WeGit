package com.quinn.githubknife.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quinn.githubknife.R;

import java.util.ArrayList;

/**
 * Created by Quinn on 9/29/15.
 */
public class SuggestAdapter extends BaseAdapter{

    private ArrayList<String> dataItems;
    private Context context;

    public SuggestAdapter(Context context, ArrayList<String> dataItems){
        this.context = context;
        this.dataItems = dataItems;
    }


    @Override
    public int getCount() {
        return dataItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dataItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_suggestlist,
                    null);
            viewholder.text = (TextView) convertView
                    .findViewById(R.id.text);
            viewholder.img = (ImageView) convertView
                    .findViewById(R.id.img);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.text.setText(dataItems.get(position));
        viewholder.img.setBackgroundResource(R.drawable.ic_up);
        return convertView;
    }

    static class ViewHolder {
        public TextView text;
        public ImageView img;
    }
}
