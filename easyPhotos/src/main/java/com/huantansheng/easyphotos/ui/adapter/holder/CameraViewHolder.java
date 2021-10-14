package com.huantansheng.easyphotos.ui.adapter.holder;

import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.huantansheng.easyphotos.R;

public class CameraViewHolder extends RecyclerView.ViewHolder {
    public final FrameLayout flCamera;

    public CameraViewHolder(View itemView) {
        super(itemView);
        this.flCamera = itemView.findViewById(R.id.fl_camera);
    }
}