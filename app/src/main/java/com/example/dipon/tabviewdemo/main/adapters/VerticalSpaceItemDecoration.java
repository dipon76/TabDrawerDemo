package com.example.dipon.tabviewdemo.main.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Dipon on 5/11/2017.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
    }
}
//    If you don't want to insert space below the last item, add the following condition:
//
//        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
//        outRect.bottom = verticalSpaceHeight;
//        }