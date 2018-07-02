package io.brainyapps.barista.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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



public class HistoryFragment extends Fragment implements HistoryContract.View {

    private String mTAG = MainActivity.TAG;

    private HistoryContract.Presenter mPresenter;

    private HistoryContract.Adapter mAdapter;

    private RecyclerView historyListRecyclerView;

    private FloatingActionButton addFab, deleteFab;

    private TextView deleteTv;

    private CardView mainCardView;

    private HistoryContract.View mView = this;

    private List<Drink> mDrinks;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new HistoryPresenter(this, context.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        historyListRecyclerView = view.findViewById
                (R.id.historyListRecyclerView);
        deleteTv = view.findViewById(R.id.deleteTextView);
        mainCardView = view.findViewById(R.id.mainCardView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getDrinks();
        setListeners();
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAdapter(HistoryContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void setListeners() {
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrinks.clear();
                Log.i(mTAG, "deleteTv " + mDrinks.size() + " " + this);
                Toast.makeText(getContext(), mDrinks.size() + " mDrinks" + "this " + this,
                        Toast.LENGTH_SHORT).show();
                mainCardView.setVisibility(View.GONE);
                mPresenter.deleteHistory();
            }
        });
    }

    @Override
    public void setDrinks(List<Drink> drinks) {
        mDrinks = drinks;
        HistoryAdapter historyListAdapter =
                new HistoryAdapter(mView, mPresenter, drinks);

        GridLayoutManager gridVertical =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        historyListRecyclerView.setLayoutManager(gridVertical);

        historyListRecyclerView.setAdapter(historyListAdapter);
    }
}