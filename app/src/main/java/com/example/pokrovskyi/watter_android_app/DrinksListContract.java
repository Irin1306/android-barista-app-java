package com.example.pokrovskyi.watter_android_app;

import com.example.pokrovskyi.watter_android_app.data.Drink;

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
