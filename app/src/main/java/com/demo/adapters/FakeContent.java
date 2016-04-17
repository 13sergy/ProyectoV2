package com.demo.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TabHost;

/**
 * Fake content for tabHost
 */
public class FakeContent implements TabHost.TabContentFactory {
    private final Context mContext;

    public FakeContent(Context context) {
        mContext = context;
    }

    @Override
    public View createTabContent(String tag) {
        View v = new View(mContext);
        v.setMinimumHeight(0);
        v.setMinimumWidth(0);
        return v;
    }
}