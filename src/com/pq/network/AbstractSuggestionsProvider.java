package com.pq.network;

import android.os.Handler;
import com.utils.framework.suggestions.SuggestionsProvider;
import com.utilsframework.android.IOErrorListener;

import java.io.IOException;
import java.util.List;

/**
 * Created by CM on 2/20/2015.
 */
public abstract class AbstractSuggestionsProvider<T> implements SuggestionsProvider<T> {
    private RequestManager requestManager;
    private Handler handler = new Handler();
    private IOErrorListener ioErrorListener;

    public AbstractSuggestionsProvider(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public IOErrorListener getIoErrorListener() {
        return ioErrorListener;
    }

    public void setIoErrorListener(IOErrorListener ioErrorListener) {
        this.ioErrorListener = ioErrorListener;
    }

    @Override
    public List<T> getSuggestions(String query) {
        try {
            return getSuggestions(requestManager, query);
        } catch (final IOException e) {
            if (ioErrorListener != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ioErrorListener.onIOError(e);
                    }
                });
            }

            return null;
        }
    }

    protected abstract List<T> getSuggestions(RequestManager requestManager, String query) throws IOException;
}
