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
}
