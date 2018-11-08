package com.blood.highengineeradvance.toutiao.channel;


import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int column;

    public SpaceItemDecoration(int space, int column) {
        this.space = space;
        this.column = column;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = space;
        int pos = parent.getChildLayoutPosition(view);
        int total = parent.getChildCount();
        if (isFirstRow(pos)) {
            outRect.top = 0;
        }
        if (isLastRow(pos, total)) {
            outRect.bottom = 0;
        }
        float avg = (column - 1) * space * 1.0f / column;
        outRect.left = (int) (pos%column * (space - avg));
        outRect.right = (int) (avg - (pos%column * (space - avg)));
    }

    boolean isFirstRow(int pos) {
        return pos < column;
    }

    boolean isLastRow(int pos, int total) {
        return total - pos <= column;
    }

    boolean isFirstColumn(int pos) {
        return pos % column == 0;
    }

    boolean isSecondColumn(int pos) {
        return isFirstColumn(pos - 1);
    }

    boolean isEndColumn(int pos) {
        return isFirstColumn(pos + 1);
    }

    boolean isNearEndColumn(int pos) {
        return isEndColumn(pos + 1);
    }
}
