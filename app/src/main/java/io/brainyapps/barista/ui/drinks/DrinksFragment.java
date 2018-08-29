package io.brainyapps.barista.ui.drinks;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.ui.drink.DrinkActivity;
import io.brainyapps.barista.util.DisplayAlertDialog;


public class DrinksFragment extends Fragment
        implements DrinksListContract.View, SearchView.OnQueryTextListener {

    private DrinksListContract.Presenter mPresenter;

    private DrinksListContract.Adapter mAdapter;

    private io.brainyapps.barista.GridRecycleView drinksListRecyclerView;

    private SearchView mSearchView;

    private FloatingActionButton addFab, deleteFab;

    private DrinksListContract.View mView = this;

    private List<Drink> mDrinks;

    private Animation mAnimation;



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

        int resId = R.anim.grid_layout_animation_from_bottom;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        drinksListRecyclerView.setLayoutAnimation(animation);


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
    public void onResume() {
        super.onResume();
        if (mSearchView != null) {

            mSearchView.setQuery("", true);
            mSearchView.setOnQueryTextListener(this);
        }
        setListeners();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //if remove menu icon
        // menu.clear();

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setOnQueryTextListener(this);

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
                final Drink drink = new Drink("Espresso" + (mDrinks.size() + 1 ), 300,25);//("Drink " + (mDrinks.size() + 1));

                mPresenter.saveDrink(drink);
                mPresenter.getDrinks();
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
                new GridLayoutManager(getActivity(), 3, GridLayoutManager.HORIZONTAL, false);


        drinksListRecyclerView.setLayoutManager(gridHorizontal);

        drinksListRecyclerView.setAdapter(drinksListAdapter);

        runLayoutAnimation(drinksListRecyclerView);
    }


    private void runLayoutAnimation(final RecyclerView recyclerView) {

        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void showToast(Drink drink) {
        Toast.makeText(getContext(), drink.getName() + "drink click",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(CartDrink cartDrink) {
        Toast.makeText(getContext(), cartDrink.getName() + "cartDrink click",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startDrinkDetails(int id) {
        Intent intent = new Intent(getContext(), DrinkActivity.class);

        //Toast.makeText(getContext(), "Drinks Fragment id = " + id, Toast.LENGTH_SHORT).show();
        intent.putExtra("drink_id", id);

        startActivity(intent);
    }

    @Override
    public void startDialog() {
        DisplayAlertDialog.displayAlertDialog(getContext(), R.string.dialog_add_to_cart, R.string.go);

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

    @Override
    public void onPause() {
        super.onPause();
        mSearchView.setOnQueryTextListener(null);
        //Toast.makeText(getContext(), "Drinks Fragment onPause Adapter " + (mAdapter != null), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getContext(), "Drinks Fragment onStop", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Toast.makeText(getContext(), "Drinks Fragment onDetach", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getContext(), "Drinks Fragment onDestroy", Toast.LENGTH_SHORT).show();
    }
}