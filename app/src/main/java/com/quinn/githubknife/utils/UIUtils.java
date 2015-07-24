package com.quinn.githubknife.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

/**
 * Created by Quinn on 7/24/15.
 */
public class UIUtils {

    public static void crossfade(final View currView, View nextView) {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        if (nextView != null) {
            nextView.setAlpha(0f);
            nextView.setVisibility(View.VISIBLE);
            nextView.animate().alpha(1f).setDuration(400).setListener(null);
        }

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        if (currView != null) {
            currView.setVisibility(View.GONE);
            currView.animate().alpha(0f).setDuration(400)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            currView.setVisibility(View.GONE);
                        }
                    });
        }
    }


}
