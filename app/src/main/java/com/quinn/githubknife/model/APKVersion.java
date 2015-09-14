package com.quinn.githubknife.model;

/**
 * Created by Quinn on 9/14/15.
 */
public class APKVersion {



    private int versionCode;
    private String versionName;


    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }


    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = "v" + versionName;
    }
}
