package io.brainyapps.barista.data.source;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;

public interface DataSource {

    void getAllDrinks(GetDrinksCallback callback);

    interface GetDrinksCallback {
        void onDrinksLoaded(List<Drink> drinks);
    }
}
