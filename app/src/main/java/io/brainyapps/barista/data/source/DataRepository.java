package io.brainyapps.barista.data.source;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;

public class DataRepository implements DataSource {

    private static DataRepository INSTANCE = null;

    private final DataSource mLocal;

    // prevent direct initialisation
    private DataRepository(DataSource appLocalDataSource) {
        mLocal = appLocalDataSource;
    }

    // light singleton pattern
    public static DataRepository getInstance
    (DataSource appLocalDataSource) {
        if (INSTANCE == null) {

            INSTANCE = new DataRepository(appLocalDataSource);

        }

        return INSTANCE;
    }

    @Override
    public void getAllDrinks(final GetDrinksCallback callback) {
        mLocal.getAllDrinks(new GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                callback.onDrinksLoaded(drinks);
            }
        });
    }

    @Override
    public void saveDrink(Drink drink, final SaveCallback callback) {
        mLocal.saveDrink(drink, new SaveCallback() {
            @Override
            public void onSaved() {
                callback.onSaved();
            }
        });
    }

    @Override
    public void deleteDrink(Drink drink, final DeleteCallback callback) {
        mLocal.deleteDrink(drink, new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }
}
