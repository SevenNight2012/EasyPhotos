package com.huantansheng.easyphotos.ui.adapter.holder;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * holder 基类
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder {

    private final RecyclerView.Adapter<?> mAdapter;

    public BaseHolder(RecyclerView.Adapter<?> adapter, @NonNull View itemView) {
        super(itemView);
        this.mAdapter = adapter;
    }

    @SuppressLint("NotifyDataSetChanged")
    public final void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public final void notifyItemRangeChanged(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount);
    }

    public final void notifyItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
        mAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    public final void notifyItemChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }

    public final void notifyItemChanged(int position, @Nullable Object payload) {
        mAdapter.notifyItemChanged(position, payload);
    }

    public final void notifyItemInserted(int position) {
        mAdapter.notifyItemInserted(position);
    }

    public final void notifyItemMoved(int fromPosition, int toPosition) {
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    public final void notifyItemRangeInserted(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }

    public final void notifyItemRemoved(int position) {
        mAdapter.notifyItemRemoved(position);
    }

    public final void notifyItemRangeRemoved(int positionStart, int itemCount) {
        mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
    }

}
