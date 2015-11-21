package allpo.cosplaystuff.helpers;

import android.app.Application;
import android.graphics.Typeface;

import allpo.cosplaystuff.R;

/**
 * Created by Allpo on 14/07/2014.
 */
public class CSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //FLAT UI
//        FlatUI.initDefaultValues(this);
//         FlatUI.setDefaultTheme(R.array.custom_theme);
//        getActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(FlatUI.DEEP, false));
//        getSupportActionBar().setBackgroundDrawable(FlatUI.getActionBarDrawable(FlatUI.DEEP, false));

        //Universal Image Loader
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
//                .diskCacheExtraOptions(480, 800, null)
//                .taskExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
//                .build();
//        ImageLoader.getInstance().init(config);

        //DB Stuff
        DatabaseHelper.createDB(this);
    }

    public Typeface getDefaultTypeFace(){
        return Typeface.createFromAsset(this.getResources().getAssets(), this.getString(R.string.default_font));
    }
}
