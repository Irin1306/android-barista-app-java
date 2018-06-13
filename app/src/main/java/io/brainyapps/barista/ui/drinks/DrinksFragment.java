package io.brainyapps.barista.ui.drinks;

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

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;

public class DrinksFragment extends Fragment
        implements DrinksListContract.View {

    private DrinksListContract.Adapter mAdapter;

    private RecyclerView drinksListRecyclerView;

    private FloatingActionButton addFab, deleteFab;

    private DrinksListContract.View mView = this;

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

        DataRepository repository = AppDataInjector
                .provideDataRepository(getContext());

        repository.getAllDrinks(new DataSource.GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                DrinksListAdapter drinksListAdapter =
                        new DrinksListAdapter(mView, drinks);

                RecyclerView.LayoutManager layoutManager =
                        new GridLayoutManager(getActivity(), 1);

                drinksListRecyclerView.setLayoutManager(layoutManager);

                drinksListRecyclerView.setAdapter(drinksListAdapter);
            }
        });

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
