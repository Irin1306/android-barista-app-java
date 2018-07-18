package io.brainyapps.barista.ui.drink;

import android.content.Context;

import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;

public class DrinkPresenter implements DrinkContract.Presenter {

    private DrinkContract.View mView;
    private int mId;
    private DataRepository mData;

    public DrinkPresenter(DrinkContract.View view, int id, Context context) {
        mView = view;
        mId = id;
        mData = AppDataInjector.provideDataRepository(context);

        start();
    }

    @Override
    public void start() {
        mView.setPresenter(this);
    }

    @Override
    public void getDrink() {
        mData.getDrinkById(mId, new DataSource.DrinkLoadedCallback() {
            @Override
            public void onDlinkLoaded(Drink drink) {

                if (mView != null && mView.isActive()) {
                    mView.setDrink(drink);
                }
            }
        });
    }
}