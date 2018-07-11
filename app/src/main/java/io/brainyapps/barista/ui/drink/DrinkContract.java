package io.brainyapps.barista.ui.drink;


public interface DrinkContract {

    interface View {
        void setPresenter(DrinkContract.Presenter presenter);

        void setListeners();


    }


    interface Presenter {


    }
}
