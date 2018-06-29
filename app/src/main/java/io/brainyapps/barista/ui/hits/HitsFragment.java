package io.brainyapps.barista.ui.hits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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



public class HitsFragment extends Fragment

            implements HitsListContract.View {

        private HitsListContract.Adapter mAdapter;

        private RecyclerView hitsListRecyclerView;

        private FloatingActionButton addFab, deleteFab;

        private ImageView imageViewBig;

        private HitsListContract.View mView = this;

        private List<Drink> mDrinks;

        private DataRepository mData;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_hits_list,
                    container, false);

            hitsListRecyclerView = view.findViewById
                    (R.id.hitsListRecyclerView);
            addFab = view.findViewById(R.id.addFab);
            deleteFab = view.findViewById(R.id.fabDelete);


            mData = AppDataInjector
                    .provideDataRepository(getContext());

            mData.getAllDrinks(drinks -> {
                mDrinks = drinks;
                HitsListAdapter hitsListAdapter =
                        new HitsListAdapter(mView, drinks);

              /*  RecyclerView.LayoutManager layoutManager =
                        new GridLayoutManager(getActivity(), 1);

                hitsListRecyclerView.setLayoutManager(layoutManager);*/
                GridLayoutManager gridHorizontal =
                        new GridLayoutManager(getActivity(),1,GridLayoutManager.HORIZONTAL,false);
                hitsListRecyclerView.setLayoutManager(gridHorizontal);

            /*
            StaggeredGridLayoutManager staggeredGridVertical=new
                    StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

            hitsListRecyclerView.setLayoutManager(staggeredGridVertical);
*/

                hitsListRecyclerView.setAdapter(hitsListAdapter);

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
    public void setAdapter(HitsListContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
        public void showToast(Drink drink) {
            Toast.makeText(getContext(), drink.getName() + " click",
                    Toast.LENGTH_SHORT).show();
        }

    }
