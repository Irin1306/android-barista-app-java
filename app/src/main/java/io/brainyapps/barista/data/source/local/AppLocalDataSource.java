package io.brainyapps.barista.data.source.local;

import java.util.List;

import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataSource;
import io.brainyapps.barista.data.source.local.dao.CartDao;
import io.brainyapps.barista.data.source.local.dao.DrinkDao;
import io.brainyapps.barista.util.AppExecutors;

public class AppLocalDataSource implements DataSource {
    private static final String TAG = AppLocalDataSource.class.getName();

    // TODO: доклад по оператору volatile

    private static volatile AppLocalDataSource INSTANCE;

    private AppExecutors mExecutors;

    private DrinkDao mDrinkDao;
    private CartDao mCart;

    private AppLocalDataSource(AppExecutors appExecutors,
                               DrinkDao drinkDao,
                               CartDao cartDao) {
        mExecutors = appExecutors;
        mDrinkDao = drinkDao;
        mCart = cartDao;
    }

    /**
     * Singleton pattern
     */
    public static AppLocalDataSource
    getInstance(AppExecutors appExecutors, DrinkDao drinkDao, CartDao cartDao) {
        if (INSTANCE == null) {
            synchronized (AppLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppLocalDataSource
                            (appExecutors, drinkDao, cartDao);
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
                final List<Drink> drinks =
                        mDrinkDao.getAllDrinks();

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

    @Override
    public void getDrinkById(final int id, final DrinkLoadedCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Drink drink = mDrinkDao.getDringById(id);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDlinkLoaded(drink);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveDrink(final Drink drink, final SaveCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mDrinkDao.saveDrink(drink);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSaved();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteDrink(final Drink drink, final DeleteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mDrinkDao.deleteDrink(drink);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDeleted();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getSearchResults(String string, GetDrinksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Drink> drinks = mDrinkDao.getSearchResults(string);
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

    @Override
    public void getAllCartDrinks(GetCartDrinksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<CartDrink> cartDrinks = mCart.getALlCart();
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCartDrinksLoaded(cartDrinks);
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveCartDrink(CartDrink cartDrink, SaveCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCart.saveCartDrink(cartDrink);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSaved();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteCartDrink(CartDrink cartDrink, DeleteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCart.deleteCartDrink(cartDrink);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDeleted();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }
}
