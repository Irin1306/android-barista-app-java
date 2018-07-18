package io.brainyapps.barista.ui.drink;


import io.brainyapps.barista.data.entity.Drink;

public interface DrinkContract {

    interface View {
        void setPresenter(Presenter presenter);

        void setDrink(Drink drink);

        boolean isActive();
    }

    interface Presenter {
        void start();

        void getDrink();
    }
}