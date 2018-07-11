package io.brainyapps.barista.data.source;

import java.util.List;

import io.brainyapps.barista.data.entity.CartDrink;
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
    public void getDrinkById(int id, DrinkLoadedCallback callback) {
        mLocal.getDrinkById(id, new DrinkLoadedCallback() {
            @Override
            public void onDlinkLoaded(Drink drink) {
                callback.onDlinkLoaded(drink);
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

    @Override
    public void getSearchResults(String string, final GetDrinksCallback callback) {
        mLocal.getSearchResults(string, new GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                callback.onDrinksLoaded(drinks);
            }
        });
    }

    @Override
    public void getAllCartDrinks(GetCartDrinksCallback callback) {
        mLocal.getAllCartDrinks(new GetCartDrinksCallback() {
            @Override
            public void onCartDrinksLoaded(List<CartDrink> cartDrinks) {
                callback.onCartDrinksLoaded(cartDrinks);
            }
        });
    }

    @Override
    public void saveCartDrink(CartDrink cartDrink, SaveCallback callback) {
        mLocal.saveCartDrink(cartDrink, new SaveCallback() {
            @Override
            public void onSaved() {
                callback.onSaved();
            }
        });
    }

    @Override
    public void deleteCartDrink(CartDrink cartDrink, DeleteCallback callback) {
        mLocal.deleteCartDrink(cartDrink, new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }
}
