package io.brainyapps.barista.data.source.local;

import java.util.ArrayList;
import java.util.List;

import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataSource;
import io.brainyapps.barista.data.source.local.dao.DrinkDao;

public class AppLocalDataSource implements DataSource {

    // TODO: доклад по оператору volatile

    private static volatile AppLocalDataSource INSTANCE;

    private DrinkDao mDrinkDao;

    private AppLocalDataSource(DrinkDao drinkDao) {
        mDrinkDao = drinkDao;
    }

    /**
     * Singleton pattern
     */
    public static AppLocalDataSource getInstance(DrinkDao drinkDao) {
        if (INSTANCE == null) {
            synchronized (AppLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppLocalDataSource(drinkDao);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void getAllDrinks(GetDrinksCallback callback) {
        List<Drink> drinks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Drink drink = new Drink();
            drink.setId(i);
            drink.setName("Name " + i);

            drinks.add(drink);
        }

        callback.onDrinksLoaded(drinks);
    }
}
