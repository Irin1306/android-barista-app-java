package io.brainyapps.barista.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.util.DisplayAlertDialog;


public class CartFragment extends Fragment implements CartContract.View {

    private String mTAG = MainActivity.TAG;

    private CartContract.Presenter mPresenter;

    private CartContract.Adapter mAdapter;

    private RecyclerView cartListRecyclerView;

    private TextView backToChoosing, makeOrder;

    private FloatingActionButton addFab, deleteFab;

    private TextView totalTv;

    private double total = 0;

    private CartContract.View mView = this;

    private List<CartDrink> mDrinks;




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
        //for smooth scrolling
        cartListRecyclerView.setNestedScrollingEnabled(false);

        totalTv = view.findViewById(R.id.totalTextView);
        totalTv.setText(String.valueOf(total) + " ₴");
        backToChoosing = view.findViewById(R.id.backToChoosing);
        makeOrder = view.findViewById(R.id.makeOrder);

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
        setListeners();
    }

    @Override
    public void onPause() {
        super.onPause();
        total = 0;
        backToChoosing.setOnClickListener(null);
        makeOrder.setOnClickListener(null);
    }

    @Override
    public void onStop() {
        super.onStop();
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
        backToChoosing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("item_ind", 1);
                startActivity(intent);
            }
        });
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(mTAG, "CartFragment makeOrder(total) " + total);

                if (total > 0) {
                    mAdapter.makeOrder();
                }

            }
        });
    }

    @Override
    public void setDrinks(List<CartDrink> cartDrinks) {
        mDrinks = cartDrinks;
        CartAdapter cartListAdapter =
                new CartAdapter(mView, mPresenter, cartDrinks);

        GridLayoutManager gridVertical =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        cartListRecyclerView.setLayoutManager(gridVertical);

        cartListRecyclerView.setAdapter(cartListAdapter);


        //for (int i = 0; i < mDrinks.size(); i++) {
        //    Log.i(mTAG, "mFragment mDrinks i" + i + ": " + mDrinks.get(i).getName());
       // }
       // Log.i(mTAG, "mFragment mDrinks.size() " + mDrinks.size() + ": " + mDrinks);

    }


    @Override
    public void takeTotalAmount(double amount) {
        if (amount != 0) {
            total += amount;
            totalTv.setText(String.valueOf(total) + " ₴");
        } else {
            total = 0;


        }
    }

    @Override
    public void startDialog() {
        DisplayAlertDialog.displayAlertDialog(getContext(), R.string.dialog_make_order, R.string.go);
    }



    @Override
    public void showToast(List<CartDrink> cartDrinks) {
        Toast.makeText(getContext(), cartDrinks.size() + "cartDrinks",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(CartDrink cartDrink) {
        Toast.makeText(getContext(), cartDrink  + "cartDrink",
                Toast.LENGTH_SHORT).show();
    }


}