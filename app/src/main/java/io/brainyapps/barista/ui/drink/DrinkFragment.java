package io.brainyapps.barista.ui.drink;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.brainyapps.barista.R;


public class DrinkFragment extends Fragment
        implements DrinkContract.View {

    private DrinkContract.Presenter mPresenter;

    private DrinkContract.View mView = this;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new DrinkPresenter(this, context.getApplicationContext());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drink,
                container, false);

       /*        DrinkActivity activity = (DrinkActivity) getActivity();
        mId = activity.getMId();

        Toast.makeText(getActivity(), "" + mId, Toast.LENGTH_SHORT).show();

        mData.getAllDrinks(new DataSource.GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                mDrink = findDrink(mId, drinks);
            }
        });
*/
        return view;
    }

   /* Drink findDrink(int id, List<Drink> drinks) {
        for (Drink drink : drinks) {
            if ((id + "").equals(drink.getId() + "")) {
                return drink;
            }

        }
        return null;
    }
*/


    @Override
    public void setPresenter(DrinkContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setListeners() {

    }


}