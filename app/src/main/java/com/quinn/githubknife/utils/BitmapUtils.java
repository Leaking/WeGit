package com.quinn.githubknife.utils;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.github.quinn.iconlibrary.IconicFontDrawable;
import com.github.quinn.iconlibrary.icons.Icon;

/**
 * Created by Quinn on 8/6/15.
 */
public class BitmapUtils {

    public static void setIconFont(Context context, ImageView iv, Icon icon,
                                   int rsid) {
        IconicFontDrawable iconDraw = new IconicFontDrawable(context);
        iconDraw.setIcon(icon);
        iconDraw.setIconColor(context.getResources().getColor(rsid));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            iv.setBackground(iconDraw);
        else
            iv.setBackgroundDrawable(iconDraw);
    }
}
