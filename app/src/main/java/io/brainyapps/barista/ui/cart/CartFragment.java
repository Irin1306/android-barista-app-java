package io.brainyapps.barista.ui.cart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;



public class CartFragment extends Fragment implements CartContract.View {

    private String mTAG = MainActivity.TAG;

    private CartContract.Presenter mPresenter;

    private CartContract.Adapter mAdapter;

    private RecyclerView cartListRecyclerView;

    private FloatingActionButton addFab, deleteFab;

    private TextView totalTv;

    private int total = 0;

    private CartContract.View mView = this;

    private List<Drink> mDrinks;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new CartPresenter(this, context.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartListRecyclerView = view.findViewById(R.id.cartListRecyclerView);
        totalTv = view.findViewById(R.id.totalTextView);
        totalTv.setText(String.valueOf(total) + " ₴");


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getDrinks();
        setListeners();
    }

    @Override
    public void setPresenter(CartContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAdapter(CartContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void setListeners() {
        //
    }

    @Override
    public void setDrinks(List<Drink> drinks) {
        mDrinks = drinks;
        CartAdapter cartListAdapter =
                new CartAdapter(mView, mPresenter, drinks);

        GridLayoutManager gridVertical =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        cartListRecyclerView.setLayoutManager(gridVertical);

        cartListRecyclerView.setAdapter(cartListAdapter);
    }

    @Override
    public void takeTotalAmount(Integer amount) {
        if (amount != 0) {
            total += amount;
            totalTv.setText(String.valueOf(total) + " ₴");

        } else {
            total = 0;
            if(mDrinks.size() == 0) {
                totalTv.setText(String.valueOf(total) + " ₴");
            }
        }
    }


}