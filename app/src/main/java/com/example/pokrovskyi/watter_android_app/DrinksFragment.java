package com.example.pokrovskyi.watter_android_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pokrovskyi.watter_android_app.data.Drink;

import java.util.ArrayList;
import java.util.List;

public class DrinksFragment extends Fragment
        implements DrinksListContract.View {

    private DrinksListContract.Adapter mAdapter;

    private RecyclerView drinksListRecyclerView;

    private FloatingActionButton addFab, deleteFab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.flagment_drinks_list,
                container, false);

        drinksListRecyclerView = view.findViewById
                (R.id.drinksListRecyclerView);
        addFab = view.findViewById(R.id.addFab);
        deleteFab = view.findViewById(R.id.fabDelete);

        List<Drink> drinks = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Drink drink = new Drink();
            drink.setId(i);
            drink.setName("Drink #" + i);

            drinks.add(drink);
        }

        DrinksListAdapter drinksListAdapter =
                new DrinksListAdapter(this, drinks);

        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1);

        drinksListRecyclerView.setLayoutManager(layoutManager);

        drinksListRecyclerView.setAdapter(drinksListAdapter);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drink drink = new Drink();
                drink.setName("Drink from fragment");

                mAdapter.addFirstElement(drink);
            }
        });
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.deleteLastElement();
            }
        });

        return view;
    }

    @Override
    public void setAdapter(DrinksListContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void showToast(Drink drink) {
        Toast.makeText(getContext(), drink.getName() + " click",
                Toast.LENGTH_SHORT).show();
    }
}
