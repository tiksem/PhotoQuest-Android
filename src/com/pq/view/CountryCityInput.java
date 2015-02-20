package com.pq.view;

import android.view.View;
import android.widget.AdapterView;
import com.pq.data.Suggestion;
import com.pq.network.CitySuggestionsProvider;
import com.pq.network.CountrySuggestionsProvider;
import com.pq.network.RequestManager;
import com.utilsframework.android.IOErrorListener;
import com.utilsframework.android.adapters.SuggestionsAdapter;
import com.utilsframework.android.view.Alerts;
import com.utilsframework.android.view.EditTextWithSuggestions;

import java.io.IOException;

/**
 * Created by CM on 2/20/2015.
 */
public class CountryCityInput {
    private EditTextWithSuggestions country;
    private EditTextWithSuggestions city;
    private RequestManager requestManager;
    private int countryId;
    private int cityId;
    private IOErrorListener ioErrorListener;

    private void initCountry() {
        CountrySuggestionsProvider countrySuggestionsProvider = new CountrySuggestionsProvider(requestManager);
        final PhotoquestSuggestionsAdapter countryAdapter =
                new PhotoquestSuggestionsAdapter(country.getContext(), countrySuggestionsProvider);
        country.setAdapter(countryAdapter);

        country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Suggestion suggestion = countryAdapter.getLastSuggestedItems().get(position);
                countryId = suggestion.id;
                city.setVisibility(View.VISIBLE);
            }
        });

        country.setOnDefaultValueSet(new EditTextWithSuggestions.OnDefaultValueSet() {
            @Override
            public void onDefaultValueSet() {
                countryId = -1;
                cityId = -1;
                city.setVisibility(View.GONE);
            }
        });

        countrySuggestionsProvider.setIoErrorListener(ioErrorListener);
        country.setDisplayDefaultValueOnNoItemSelected(true);
    }

    private void initCity() {
        CitySuggestionsProvider citySuggestionsProvider = new CitySuggestionsProvider(requestManager) {
            @Override
            protected int getCountryId() {
                return countryId;
            }
        };

        final PhotoquestSuggestionsAdapter countryAdapter =
                new PhotoquestSuggestionsAdapter(city.getContext(), citySuggestionsProvider);
        city.setAdapter(countryAdapter);

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Suggestion suggestion = countryAdapter.getLastSuggestedItems().get(position);
                cityId = suggestion.id;
            }
        });

        city.setOnDefaultValueSet(new EditTextWithSuggestions.OnDefaultValueSet() {
            @Override
            public void onDefaultValueSet() {
                cityId = -1;
            }
        });

        citySuggestionsProvider.setIoErrorListener(ioErrorListener);
        city.setDisplayDefaultValueOnNoItemSelected(true);
    }

    public CountryCityInput(RequestManager requestManager,
                            final EditTextWithSuggestions country, final EditTextWithSuggestions city) {
        this.country = country;
        this.city = city;
        this.requestManager = requestManager;

        ioErrorListener = new IOErrorListener() {
            @Override
            public void onIOError(IOException error) {
                Alerts.showOkButtonAlert(country.getContext(), error.getMessage());
            }
        };

        city.setVisibility(View.GONE);

        initCountry();
        initCity();
    }

    public int getCountryId() {
        return countryId;
    }

    public int getCityId() {
        return cityId;
    }
}
