package com.quinn.githubknife.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static android.content.ContentValues.TAG;

/**
 * Created by Quinn on 25/10/2016.
 */

public class ChannelUtils {

    private static HashMap<String, String> channels = new HashMap<>();

    static {
        channels.put("0", "dev_build");
        channels.put("1", "yingyongbao");
        channels.put("2", "google play");
        channels.put("3", "github");
        channels.put("4", "360");
        channels.put("5", "baidu");
    }

    public static String getChannel(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                Log.i(TAG, "entryName = " + entryName);
                if (entryName.startsWith("channel") || entryName.startsWith("META-INF/channel")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[] split = ret.split("_");
        if (split != null && split.length >= 2) {
            return ret.substring(split[0].length() + 1);

        } else {
            return "0";
        }
    }

    public static String getChannelName(Context context) {
        return channels.get(getChannel(context));
    }

}
