import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by ljw on 16/12/14.
 */

public class MainApplication extends Application {

    public static DisplayImageOptions options;
    ImageLoaderConfiguration config;
    public static ImageLoader loader; // 全局公用的imageloader
    public static final String serverIp = "http://123.56.138.147";
    public static final String pictureServer = "http://zuibangai.qiniudn.com";
    public static MainApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public MainApplication getInstance() {
        if (instance == null) {
            instance = new MainApplication();
            //RequestManager.getInstance().init(MyApplication.this);
//            sImageLoader = new com.android.volley.toolbox.ImageLoader(
//                    RequestManager.getInstance().getRequestQueue(),
//                    imageCacheMap);
        }
        return instance;
    }
    public void initImageLoader() {

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
        config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .defaultDisplayImageOptions(options).writeDebugLogs().build();

        loader = ImageLoader.getInstance();

        loader.init(config);

    }
    /**
     *
     * 异步加载图片
     *
     * @param v
     *            需要显示图片的view
     * @param picName
     *            图片名字
     * @param
     * @param handler
     *            传入的handler
     */
    public static void displayImage(final View v, String picName,
                                    final Handler handler) {

        Bitmap bm = loader.getMemoryCache().get(pictureServer + "/" + picName); // 有就不用重新下载了
        if (bm != null) {
            Message msg = handler.obtainMessage();
            msg.obj = bm;
            msg.what = 0;
            handler.sendMessage(msg);
        } else {
            bm = BitmapFactory.decodeFile(loader.getDiskCache().getDirectory()
                    .getAbsolutePath());

            if (bm != null) {
                Message msg = handler.obtainMessage();
                msg.obj = bm;
                msg.what = 0;
                handler.sendMessage(msg);
            }

            else

                loader.displayImage(pictureServer + "/" + picName,
                        (ImageView) v, new ImageLoadingListener() {

                            @Override
                            public void onLoadingStarted(String imageUri,
                                                         View view) {
                                // TODO Auto-generated method stub

                            }


                            @Override
                            public void onLoadingFailed(String imageUri,
                                                        View view, FailReason failReason) {
                                // TODO Auto-generated method stub
                                // v.setBackgroundDrawable(defaultResDrawable);
                                handler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onLoadingComplete(String imageUri,
                                                          View view, Bitmap loadedImage) {
                                // TODO Auto-generated method stub
                                Message msg = handler.obtainMessage();
                                msg.what = 0;
                                msg.obj = loadedImage;
                                handler.sendMessage(msg);

                            }

                            @Override
                            public void onLoadingCancelled(String imageUri,
                                                           View view) {
                                // TODO Auto-generated method stub

                            }
                        });
        }

    }

}
