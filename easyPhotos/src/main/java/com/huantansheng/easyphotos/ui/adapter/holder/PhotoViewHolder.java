package com.huantansheng.easyphotos.ui.adapter.holder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.huantansheng.easyphotos.R;
import com.huantansheng.easyphotos.constant.Type;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.huantansheng.easyphotos.result.Result;
import com.huantansheng.easyphotos.setting.Setting;
import com.huantansheng.easyphotos.ui.adapter.EmptyPhotoClick;
import com.huantansheng.easyphotos.ui.adapter.PhotoClickListener;
import com.huantansheng.easyphotos.utils.media.DurationUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * photo list item view holder
 * {@link R.layout#item_rv_photos_easy_photos}
 */
public class PhotoViewHolder extends BaseHolder {

    private static final EmptyPhotoClick EMPTY_PHOTO_CLICK = new EmptyPhotoClick();

    private int singlePosition;

    final ImageView ivPhoto;
    final TextView tvSelector;
    final View vSelector;
    final TextView tvType;
    final ImageView ivVideo;

    final AtomicBoolean mUnable;
    final AtomicBoolean mSingleSelect;

    private PhotoClickListener mPhotoClickListener;

    public PhotoViewHolder(RecyclerView.Adapter<?> adapter, View itemView, AtomicBoolean unable, AtomicBoolean singleSelect) {
        super(adapter, itemView);
        this.ivPhoto = itemView.findViewById(R.id.iv_photo);
        this.tvSelector = itemView.findViewById(R.id.tv_selector);
        this.vSelector = itemView.findViewById(R.id.v_selector);
        this.tvType = itemView.findViewById(R.id.tv_type);
        this.ivVideo = itemView.findViewById(R.id.iv_play);

        mUnable = unable;
        mSingleSelect = singleSelect;
    }

    public void setPhotoClickListener(PhotoClickListener photoClickListener) {
        mPhotoClickListener = photoClickListener;
    }

    public PhotoClickListener getPhotoClickListener() {
        return null == mPhotoClickListener ? EMPTY_PHOTO_CLICK : mPhotoClickListener;
    }

    /**
     * 绑定数据项
     *
     * @param item     相册数据
     * @param position 位置
     */
    public void onBindItem(final Photo item, final int position) {
        updateSelector(tvSelector, item.selected, item, position);
        String path = item.path;
        Uri uri = item.uri;
        String type = item.type;
        long duration = item.duration;
        final boolean isGif = path.endsWith(Type.GIF) || type.endsWith(Type.GIF);
        if (Setting.showGif && isGif) {
            Setting.imageEngine.loadGifAsBitmap(ivPhoto.getContext(), uri, ivPhoto);
            tvType.setText(R.string.gif_easy_photos);
            tvType.setVisibility(View.VISIBLE);
            ivVideo.setVisibility(View.GONE);
        } else if (Setting.showVideo && type.contains(Type.VIDEO)) {
            Setting.imageEngine.loadPhoto(ivPhoto.getContext(), uri, ivPhoto);
            tvType.setText(DurationUtils.format(duration));
            tvType.setVisibility(View.VISIBLE);
            ivVideo.setVisibility(View.VISIBLE);
        } else {
            Setting.imageEngine.loadPhoto(ivPhoto.getContext(), uri, ivPhoto);
            tvType.setVisibility(View.GONE);
            ivVideo.setVisibility(View.GONE);
        }

        vSelector.setVisibility(View.VISIBLE);
        tvSelector.setVisibility(View.VISIBLE);
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int realPosition = position;
                if (Setting.hasPhotosAd()) {
                    realPosition--;
                }
                if (Setting.isShowCamera && !Setting.isBottomRightCamera()) {
                    realPosition--;
                }
                getPhotoClickListener().onPhotoClick(position, realPosition);
            }
        });

        vSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSingleSelect.get()) {
                    singleSelector(item, position);
                    return;
                }
                if (mUnable.get()) {
                    if (item.selected) {
                        Result.removePhoto(item);
                        if (mUnable.get()) {
                            mUnable.set(false);
                        }
                        getPhotoClickListener().onSelectorChanged();
                        notifyDataSetChanged();
                        return;
                    }
                    getPhotoClickListener().onSelectorOutOfMax(null);
                    return;
                }
                item.selected = !item.selected;
                if (item.selected) {
                    int res = Result.addPhoto(item);
                    if (res != 0) {
                        getPhotoClickListener().onSelectorOutOfMax(res);
                        item.selected = false;
                        return;
                    }
                    tvSelector.setBackgroundResource(R.drawable.bg_select_true_easy_photos);
                    tvSelector.setText(String.valueOf(Result.count()));
                    if (Result.count() == Setting.count) {
                        mUnable.set(true);
                        notifyDataSetChanged();
                    }
                } else {
                    Result.removePhoto(item);
                    if (mUnable.get()) {
                        mUnable.set(false);
                    }
                    notifyDataSetChanged();
                }
                getPhotoClickListener().onSelectorChanged();
            }
        });
    }

    private void updateSelector(TextView tvSelector, boolean selected, Photo photo, int position) {
        if (selected) {
            String number = Result.getSelectorNumber(photo);
            if (number.equals("0")) {
                tvSelector.setBackgroundResource(R.drawable.bg_select_false_easy_photos);
                tvSelector.setText(null);
                return;
            }
            tvSelector.setText(number);
            tvSelector.setBackgroundResource(R.drawable.bg_select_true_easy_photos);
            if (mSingleSelect.get()) {
                singlePosition = position;
                tvSelector.setText("1");
            }
        } else {
            if (mUnable.get()) {
                tvSelector.setBackgroundResource(R.drawable.bg_select_false_unable_easy_photos);
            } else {
                tvSelector.setBackgroundResource(R.drawable.bg_select_false_easy_photos);
            }
            tvSelector.setText(null);
        }
    }

    private void singleSelector(Photo photo, int position) {
        if (!Result.isEmpty()) {
            if (Result.getPhotoPath(0).equals(photo.path)) {
                Result.removePhoto(photo);
            } else {
                Result.removePhoto(0);
                Result.addPhoto(photo);
                notifyItemChanged(singlePosition);
            }
        } else {
            Result.addPhoto(photo);
        }
        notifyItemChanged(position);
        getPhotoClickListener().onSelectorChanged();
    }
}