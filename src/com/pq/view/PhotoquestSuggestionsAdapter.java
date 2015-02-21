package com.pq.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.pq.R;
import com.pq.data.Suggestion;
import com.utils.framework.suggestions.SuggestionsProvider;
import com.utilsframework.android.adapters.SingleViewArrayAdapter;
import com.utilsframework.android.adapters.SuggestionsAdapter;

/**
 * Created by CM on 2/20/2015.
 */
public class PhotoquestSuggestionsAdapter extends SuggestionsAdapter<Suggestion, Void> {
    public PhotoquestSuggestionsAdapter(Context context, SuggestionsProvider<Suggestion> suggestionsProvider) {
        setViewArrayAdapter(new SingleViewArrayAdapter<Suggestion>(context) {
            @Override
            protected int getRootLayoutId(int viewType) {
                return R.layout.suggestion_item;
            }

            @Override
            protected void reuseView(Suggestion suggestion, Void aVoid, int position, View view) {
                TextView textView = (TextView) view;
                textView.setText(suggestion.value);
            }
        });
        setSuggestionsProvider(suggestionsProvider);
    }
}
