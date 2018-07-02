package io.brainyapps.barista.ui.cart;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;


public interface CartContract {

    interface View {
        void setPresenter(CartContract.Presenter presenter);

        void setAdapter(CartContract.Adapter adapter);

        void setListeners();

        void setDrinks(List<Drink> drinks);

        void takeTotalAmount(Integer amount);

    }

    interface Adapter {
        //void deleteLastElement();

        //void addFirstElement(Drink drink);


        void addItem(Drink drink);

        void deleteItem(Drink drink);
    }

    interface Presenter {
        void start();

        void setAdapter(CartContract.Adapter adapter);

        void getDrinks();

        // void deleteDrink(Drink drink);

        // void saveDrink(Drink drink);

        void addQty(Drink drink);

        void deleteQty(Drink drink);
    }
}
