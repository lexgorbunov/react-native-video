package com.brentvatne.exoplayer;


import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import java.util.Map;

class AndroidCacheDataSourceFactory implements DataSource.Factory {
    private final Context context;
    private final DefaultDataSourceFactory defaultDatasourceFactory;
    private final long maxFileSize;

    AndroidCacheDataSourceFactory(
            ReactContext context,
            long maxFileSize,
            DefaultBandwidthMeter bandwidthMeter,
            Map<String, String> requestHeaders
    ) {
        super();
        this.context = context;
        this.maxFileSize = maxFileSize;
//        String userAgent = Util.getUserAgent(context, "Looky");
        defaultDatasourceFactory = new DefaultDataSourceFactory(this.context,
                bandwidthMeter,
                DataSourceUtil.getDefaultHttpDataSourceFactory(context, bandwidthMeter, requestHeaders));
    }


    public SimpleCache getInstance(Context context) {
        return RNVideoModule.getCacheInstance(context);
    }

    @Override
    public DataSource createDataSource() {
        Log.d("simpleCache", getInstance(context).toString());
        return new CacheDataSource(getInstance(context), defaultDatasourceFactory.createDataSource(),
                new FileDataSource(), new CacheDataSink(getInstance(context), maxFileSize),
                CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
    }
}
