package com.quinn.githubknife.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;

import com.github.quinn.iconlibrary.IconicFontDrawable;
import com.github.quinn.iconlibrary.icons.Icon;

import java.io.ByteArrayOutputStream;

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

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
