package io.brainyapps.barista.data.source.local;

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

        Runnable runnable = ()->{

                final List<Drink> drinks =
                        mDrinkDao.getAllDrinks();

                mExecutors.mainThread().execute(() -> callback.onDrinksLoaded(drinks));

        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveDrink(final Drink drink, final SaveCallback callback) {
        Runnable runnable =  ()->{
                mDrinkDao.saveDrink(drink);
                mExecutors.mainThread().execute(callback::onSaved);

        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteDrink(final Drink drink, final DeleteCallback callback) {
        Runnable runnable =  ()->{

                mDrinkDao.deleteDrink(drink);
                mExecutors.mainThread().execute(callback::onDeleted);

        };
        mExecutors.diskIO().execute(runnable);
    }
}
