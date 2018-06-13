package io.brainyapps.barista.ui.drinks;

import io.brainyapps.barista.data.entity.Drink;

public interface DrinksListContract {

    interface View {
        void setAdapter(Adapter adapter);

        void showToast(Drink drink);
    }

    interface Adapter {
        void deleteLastElement();

        void addFirstElement(Drink drink);
    }
}
