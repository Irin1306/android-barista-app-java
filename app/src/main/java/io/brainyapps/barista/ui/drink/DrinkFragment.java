package io.brainyapps.barista.ui.drink;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;


public class DrinkFragment extends Fragment
        implements DrinkContract.View {

    private DrinkContract.Presenter mPresenter;

    private TextView nameTextView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getDrink();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drink,
                container, false);

        nameTextView = view.findViewById(R.id.nameTextView);

        //

        return view;
    }

    @Override
    public void setPresenter(DrinkContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setDrink(Drink drink) {
        // TODO:
        nameTextView.setText(drink.getName());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}