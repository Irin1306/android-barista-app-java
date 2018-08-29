package io.brainyapps.barista.ui.history;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.data.entity.OrderItem;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;



public class HistoryPresenter implements HistoryContract.Presenter {
    private String mTAG = MainActivity.TAG;

    private HistoryContract.View mView;

    private HistoryContract.Adapter mAdapter;

    private DataRepository mData;

    private List<Order> mOrders;

    public HistoryPresenter(HistoryContract.View view, Context context) {
        mView = view;
        mView.setPresenter(this);
        mData = AppDataInjector.provideDataRepository(context);
        start();
    }

    @Override
    public void start() {
        //
    }

    @Override
    public void setAdapter(HistoryContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void getOrders() {
        mData.getAllOrdersWithOrderItems(new DataSource.GetOrdersWithOrderItemsCallback() {
            @Override
            public void onOrdersWithOrderItemsLoaded(List<OrderWithOrderItems> ordersWithOrderItems) {

                if (mView != null && mView.isActive() && ordersWithOrderItems.size() > 0) {

                    mView.setOrdersWithOrderItems(ordersWithOrderItems);
                }
            }
        });

       /* mData.getAllOrders(new DataSource.GetOrdersCallback() {
            @Override
            public void onOrdersLoaded(List<Order> orders) {
                if (orders.size() <= 5) {
                    mView.setOrders(orders);
                }
                else {
                    mOrders = orders.subList((orders.size() - 6), (orders.size() - 1));
                    mView.setOrders(mOrders);
                }

            }
        });*/

    }
    //for test
    @Override
    public void getAllOrderItems() {
        mData.getAllOrderItems(new DataSource.GetOrdersItemsCallback() {
            @Override
            public void onOrderItemsLoaded(List<OrderItem> orderItems) {
                Log.i(mTAG, "HistoryPresenter getAllOrderItems: " + orderItems.toString());
            }
        });
    }

   /* @Override
    public void getOrderItems(int id) {
        mData.getOrderItemsByOrderId(id, new DataSource.GetOrdersItemsCallback() {
            @Override
            public void onOrderItemsLoaded(List<OrderItem> orderItems) {
                Log.i(mTAG, "HistoryPresenter getOrderItems: " + orderItems.toString());
            }
        });
    }*/

    @Override
    public void deleteHistory() {
        mData.deleteAllOrders(new DataSource.DeleteCallback() {
            @Override
            public void onDeleted() {
                mAdapter.deleteAllOrders();
            }
        });
    }


}
