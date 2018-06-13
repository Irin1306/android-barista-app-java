package io.brainyapps.barista.data.source.local;

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
}
