package in.mvcdemo.Utills;

import android.content.Context;
import android.location.Location;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;

public class VApp extends MultiDexApplication  {

    public static final String TAG = VApp.class.getSimpleName();
    private static final String CREATIVE_SDK_CLIENT_ID = "220cb6ed850247abbc812869dc76ac5e";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "e6a9bf7d-e184-4665-bde1-419ee940da2a";
    public static String IMAGE_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/Camera/";
    private static Context context = null;
    public Location location;
    public String photographerEmail;
    public String photographerName;
    public String photoPassID;
    public String photographerImage;
    public String photographerInviteCode;



    public static Context getContext() {
        return context;
    }

    public String getPhotographerEmail() {
        return photographerEmail;
    }

    public void setPhotographerEmail(String photographerEmail) {
        this.photographerEmail = photographerEmail;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public String getPhotoPassID() {
        return photoPassID;
    }

    public void setPhotoPassID(String photoPassID) {
        this.photoPassID = photoPassID;
    }

    public String getPhotographerImage() {
        return photographerImage;
    }

    public void setPhotographerImage(String photographerImage) {
        this.photographerImage = photographerImage;
    }

    public String getPhotographerInviteCode() {
        return photographerInviteCode;
    }

    public void setPhotographerInviteCode(String photographerInviteCode) {
        this.photographerInviteCode = photographerInviteCode;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);


        File folder = new File(IMAGE_DIR);
        if (!folder.exists())
            folder.mkdirs();
        context = this;
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public VApp getInstance() {
        return ((VApp) VApp
                .getContext());
    }


}
