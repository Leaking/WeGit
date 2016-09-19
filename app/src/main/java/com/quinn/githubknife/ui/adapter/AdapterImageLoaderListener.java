package com.quinn.githubknife.ui.adapter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.jsoup.helper.StringUtil;

/**
 * Created by Quinn on 9/16/16.
 *
 * listview等可以复用view的组件，网络请求回来，需要判断TAG
 */
public class AdapterImageLoaderListener extends SimpleImageLoadingListener {

    private final static String TAG = "AdapterImgListener";

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        super.onLoadingStarted(imageUri, view);
//        view.setTag(imageUri);
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        if (loadedImage != null) {
            ImageView imageView = (ImageView) view;
            String viewTag = (String)imageView.getTag();
            if(!TextUtils.isEmpty(viewTag)) {
                if(viewTag.equals(imageUri)) {
                     ((ImageView) view).setImageBitmap(loadedImage);
                    Log.i(TAG, "hit tag");
                } else{
                    Log.i(TAG, "miss tag");
                }
            } else {
                Log.i(TAG, "have not tag");
                ((ImageView) view).setImageBitmap(loadedImage);
            }
        }
    }
}
