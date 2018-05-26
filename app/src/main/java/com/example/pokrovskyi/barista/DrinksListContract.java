package com.example.pokrovskyi.barista;

import com.example.pokrovskyi.barista.data.Drink;

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
