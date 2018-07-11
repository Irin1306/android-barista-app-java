package io.brainyapps.barista.ui.drink;

public class DrinkPresenter implements DrinkContract.Presenter {

    private DrinkContract.View mView;

    public DrinkPresenter(DrinkContract.View view) {
        mView = view;

        start();
    }

    @Override
    public void start() {
        mView.setPresenter(this);

        if (mView != null && mView.isActive()) {
            // TODO: что то делаем
        }
    }
}
