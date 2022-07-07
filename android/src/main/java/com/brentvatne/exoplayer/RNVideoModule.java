package com.brentvatne.exoplayer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.io.File;

public class RNVideoModule extends ReactContextBaseJavaModule {
    static final int MAX_CACHE_SIZE = 300 * 1024 * 1024;

    @Nullable
    public static SimpleCache cache = null;

    public static SimpleCache getCacheInstance(Context context) {
        LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(MAX_CACHE_SIZE);
        if (cache == null)
            cache = new SimpleCache(new File(context.getCacheDir(), "exoCache"), evictor);
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
        promise.resolve(cache.getCacheSpace());
    }

    @ReactMethod
    void releaseCache() {
        if (cache == null) return;
        cache.release();
        cache = null;
    }
}
