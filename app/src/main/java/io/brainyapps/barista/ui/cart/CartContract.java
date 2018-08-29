package io.brainyapps.barista.ui.cart;

import android.content.Context;

import java.util.List;

import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Order;


public interface CartContract {

    interface View {
        void setPresenter(CartContract.Presenter presenter);

        void setAdapter(CartContract.Adapter adapter);

        void setListeners();

        void setDrinks(List<CartDrink> cartDrinks);

        void takeTotalAmount(double total);

        void startDialog();

        void showToast(List<CartDrink> cartDrinks);

        void showToast(CartDrink cartDrink);


    }

    interface Adapter {

        void setCount(CartDrink cartDink, CartAdapter.ViewHolder holder);

        void deleteItem(CartDrink cartDink, int position);

        void makeOrder();

        void refresh();
    }

    interface Presenter {
        void start();

        void setAdapter(CartContract.Adapter adapter);

        void getDrinks();

        void getCount(CartDrink cartDink, CartAdapter.ViewHolder holder, int count);

        void deleteDrink(CartDrink cartDrink, int position);

       // void saveOrder(Order order);

        void saveOrderAndReturnId(Order order, List<CartDrink> cartDrinks);

        //void saveOrderItems(String name, List<CartDrink> cartDrinks);
    }
}
