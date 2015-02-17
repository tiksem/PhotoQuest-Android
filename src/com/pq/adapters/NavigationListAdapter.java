package com.pq.adapters;

import android.content.Context;
import android.view.View;
import com.pq.R;
import com.utilsframework.android.adapters.ViewArrayAdapter;

/**
 * Created by CM on 2/18/2015.
 */
public abstract class NavigationListAdapter<Element, ViewHolder> extends ViewArrayAdapter<Element, ViewHolder> {
    public NavigationListAdapter(Context context) {
        super(context);
    }

    public NavigationListAdapter(Context context, View header) {
        super(context, header);
    }

    @Override
    protected int getNullLayoutId() {
        return R.layout.navigation_list_null_item;
    }
}
