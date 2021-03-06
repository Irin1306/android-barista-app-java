package io.brainyapps.barista.ui.drinks;

import java.util.List;

import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;

public interface DrinksListContract {

    interface View {
        void setPresenter(Presenter presenter);

        void setAdapter(Adapter adapter);

        void setListeners();

        void setDrinks(List<Drink> drinks);

        void showToast(Drink drink);

        void showToast(CartDrink cartDrink);

        void startDrinkDetails(int id);

        void startDialog();
    }

    interface Adapter {
        void deleteLastElement();

        void addFirstElement(Drink drink);
    }

    interface Presenter {
        void setAdapter(Adapter adapter);

        void getDrinks();

        void deleteDrink(Drink drink);

        void saveDrink(Drink drink);

        void makeSearch(String searchString);

        void addToCart(Drink drink);
    }
}
