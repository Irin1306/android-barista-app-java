package io.brainyapps.barista.ui.drink;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;


public class DrinkFragment extends Fragment
        implements DrinkContract.View {

    private DrinkContract.Presenter mPresenter;

    private DrinkContract.View mView = this;

    private List<Drink> mDrinks;

    private Drink mDrink;

    private DataRepository mData;

    private int mId;

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