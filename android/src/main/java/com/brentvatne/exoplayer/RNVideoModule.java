package com.brentvatne.exoplayer;

import android.content.Context;
import android.os.FileUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

public class RNVideoModule extends ReactContextBaseJavaModule {
    static final int Gb = 1024 * 1024;
    static final int MAX_CACHE_SIZE = 1024 * Gb;

    @Nullable
    public static SimpleCache cache = null;
    public static File file = null;

    public static SimpleCache getCacheInstance(Context context) {
        LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(MAX_CACHE_SIZE);
        if (cache == null) {
            file = new File(context.getCacheDir(), "exoCache");
            cache = new SimpleCache(file, evictor);
        }
        return cache;
    }

    @NonNull
    @Override
    public String getName() {
        return "RNVideoModule";
    }

    public RNVideoModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    void getCacheTotal(Promise promise) {
        if (cache == null) {
            promise.resolve(-1);
            return;
        }
        WritableMap event = new WritableNativeMap();
        event.putDouble("total", cache.getCacheSpace());
        promise.resolve(event);
    }

    @ReactMethod
    void releaseCache() {
        if (cache == null) return;
        cache.release();
        deleteDir(file);

        file = null;
        cache = null;
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }
}
