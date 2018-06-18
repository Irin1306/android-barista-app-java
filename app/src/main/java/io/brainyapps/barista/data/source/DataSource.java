package io.brainyapps.barista.data.source;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;

public interface DataSource {

    void getAllDrinks(GetDrinksCallback callback);

    void saveDring(Drink drink, SaveCallback callback);

    void deleteDrink(Drink drink, DeleteCallback callback);

    interface SaveCallback {
        void onSaved();
    }

    interface DeleteCallback {
        void onDeleted();
    }

    interface GetDrinksCallback {
        void onDrinksLoaded(List<Drink> drinks);
    }
}
