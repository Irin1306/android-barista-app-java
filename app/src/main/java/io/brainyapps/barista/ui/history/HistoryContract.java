package io.brainyapps.barista.ui.history;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;


public interface HistoryContract {
    interface View {
        void setPresenter(HistoryContract.Presenter presenter);

        void setAdapter(HistoryContract.Adapter adapter);

        void setListeners();

        void setDrinks(List<Drink> drinks);
    }

    interface Adapter {
        //void deleteLastElement();

        //void addFirstElement(Drink drink);
    }

    interface Presenter {
        void start();

        void setAdapter(HistoryContract.Adapter adapter);

        void getDrinks();

        void deleteHistory();

        // void deleteDrink(Drink drink);

        // void saveDrink(Drink drink);
    }
}
