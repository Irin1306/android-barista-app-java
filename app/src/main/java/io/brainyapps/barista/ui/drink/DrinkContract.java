package io.brainyapps.barista.ui.drink;


import io.brainyapps.barista.data.entity.Drink;

public interface DrinkContract {

    interface View {
        void setPresenter(Presenter presenter);

        void setDrink(Drink drink);

        boolean isActive();

        void setListeners();

        void startDialog();
    }

    interface Presenter {

        void getDrink();

        void addToCart(Drink drink);
    }
}