package com.quinn.githubknife.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quinn.githubknife.R;
import com.quinn.githubknife.utils.L;

/**
 * Created by Quinn on 9/14/15.
 */
public class SettingLabel extends LinearLayout {

    private final static String TAG = SettingLabel.class.getSimpleName();

    private String label_name = "name";
    private String label_value = "value";
    private TextView label_name_tv;
    private TextView label_value_tv;

    public SettingLabel(Context context) {
        this(context, null);
    }

    public SettingLabel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.label_layout);
        label_name = a.getString(R.styleable.label_layout_label_name);
        label_value = a.getString(R.styleable.label_layout_label_value);

        L.i(TAG, "label_name = " + label_name);
        L.i(TAG, "label_value = " + label_value);

        LayoutInflater.from(context).inflate(R.layout.label_layout, this);
        label_name_tv = (TextView) findViewById(R.id.label_name);
        label_value_tv = (TextView) findViewById(R.id.label_value);
        label_name_tv.setText(label_name);
        label_value_tv.setText(label_value);
    }


    public void setValue(String value){
        label_value_tv.setText(value);
        invalidate();
    }



}
