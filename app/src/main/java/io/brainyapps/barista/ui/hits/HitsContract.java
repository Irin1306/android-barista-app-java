package io.brainyapps.barista.ui.hits;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.ui.history.HistoryContract;

public interface HitsContract {

    interface View {
        void setPresenter(Presenter presenter);

        void setAdapter(HitsContract.Adapter adapter);

//        void setListeners();

        void setDrinks(List<Drink> drinks);
    }

    interface Adapter {
    }

    interface Presenter {
        void start();

        void setAdapter(HitsContract.Adapter adapter);

        void getDrinks();
    }
}