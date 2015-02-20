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

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Suggestion suggestion = countryAdapter.getLastSuggestedItems().get(position);
                countryId = suggestion.id;
                city.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                countryId = -1;
                cityId = -1;
                city.setText("");
                country.setText("");
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

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Suggestion suggestion = countryAdapter.getLastSuggestedItems().get(position);
                cityId = suggestion.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cityId = -1;
                city.setText("");
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
