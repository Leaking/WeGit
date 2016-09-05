package com.quinn.githubknife.utils;

import com.quinn.githubknife.GithubApplication;
import com.tendcloud.tenddata.TCAgent;

/**
 * Created by Quinn on 9/6/16.
 */
public class TDUtils {

    public static void event(String eventID) {
        TCAgent.onEvent(GithubApplication.instance, eventID);
    }

    public static void event(int eventID_Rsid) {
        TCAgent.onEvent(GithubApplication.instance, GithubApplication.instance.getString(eventID_Rsid));
    }
}
