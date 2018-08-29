package io.brainyapps.barista.ui.history;

import java.util.List;

import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.Order;


public interface HistoryContract {
    interface View {
        void setPresenter(Presenter presenter);

        void setAdapter(Adapter adapter);

        void setListeners();

        boolean isActive();

       // void setOrders(List<Order> orders);

        void setOrdersWithOrderItems(List<OrderWithOrderItems> ordersWithOrderItems);

    }

    interface Adapter {
        void deleteAllOrders();
    }

    interface Presenter {
        void start();

        void setAdapter(Adapter adapter);

        void getOrders();

        //void getOrderItems(int id);

        //for test
        void getAllOrderItems();

        void deleteHistory();

    }
}
