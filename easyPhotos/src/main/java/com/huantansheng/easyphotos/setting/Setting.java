package com.huantansheng.easyphotos.setting;

import android.support.annotation.IntDef;
import android.view.View;

import com.huantansheng.easyphotos.engine.ImageEngine;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * EasyPhotos的设置值
 * Created by huan on 2017/10/24.
 */

public class Setting {
    public static int minWidth = 1;
    public static int minHeight = 1;
    public static long minSize = 1;
    public static int count = 1;
    public static int pictureCount = -1;
    public static int videoCount = -1;
    public static WeakReference<View> photosAdView = null;
    public static WeakReference<View> albumItemsAdView = null;
    public static boolean photoAdIsOk = false;
    public static boolean albumItemsAdIsOk = false;
    public static ArrayList<Photo> selectedPhotos = new ArrayList<>();
    public static boolean showOriginalMenu = false;
    public static boolean originalMenuUsable = false;
    public static String originalMenuUnusableHint = "";
    public static boolean selectedOriginal = false;
    public static String fileProviderAuthority = null;
    public static boolean isShowCamera = false;
    public static int cameraLocation = 1;
    public static boolean onlyStartCamera = false;
    public static boolean showPuzzleMenu = true;
    public static boolean onlyVideo = false;
    public static boolean showGif = false;
    public static boolean showVideo = false;
    public static boolean showCleanMenu = true;
    public static ImageEngine imageEngine = null;

    public static final int LIST_FIRST = 0;
    public static final int BOTTOM_RIGHT = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = {LIST_FIRST, BOTTOM_RIGHT})
    public @interface Location {
    }

    public static void clear() {
        minWidth = 1;
        minHeight = 1;
        minSize = 1;
        count = 1;
        pictureCount = -1;
        videoCount = -1;
        photosAdView = null;
        albumItemsAdView = null;
        photoAdIsOk = false;
        albumItemsAdIsOk = false;
        selectedPhotos.clear();
        showOriginalMenu = false;
        originalMenuUsable = false;
        originalMenuUnusableHint = "";
        selectedOriginal = false;
        cameraLocation = BOTTOM_RIGHT;
        isShowCamera = false;
        onlyStartCamera = false;
        showPuzzleMenu = true;
        onlyVideo = false;
        showGif = false;
        showVideo = false;
        showCleanMenu = true;
    }

    public static boolean hasPhotosAd() {
        return photosAdView != null && photosAdView.get() != null;
    }

    public static boolean hasAlbumItemsAd() {
        return albumItemsAdView != null && albumItemsAdView.get() != null;
    }

    public static boolean isBottomRightCamera() {
        return cameraLocation == BOTTOM_RIGHT;
    }
}
