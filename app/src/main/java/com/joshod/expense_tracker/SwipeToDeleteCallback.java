package com.joshod.expense_tracker;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

abstract class SwipeToDeleteCallback extends ItemTouchHelper.Callback {

    public SwipeToDeleteCallback(){
        super();
    }

    @Override
     public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlag);
    }

    @Override
    public boolean onMove(
        RecyclerView recyclerView,
        RecyclerView.ViewHolder viewHolder,
        RecyclerView.ViewHolder target
    ){
        return false;
    }
}