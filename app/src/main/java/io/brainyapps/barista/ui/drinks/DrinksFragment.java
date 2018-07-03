package io.brainyapps.barista.ui.drinks;

import android.content.Context;
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


public class DrinksFragment extends Fragment
        implements DrinksListContract.View, SearchView.OnQueryTextListener {

    private DrinksListContract.Presenter mPresenter;

    private DrinksListContract.Adapter mAdapter;

    private RecyclerView drinksListRecyclerView;

    private FloatingActionButton addFab, deleteFab;

    private DrinksListContract.View mView = this;

    private List<Drink> mDrinks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new DrinksListPresenter(this, context.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flagment_drinks_list,
                container, false);

        setHasOptionsMenu(true);

        drinksListRecyclerView = view.findViewById
                (R.id.drinksListRecyclerView);
        addFab = view.findViewById(R.id.addFab);
        deleteFab = view.findViewById(R.id.fabDelete);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getDrinks();

        setListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public void setPresenter(DrinksListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAdapter(DrinksListContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void setListeners() {
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Drink drink = new Drink("Espresso" + (mDrinks.size() + 1));//("Drink " + (mDrinks.size() + 1));

                mPresenter.saveDrink(drink);
            }
        });

        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrinks.size() == 0) {
                    return;
                }

                Drink drink = mDrinks.get(mDrinks.size() - 1);

                mPresenter.deleteDrink(drink);
            }
        });
    }

    @Override
    public void setDrinks(List<Drink> drinks) {
        mDrinks = drinks;
        DrinksListAdapter drinksListAdapter =
                new DrinksListAdapter(mView, mPresenter, drinks);

        GridLayoutManager gridHorizontal =
                new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        drinksListRecyclerView.setLayoutManager(gridHorizontal);

        drinksListRecyclerView.setAdapter(drinksListAdapter);
    }

    @Override
    public void showToast(Drink drink) {
        Toast.makeText(getContext(), drink.getName() + " click",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        mPresenter.makeSearch(newText);

        return true;
    }
}