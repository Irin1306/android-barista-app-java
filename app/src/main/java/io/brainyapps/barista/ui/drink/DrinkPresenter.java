package io.brainyapps.barista.ui.drink;

import android.content.Context;

import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;

public class DrinkPresenter implements DrinkContract.Presenter {

    private DrinkContract.View mView;

    private Drink mDrink;

    private DataRepository mData;

    public DrinkPresenter(DrinkContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);

        mData = AppDataInjector.provideDataRepository(context);
    }


}

