package io.brainyapps.barista.ui.hits;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;


public interface HitsContract {

    interface View {
        void setPresenter(HitsContract.Presenter presenter);

        void setAdapter(HitsContract.Adapter adapter);

        //void setListeners();

        void setDrinks(List<Drink> drinks);
    }

    interface Adapter {
        //void deleteLastElement();

        //void addFirstElement(Drink drink);
    }

    interface Presenter {
        void start();

        void setAdapter(HitsContract.Adapter adapter);

        void getDrinks();

       // void deleteDrink(Drink drink);

       // void saveDrink(Drink drink);
    }
}
