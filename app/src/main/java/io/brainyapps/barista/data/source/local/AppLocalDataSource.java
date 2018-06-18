package io.brainyapps.barista.data.source.local;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataSource;
import io.brainyapps.barista.data.source.local.dao.DrinkDao;
import io.brainyapps.barista.util.AppExecutors;

public class AppLocalDataSource implements DataSource {
    private static final String TAG = AppLocalDataSource.class.getName();

    // TODO: доклад по оператору volatile

    private static volatile AppLocalDataSource INSTANCE;

    private AppExecutors mExecutors;

    private DrinkDao mDrinkDao;

    private AppLocalDataSource(AppExecutors appExecutors,
                               DrinkDao drinkDao) {
        mExecutors = appExecutors;
        mDrinkDao = drinkDao;
    }

    /**
     * Singleton pattern
     */
    public static AppLocalDataSource
    getInstance(AppExecutors appExecutors, DrinkDao drinkDao) {
        if (INSTANCE == null) {
            synchronized (AppLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppLocalDataSource
                            (appExecutors, drinkDao);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void getAllDrinks(final GetDrinksCallback callback) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Drink> drinks = new ArrayList<>();

                for (int i = 0; i < 100000; i++) {
                    Drink drink = new Drink();
                    drink.setId(i);
                    drink.setName("Name " + i);

                    drinks.add(drink);

                    Log.i(TAG, "Iteration = " + i);
                }

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDrinksLoaded(drinks);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }
}
