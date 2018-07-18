package io.brainyapps.barista.data.source;

import java.util.List;

import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;

public interface DataSource {

    void getAllDrinks(GetDrinksCallback callback);

    void getDrinkById(int id, DrinkLoadedCallback callback);

    void saveDrink(Drink drink, SaveCallback callback);

    void deleteDrink(Drink drink, DeleteCallback callback);

    void getSearchResults(String string, GetDrinksCallback callback);

    void getAllCartDrinks(GetCartDrinksCallback callback);

    void saveCartDrink(CartDrink cartDrink, SaveCallback callback);

    void deleteCartDrink(CartDrink cartDrink, DeleteCallback callback);

    interface SaveCallback {
        void onSaved();
    }

    interface DeleteCallback {
        void onDeleted();
    }

    interface GetDrinksCallback {
        void onDrinksLoaded(List<Drink> drinks);
    }

    interface DrinkLoadedCallback {
        void onDlinkLoaded(Drink drink);
    }

    interface GetCartDrinksCallback {
        void onCartDrinksLoaded(List<CartDrink> cartDrinks);
    }
}