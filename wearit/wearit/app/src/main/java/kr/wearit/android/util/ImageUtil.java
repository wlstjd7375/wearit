package kr.wearit.android.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import kr.wearit.android.App;
import kr.wearit.android.Config;
import kr.wearit.android.Const;
import kr.wearit.android.controller.Api;
import kr.wearit.android.model.Images;
import kr.wearit.android.model.Resource;

/**
 * Created by KimJS on 2016-09-07.
 */
public class ImageUtil {


    private static final String TAG = "ImageUtil";
    private static final boolean LOG = Config.LOG;

    //

    public static DisplayImageOptions mDisplayDefault;
    public static DisplayImageOptions mDisplayHigh;
    public static DisplayImageOptions mDisplaySource;
    public static DisplayImageOptions mDisplayFadein;
    //

    public static void init(App app) {
        mDisplayFadein  = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.color.white)
                .showImageForEmptyUri(android.R.color.white)
                .displayer(new FadeInBitmapDisplayer(500))
                .cacheInMemory(true)
                .build();

        mDisplayDefault = new DisplayImageOptions.Builder() //
                .cacheInMemory(true) //
                .considerExifParams(true) //
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) //
                .bitmapConfig(Bitmap.Config.RGB_565) //
                .resetViewBeforeLoading(true) //
                .build();

        mDisplayHigh = new DisplayImageOptions.Builder() //
                .cloneFrom(mDisplayDefault) //
                .bitmapConfig(Bitmap.Config.ARGB_8888) //
                .build();

        mDisplaySource = new DisplayImageOptions.Builder() //
                .cloneFrom(mDisplayHigh) //
                .imageScaleType(ImageScaleType.EXACTLY) //
                .build();

        ImageLoader.getInstance().init( //
                new ImageLoaderConfiguration.Builder(app) //
                        .defaultDisplayImageOptions(mDisplayFadein) //
                        //.threadPoolSize(1) //
                        .diskCache(new UnlimitedDiscCache(StorageUtils.getCacheDirectory(app))) //
                        .denyCacheImageMultipleSizesInMemory() //
                        .build());

		/*
		ImageLoader.getInstance().init( //
				new ImageLoaderConfiguration.Builder(this) //
						//.memoryCacheExtraOptions(480, 800) // default = device screen dimensions
						//.diskCacheExtraOptions(480, 800, null) //
						//.taskExecutor(...) //
						//.taskExecutorForCachedImages(...) //
						//.threadPoolSize(3) // default
						//.threadPriority(Thread.NORM_PRIORITY - 2) // default
						//.tasksProcessingOrder(QueueProcessingType.FIFO) // default
						.memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //
						//.memoryCacheSize(2 * 1024 * 1024) //
						//.memoryCacheSizePercentage(13) // default
						//.diskCacheSize(50 * 1024 * 1024) //
						//.diskCacheFileCount(100) //
						//.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
						//.imageDownloader(new BaseImageDownloader(this)) // default
						//.imageDecoder(new BaseImageDecoder()) // default
						//.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
						//.writeDebugLogs() //
						.build());
						*/
    }

    //

    private static String getPath(Resource resource) {
        return resource != null ? resource.getImagePath() : null;
    }

    //
    public static void display(ImageView imageView, String path) {
        display(imageView, path, null);
    }

    public static void display(ImageView imageView, Resource resource) {
        display(imageView, getPath(resource));
    }
/*
    public static void display(View container, int id, String path) {
        display(Binder.findImageView(container, id), path);
    }

    public static void display(View container, int id, String path, int resId) {
        display(Binder.findImageView(container, id), path, resId);
    }

    public static void display(View container, int id, Resource resource) {
        display(container, id, getPath(resource));
    }*/

    public static void display(ImageView imageView, String path, int resId) {
        imageView.setImageResource(resId);

        display(imageView, path);
    }

    public static void display(ImageView imageView, Resource resource, int resId) {
        display(imageView, getPath(resource), resId);
    }

    public static void displayHigh(ImageView imageView, String path) {
        display(imageView, path, mDisplayHigh);
    }

    @Deprecated
    public static void displaySource(ImageView imageView, String path) {
        display(imageView, path, mDisplaySource);
    }

    public static void displaySource(ImageView imageView, String path, ImageLoadingListener listener) {
        display(imageView, path, null/*mDisplaySource*/, listener);
    }

    //

    public static void display(ImageView imageView, String path, DisplayImageOptions options) {
        display(imageView, path, options, null);
    }

    public static void display(ImageView imageView, String path, int resId ,DisplayImageOptions options) {
        imageView.setImageResource(resId);
        display(imageView, path, options, null);
    }

    private static void display(ImageView imageView, String path, DisplayImageOptions options, ImageLoadingListener listener) {
        if (LOG) {
            Log.d(TAG, Const.LOG_DATE);
            Log.d(TAG, "display // path = " + path);
        }

        ImageLoader.getInstance().displayImage(Api.getResourceUrl(path), imageView, options, listener);
    }

    //

    public static void load(String path, ImageSize size, ImageLoadingListener listener) {
        if (LOG) {
            //Log.d(TAG, Const.LOG_DATE);
            Log.d(TAG, "load // path = " + path);
        }

        ImageLoader.getInstance().loadImage(Api.getResourceUrl(path), size, listener);
    }

    //

    public static double getRatio(int width, int height) {
        return (double) height / width;
    }

    public static double getRatio(Resource image) {
        return getRatio(image.getImageWidth(), image.getImageHeight());
    }

    public static double getRatio(Images images) {
        if (images == null)
            return 0;

        double ratio = 0;

        for (int i = 0; i < images.getImagesSize(); i++)
            ratio = Math.max(ratio, images.getImagesResource(i).getImageRatio());

        return ratio;
    }
}
