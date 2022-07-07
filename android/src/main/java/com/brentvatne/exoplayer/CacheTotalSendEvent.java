package com.brentvatne.exoplayer;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class CacheTotalSendEvent extends Event<CacheTotalSendEvent> {
    long value;

    CacheTotalSendEvent(int viewTag, long total) {
        super(viewTag);
        this.value = total;
    }

    @Override
    public String getEventName() {
        return "onCacheTotalInfo";
    }

    @Override
    public boolean canCoalesce() {
        return false;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        WritableMap event = new WritableNativeMap();
        event.putDouble("total", this.value);
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), event);
    }
}
