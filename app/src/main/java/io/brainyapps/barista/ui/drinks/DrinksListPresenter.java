package io.brainyapps.barista.ui.drinks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;

public class DrinksListPresenter implements DrinksListContract.Presenter {
    private String mTAG = MainActivity.TAG;

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
                //Log.i(mTAG, "Long id " + id);
                mAdapter.addFirstElement(drink);
            }
        });
    }

    @Override
    public void makeSearch(String searchString) {
        mData.getSearchResults(searchString, new DataSource.GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                mView.setDrinks(drinks);
            }
        });
    }

    @Override
    public void addToCart(Drink drink) {

        CartDrink _cartDrink = new CartDrink(drink.getName(), drink.getMl(), drink.getPrice());
        _cartDrink.setCount(1);

        mData.getCartDrinkByName(_cartDrink.getName(), new DataSource.CartDrinkLoadedCallback() {
            @Override
            public void onCartDrinkLoaded(final CartDrink cartDrink) {

                if (cartDrink != null) {
                    cartDrink.setCount(cartDrink.getCount() + 1);

                    mData.saveCartDrink(cartDrink, new DataSource.SaveCallback() {
                        @Override
                        public void onSaved() {
                           mView.startDialog();
                        }
                    });
                } else {

                    mData.saveCartDrink(_cartDrink, new DataSource.SaveCallback() {
                        @Override
                        public void onSaved() {
                            mView.startDialog();
                        }
                    });

                }

            }
        });
    }
}
