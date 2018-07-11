package io.brainyapps.barista.ui.drink;



import java.util.List;

import io.brainyapps.barista.data.entity.Drink;

public interface DrinkContract {

    interface View {
        void setPresenter(DrinkContract.Presenter presenter);

        void setListeners();


    }



    interface Presenter {




    }
}
