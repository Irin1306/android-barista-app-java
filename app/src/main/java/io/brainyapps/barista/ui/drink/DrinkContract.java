package io.brainyapps.barista.ui.drink;


public interface DrinkContract {

    interface View {
        void setPresenter(Presenter presenter);

        boolean isActive();
    }

    interface Presenter {
        void start();
    }
}
