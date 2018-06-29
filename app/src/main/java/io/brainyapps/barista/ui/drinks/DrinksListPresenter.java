package io.brainyapps.barista.ui.drinks;

import android.content.Context;

import java.util.List;

import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;

public class DrinksListPresenter implements DrinksListContract.Presenter {

    private DrinksListContract.View mView;
    private DrinksListContract.Adapter mAdapter;

    private DataRepository mData;

    public DrinksListPresenter(DrinksListContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);

        mData = AppDataInjector.provideDataRepository(context);
    }

    @Override
    public void setAdapter(DrinksListContract.Adapter adapter) {
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

    @Override
    public void deleteDrink(Drink drink) {
        mData.deleteDrink(drink, new DataSource.DeleteCallback() {
            @Override
            public void onDeleted() {
                mAdapter.deleteLastElement();
            }
        });
    }

    @Override
    public void saveDrink(Drink drink) {
        mData.saveDrink(drink, new DataSource.SaveCallback() {
            @Override
            public void onSaved() {
                mAdapter.addFirstElement(drink);
            }
        });
    }
}
