package io.brainyapps.barista.ui.cart;

import android.content.Context;

import java.util.List;

import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;


public class CartPresenter implements CartContract.Presenter {

    private CartContract.View mView;

    private CartContract.Adapter mAdapter;

    private DataRepository mData;

    public CartPresenter(CartContract.View view, Context context) {
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
    public void setAdapter(CartContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void getDrinks() {
        mData.getAllDrinks(new DataSource.GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                mView.setDrinks(drinks);
            }
        });
    }



}
