package io.brainyapps.barista.ui.history;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.entity.Order;


public class HistoryFragment extends Fragment implements HistoryContract.View {

    private String mTAG = MainActivity.TAG;

    private HistoryContract.Presenter mPresenter;

    private HistoryContract.Adapter mAdapter;

    private RecyclerView historyListRecyclerView;


    private TextView deleteTv, emptyTextView;

    private CardView mainCardView;

    private HistoryContract.View mView = this;

    //private List<Order> mOrders;

    private List<OrderWithOrderItems> mOrdersWithOrderItems;


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

        int resId = R.anim.layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        historyListRecyclerView.setLayoutAnimation(animation);


        deleteTv = view.findViewById(R.id.deleteTextView);
        emptyTextView = view.findViewById(R.id.emptyTextView);
        mainCardView = view.findViewById(R.id.mainCardView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getOrders();
        //for test
       // mPresenter.getAllOrderItems();

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
        deleteTv.setOnClickListener(null);
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
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setListeners() {
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteHistory();
            }
        });
    }

    @Override
    public void setOrdersWithOrderItems(List<OrderWithOrderItems> ordersWithOrderItems) {
        mOrdersWithOrderItems = ordersWithOrderItems;
        Log.i(mTAG, "HistoryFragment setOrders: ordersItems.size() " + ordersWithOrderItems.get(0).getOrderItems().size());
        emptyTextView.setVisibility(View.GONE);
        mainCardView.setVisibility(View.VISIBLE);

        HistoryAdapter historyListAdapter =
                new HistoryAdapter(mView, mPresenter, ordersWithOrderItems);

        GridLayoutManager gridVertical =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        historyListRecyclerView.setLayoutManager(gridVertical);

        historyListRecyclerView.setAdapter(historyListAdapter);

        //runLayoutAnimation(historyListRecyclerView);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {

        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }



}