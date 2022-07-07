package com.kevin.breatheezy.util;

import android.graphics.drawable.Animatable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ExpandableRecyclerViewAnimation {
    public static boolean toggleArrow(View v, boolean isExpanded){
        if(isExpanded) {
            v.animate().setDuration(200).rotation(180);
            return true;
        }else{
            v.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    public static void expand(final View view){
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int actualHeight = view.getMeasuredHeight();

        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                view.getLayoutParams().height = interpolatedTime == 1 ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (actualHeight*interpolatedTime);
                view.requestLayout();
            }
        };

        animation.setDuration((long) (actualHeight / view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);

    }

    public static void collapse(final View view){
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int actualHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                if(interpolatedTime == 1){
                    view.setVisibility(View.GONE);
                }else{
                    view.getLayoutParams().height = actualHeight - (int) (actualHeight*interpolatedTime);
                    view.requestLayout();
                }
            }
        };

        animation.setDuration((long) (actualHeight / view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);

    }
}
