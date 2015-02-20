package com.pq.network;

import com.pq.data.Suggestion;

import java.io.IOException;
import java.util.List;

/**
 * Created by CM on 2/20/2015.
 */
public abstract class CitySuggestionsProvider extends AbstractSuggestionsProvider<Suggestion> {
    public CitySuggestionsProvider(RequestManager requestManager) {
        super(requestManager);
    }

    @Override
    protected List<Suggestion> getSuggestions(RequestManager requestManager, String query) throws IOException {
        return requestManager.getCitySuggestions(query, getCountryId());
    }

    protected abstract int getCountryId();
}
