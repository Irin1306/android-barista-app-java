package io.brainyapps.barista.ui.drink;

import android.content.Context;
import android.support.v4.app.DialogFragment;



import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;


public class DrinkPresenter implements DrinkContract.Presenter {
    private String mTAG = MainActivity.TAG;

    private DrinkContract.View mView;
    private int mId;
    private DataRepository mData;

    public DrinkPresenter(DrinkContract.View view, int id, Context context) {
        mView = view;
        mView.setPresenter(this);
        mId = id;
        mData = AppDataInjector.provideDataRepository(context);

    }



    @Override
    public void getDrink() {
        mData.getDrinkById(mId, new DataSource.DrinkLoadedCallback() {
            @Override
            public void onDrinkLoaded(Drink drink) {

                if (mView != null && mView.isActive()) {
                    mView.setDrink(drink);
                }
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
               // Log.i(mTAG, "cartDrink -" + cartDrink);
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