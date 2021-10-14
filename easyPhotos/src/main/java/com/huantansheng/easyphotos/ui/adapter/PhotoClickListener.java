package com.huantansheng.easyphotos.ui.adapter;

import androidx.annotation.Nullable;

/**
 * 相册点击回调
 */
public interface PhotoClickListener {
    void onCameraClick();

    void onPhotoClick(int position, int realPosition);

    void onSelectorOutOfMax(@Nullable Integer result);

    void onSelectorChanged();
}