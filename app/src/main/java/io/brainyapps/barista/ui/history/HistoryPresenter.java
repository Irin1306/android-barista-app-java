package io.brainyapps.barista.ui.history;

import android.content.Context;

import java.util.List;

import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;



public class HistoryPresenter implements HistoryContract.Presenter {

    private HistoryContract.View mView;

    private HistoryContract.Adapter mAdapter;

    private DataRepository mData;

    private List<Drink> mDrinks;

    public HistoryPresenter(HistoryContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mData = AppDataInjector.provideDataRepository(context);
        start();
    }

    @Override
    public void start() {
        //
    }

    @Override
    public void setAdapter(HistoryContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void getDrinks() {
        mData.getAllDrinks(new DataSource.GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                if (drinks.size() <= 5) {

                    mView.setDrinks(drinks);

                }
                else {

                    mDrinks = drinks.subList((drinks.size() - 6), (drinks.size() - 1));
                    mView.setDrinks(mDrinks);
                }

            }
        });
    }

    @Override
    public void deleteHistory() {
        //
    }


}
