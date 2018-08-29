package io.brainyapps.barista.data.source;

import java.util.List;

import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.data.entity.OrderItem;

public interface DataSource {

    void getAllDrinks(GetDrinksCallback callback);

    void getDrinkById(int id, DrinkLoadedCallback callback);

    void saveDrink(Drink drink, SaveCallback callback);

    void deleteDrink(Drink drink, DeleteCallback callback);

    void deleteAllDrinks(DeleteCallback callback);

    void getSearchResults(String string, GetDrinksCallback callback);

    void getAllCartDrinks(GetCartDrinksCallback callback);

    void getCartDrinkById(int id, CartDrinkLoadedCallback callback);

    void getCartDrinkByName(String name, CartDrinkLoadedCallback callback);

    void saveCartDrink(CartDrink cartDrink, SaveCallback callback);

    void deleteCartDrink(CartDrink cartDrink, DeleteCallback callback);

    void deleteAllCartDrinks(DeleteCallback callback);

    void getAllOrders(GetOrdersCallback callback);

    void getAllOrdersByDate(GetOrdersCallback callback);

    void getOrderById(int id, OrderLoadedCallback callback);

    void getOrderByName(String name, OrderLoadedCallback callback);

    void saveOrder(Order order, SaveCallback callback);

    long saveOrderAndReturnId(Order order, SaveAndReturnCallback callback);

    void deleteOrder(Order order, DeleteCallback callback);

    void deleteAllOrders(DeleteCallback callback);

    void getSearchResults(String string, GetOrdersCallback callback);

    void saveOrderItems(List<OrderItem> orderItems, SaveCallback callback);

    //for test
    void getAllOrderItems(GetOrdersItemsCallback callback);

    void getOrderItemsByOrderId(int id, GetOrdersItemsCallback callback);

    void getAllOrdersWithOrderItems(GetOrdersWithOrderItemsCallback callback);

    void deleteAllOrdersAndOrderItems(DeleteCallback callback);



    interface SaveCallback {
        void onSaved();
    }

    interface SaveAndReturnCallback {
        void onSavedAndReturn(Long id);
    }

    interface DeleteCallback {
        void onDeleted();
    }

    interface GetDrinksCallback {
        void onDrinksLoaded(List<Drink> drinks);
    }

    interface DrinkLoadedCallback {
        void onDrinkLoaded(Drink drink);
    }

    interface CartDrinkLoadedCallback {
        void onCartDrinkLoaded(CartDrink cartDrink);
    }

    interface GetCartDrinksCallback {
        void onCartDrinksLoaded(List<CartDrink> cartDrinks);
    }

    interface OrderLoadedCallback {
        void onOrderLoaded(Order order);
    }

    interface GetOrdersCallback {
        void onOrdersLoaded(List<Order> orders);
    }

    interface GetOrdersItemsCallback {
        void onOrderItemsLoaded(List<OrderItem> orderItems);
    }

    interface GetOrdersWithOrderItemsCallback {
        void onOrdersWithOrderItemsLoaded(List<OrderWithOrderItems> ordersWithOrderItems);
    }


}