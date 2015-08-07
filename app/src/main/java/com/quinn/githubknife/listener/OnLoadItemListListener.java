package com.quinn.githubknife.listener;

import java.util.List;

/**
 * Created by Quinn on 7/20/15.
 */
public interface OnLoadItemListListener{

    public void onFinished(List items);
    public void onError(boolean first,String content);

}
