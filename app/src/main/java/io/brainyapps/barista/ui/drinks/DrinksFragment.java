package io.brainyapps.barista.ui.drinks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private ImageView imageViewBig;

    private DrinksListContract.View mView = this;

    private List<Drink> mDrinks;

    private DataRepository mData;

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


        mData = AppDataInjector
                .provideDataRepository(getContext());

        mData.getAllDrinks(drinks -> {
            mDrinks = drinks;
            DrinksListAdapter drinksListAdapter =
                    new DrinksListAdapter(mView, drinks);

           /* RecyclerView.LayoutManager layoutManager =
                    new GridLayoutManager(getActivity(), 1);*/
           /* drinksListRecyclerView.setLayoutManager(layoutManager);*/


           /* StaggeredGridLayoutManager staggeredGridVertical=new
                    StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

            drinksListRecyclerView.setLayoutManager(staggeredGridVertical);*/

            GridLayoutManager gridHorizontal =
                    new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false);
            drinksListRecyclerView.setLayoutManager(gridHorizontal);
           /* drinksListRecyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int height = drinksListRecyclerView.getMeasuredHeight();
            int width = drinksListRecyclerView.getMeasuredWidth();
            Log.i("kkk", "drinksListRecyclerVie height: " + height + "drinksListRecyclerVie width: " + width);
*/
            drinksListRecyclerView.setAdapter(drinksListAdapter);
        });

        addFab.setOnClickListener(v ->{
                final Drink drink = new Drink("Espresso");//("Drink " + (mDrinks.size() + 1));

                mData.saveDrink(drink, () -> mAdapter.addFirstElement(drink));

        });
        deleteFab.setOnClickListener(v -> {
            if (mDrinks.size() == 0) {
                return;
            }

            Drink drink = mDrinks.get(mDrinks.size() - 1);

            mData.deleteDrink(drink, ()-> mAdapter.deleteLastElement());
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