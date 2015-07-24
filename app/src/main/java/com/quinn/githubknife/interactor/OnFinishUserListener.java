package com.quinn.githubknife.interactor;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public interface OnFinishUserListener {
    public void onFinished(List items);
    public void onError(boolean first);

}
