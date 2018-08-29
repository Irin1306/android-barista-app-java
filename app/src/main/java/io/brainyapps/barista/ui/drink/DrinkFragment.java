package io.brainyapps.barista.ui.drink;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;

import io.brainyapps.barista.util.DisplayAlertDialog;


public class DrinkFragment extends Fragment
        implements DrinkContract.View {

    private String mTAG = MainActivity.TAG;

    private String mDRINK_ID = DrinkActivity.DRINK_ID;

    private DrinkContract.Presenter mPresenter;

    private TextView nameTextView, priceTextView;
    private AppCompatImageView cartImageView;

    private Drink mDrink;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            int id = bundle.getInt(mDRINK_ID);
            //Toast.makeText(getContext(), "DrinkFragment id = " + id, Toast.LENGTH_SHORT).show();
            new DrinkPresenter(this, id, getContext());

        }

        View view = inflater.inflate(R.layout.drink,
                container, false);

        nameTextView = view.findViewById(R.id.nameTextView);
        cartImageView = view.findViewById(R.id.cartImageView);
        priceTextView = view.findViewById(R.id.priceTextView);
        //setRetainInstance(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getDrink();
        setListeners();
       // Toast.makeText(getContext(), "Drink onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setListeners();
    }

    @Override
    public void setPresenter(DrinkContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setDrink(Drink drink) {
        mDrink = drink;
        nameTextView.setText(drink.getName());
        priceTextView.setText(String.valueOf(drink.getPrice()) + " ₴");

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setListeners() {
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.addToCart(mDrink);
            }
        });
    }

    @Override
    public void startDialog() {
        DisplayAlertDialog.displayAlertDialog(getContext(), R.string.dialog_add_to_cart, R.string.go);
    }

    @Override
    public void onPause() {
        super.onPause();
        cartImageView.setOnClickListener(null);
        //Toast.makeText(getContext(), "Drink onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getContext(), "Drink onStop", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Toast.makeText(getContext(), "Drink onDetach", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getContext(), "Drink onDestroy", Toast.LENGTH_SHORT).show();
    }

}