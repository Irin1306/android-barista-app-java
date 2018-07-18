package io.brainyapps.barista.ui.hits;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;

public class HitsFragment extends Fragment implements HitsContract.View {

    private HitsContract.Presenter mPresenter;

    private RecyclerView hitsListRecyclerView;

    private HitsContract.Adapter mAdapter;

    private List<Drink> mDrinks;

    private HitsContract.View mView = this;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new HitsPresenter(this, context.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hits, container, false);
        hitsListRecyclerView = view.findViewById(R.id.hitsListRecyclerView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getDrinks();

//        setListeners();
    }



    @Override
    public void setPresenter(HitsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAdapter(HitsContract.Adapter adapter) {
        mAdapter = adapter;

    }

//    @Override
//    public void setListeners() {
//
//    }

    @Override
    public void setDrinks(List<Drink> drinks) {
        mDrinks = drinks;
        HitsAdapter hitsAdapter = new HitsAdapter(mView, mPresenter, drinks);
        GridLayoutManager gridHorizontal =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        hitsListRecyclerView.setLayoutManager(gridHorizontal);
        hitsListRecyclerView.setAdapter(hitsAdapter);


    }
}