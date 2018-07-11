package io.brainyapps.barista.ui.drink;

import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;

public class DrinkPresenter implements DrinkContract.Presenter {

    private DrinkContract.View mView;

    private Drink mDrink;

    private DataRepository mData;

    public DrinkPresenter() {
    }


}

