package com.huantansheng.easyphotos.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huantansheng.easyphotos.R;
import com.huantansheng.easyphotos.models.ad.AdViewHolder;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.huantansheng.easyphotos.result.Result;
import com.huantansheng.easyphotos.setting.Setting;
import com.huantansheng.easyphotos.ui.adapter.holder.CameraViewHolder;
import com.huantansheng.easyphotos.ui.adapter.holder.PhotoViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 专辑相册适配器
 * Created by huan on 2017/10/23.
 */
public class PhotosAdapter extends RecyclerView.Adapter {
    private static final int TYPE_AD = 0;
    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_ALBUM_ITEMS = 2;

    private ArrayList<Object> dataList;
    private LayoutInflater mInflater;
    private PhotoClickListener listener;

    private final AtomicBoolean mUnable;
    private final AtomicBoolean mSingleSelect;

    private boolean clearAd = false;

    public PhotosAdapter(Context ctx, ArrayList<Object> dataList, PhotoClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
        this.mInflater = LayoutInflater.from(ctx);
        mUnable = new AtomicBoolean(Result.count() == Setting.count);
        mSingleSelect = new AtomicBoolean(Setting.count == 1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void change() {
        mUnable.set(Result.count() == Setting.count);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_AD:
                return new AdViewHolder(mInflater.inflate(R.layout.item_ad_easy_photos, parent, false));
            case TYPE_CAMERA:
                return new CameraViewHolder(mInflater.inflate(R.layout.item_camera_easy_photos, parent, false));
            default:
                View itemView = mInflater.inflate(R.layout.item_rv_photos_easy_photos, parent, false);
                PhotoViewHolder photoViewHolder = new PhotoViewHolder(this, itemView, mUnable, mSingleSelect);
                photoViewHolder.setPhotoClickListener(listener);
                return photoViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final int p = position;
        if (holder instanceof PhotoViewHolder) {
            final Photo item = (Photo) dataList.get(p);
            if (item == null) {
                return;
            }
            ((PhotoViewHolder) holder).onBindItem(item, position);
            return;
        }

        if (holder instanceof AdViewHolder) {
            if (clearAd) {
                ((AdViewHolder) holder).adFrame.removeAllViews();
                ((AdViewHolder) holder).adFrame.setVisibility(View.GONE);
                return;
            }
            if (!Setting.photoAdIsOk) {
                ((AdViewHolder) holder).adFrame.setVisibility(View.GONE);
                return;
            }

            WeakReference weakReference = (WeakReference) dataList.get(p);

            if (null != weakReference) {
                View adView = (View) weakReference.get();
                if (null != adView) {
                    if (null != adView.getParent()) {
                        if (adView.getParent() instanceof FrameLayout) {
                            ((FrameLayout) adView.getParent()).removeAllViews();
                        }
                    }
                    ((AdViewHolder) holder).adFrame.setVisibility(View.VISIBLE);
                    ((AdViewHolder) holder).adFrame.removeAllViews();
                    ((AdViewHolder) holder).adFrame.addView(adView);
                }
            }
        }

        if (holder instanceof CameraViewHolder) {
            ((CameraViewHolder) holder).flCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCameraClick();
                }
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearAd() {
        clearAd = true;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            if (Setting.hasPhotosAd()) {
                return TYPE_AD;
            }
            if (Setting.isShowCamera && !Setting.isBottomRightCamera()) {
                return TYPE_CAMERA;
            }
        }
        if (1 == position && !Setting.isBottomRightCamera()) {
            if (Setting.hasPhotosAd() && Setting.isShowCamera) {
                return TYPE_CAMERA;
            }
        }
        return TYPE_ALBUM_ITEMS;
    }


}
