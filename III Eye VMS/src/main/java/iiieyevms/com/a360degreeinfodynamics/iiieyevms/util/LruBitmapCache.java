package iiieyevms.com.a360degreeinfodynamics.iiieyevms.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Admin on 1/23/2017.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        Log.i("maxMemory",""+maxMemory);
        Log.i("cacheSize",""+cacheSize);
        return cacheSize;
    }

    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {
        Log.i("cacheSize1",""+value.getRowBytes() * value.getHeight() / 1024);
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
