package io.brainyapps.barista.data.source;

import java.util.List;

import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.data.entity.OrderItem;

public class DataRepository implements DataSource {


    private static DataRepository INSTANCE = null;

    private final DataSource mLocal;

    // prevent direct initialisation
    private DataRepository(DataSource appLocalDataSource) {
        mLocal = appLocalDataSource;
    }

    // light singleton pattern
    public static DataRepository getInstance
    (DataSource appLocalDataSource) {
        if (INSTANCE == null) {

            INSTANCE = new DataRepository(appLocalDataSource);

        }

        return INSTANCE;
    }

    @Override
    public void getAllDrinks(final GetDrinksCallback callback) {
        mLocal.getAllDrinks(new GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                callback.onDrinksLoaded(drinks);
            }
        });
    }

    @Override
    public void getDrinkById(int id, DrinkLoadedCallback callback) {
        mLocal.getDrinkById(id, new DrinkLoadedCallback() {
            @Override
            public void onDrinkLoaded(Drink drink) {
                callback.onDrinkLoaded(drink);
            }
        });
    }

    @Override
    public void saveDrink(Drink drink, final SaveCallback callback) {
        mLocal.saveDrink(drink, new SaveCallback() {
            @Override
            public void onSaved() {
                callback.onSaved();
            }
        });
    }

    @Override
    public void deleteDrink(Drink drink, final DeleteCallback callback) {
        mLocal.deleteDrink(drink, new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }

    @Override
    public void deleteAllDrinks(DeleteCallback callback) {
        mLocal.deleteAllDrinks(new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }

    @Override
    public void getSearchResults(String string, final GetDrinksCallback callback) {
        mLocal.getSearchResults(string, new GetDrinksCallback() {
            @Override
            public void onDrinksLoaded(List<Drink> drinks) {
                callback.onDrinksLoaded(drinks);
            }
        });
    }

    @Override
    public void getAllCartDrinks(final GetCartDrinksCallback callback) {
        mLocal.getAllCartDrinks(new GetCartDrinksCallback() {
            @Override
            public void onCartDrinksLoaded(List<CartDrink> cartDrinks) {
                callback.onCartDrinksLoaded(cartDrinks);
            }
        });
    }

    @Override
    public void getCartDrinkById(int id, CartDrinkLoadedCallback callback) {
        mLocal.getCartDrinkById(id, new CartDrinkLoadedCallback() {
            @Override
            public void onCartDrinkLoaded(CartDrink cartDrink) {
                callback.onCartDrinkLoaded(cartDrink);
            }
        });
    }

    @Override
    public void getCartDrinkByName(String name, CartDrinkLoadedCallback callback) {
        mLocal.getCartDrinkByName(name, new CartDrinkLoadedCallback() {
            @Override
            public void onCartDrinkLoaded(CartDrink cartDrink) {
                callback.onCartDrinkLoaded(cartDrink);
            }
        });
    }

    @Override
    public void saveCartDrink(CartDrink cartDrink, final SaveCallback callback) {
        mLocal.saveCartDrink(cartDrink, new SaveCallback() {
            @Override
            public void onSaved() {
                callback.onSaved();
            }
        });
    }

    @Override
    public void deleteCartDrink(CartDrink cartDrink, final DeleteCallback callback) {
        mLocal.deleteCartDrink(cartDrink, new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }

    @Override
    public void deleteAllCartDrinks(DeleteCallback callback) {
        mLocal.deleteAllCartDrinks(new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }

    @Override
    public void getAllOrders(GetOrdersCallback callback) {
        mLocal.getAllOrders(new GetOrdersCallback() {
            @Override
            public void onOrdersLoaded(List<Order> orders) {
                callback.onOrdersLoaded(orders);
            }
        });
    }

    @Override
    public void getAllOrdersByDate(GetOrdersCallback callback) {
        mLocal.getAllOrdersByDate (new GetOrdersCallback() {
            @Override
            public void onOrdersLoaded(List<Order> orders) {
                callback.onOrdersLoaded(orders);
            }
        });
    }

    @Override
    public void getOrderById(int id, OrderLoadedCallback callback) {
        mLocal.getOrderById(id, new OrderLoadedCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                callback.onOrderLoaded(order);
            }
        });
    }

    @Override
    public void getOrderByName(String name, OrderLoadedCallback callback) {
        mLocal.getOrderByName(name, new OrderLoadedCallback() {
            @Override
            public void onOrderLoaded(Order order) {
                    callback.onOrderLoaded(order);
            }
        });
    }

    @Override
    public void saveOrder(Order order, SaveCallback callback) {

        mLocal.saveOrder(order, new SaveCallback() {
            @Override
            public void onSaved() {
                 callback.onSaved();
            }
        });

    }

    @Override
    public long saveOrderAndReturnId(Order order, SaveAndReturnCallback callback) {
        mLocal.saveOrderAndReturnId(order, new SaveAndReturnCallback() {
            @Override
            public void onSavedAndReturn(Long id) {
                callback.onSavedAndReturn(id);
            }
        });
        return 0;
    }

    @Override
    public void deleteOrder(Order order, DeleteCallback callback) {
        mLocal.deleteOrder(order, new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }

    @Override
    public void deleteAllOrders(DeleteCallback callback) {
        mLocal.deleteAllOrders(new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }

    @Override
    public void getSearchResults(String string, GetOrdersCallback callback) {
        mLocal.getSearchResults(string, new GetOrdersCallback()  {
            @Override
            public void onOrdersLoaded(List<Order> orders) {
                callback.onOrdersLoaded(orders);
            }
        });
    }

    @Override
    public void saveOrderItems(List<OrderItem> orderItems, SaveCallback callback) {
        mLocal.saveOrderItems(orderItems, new SaveCallback() {
            @Override
            public void onSaved() {
                callback.onSaved();
            }
        });
    }

    //for test
    @Override
    public void getAllOrderItems(GetOrdersItemsCallback callback) {
        mLocal.getAllOrderItems(new GetOrdersItemsCallback() {
            @Override
            public void onOrderItemsLoaded(List<OrderItem> orderItems) {
                callback.onOrderItemsLoaded(orderItems);
            }
        });
    }

    @Override
    public void getOrderItemsByOrderId(int id, GetOrdersItemsCallback callback) {
        mLocal.getOrderItemsByOrderId(id, new GetOrdersItemsCallback() {
            @Override
            public void onOrderItemsLoaded(List<OrderItem> orderItems) {
                callback.onOrderItemsLoaded(orderItems);
            }
        });
    }

    @Override
    public void getAllOrdersWithOrderItems(GetOrdersWithOrderItemsCallback callback) {
        mLocal.getAllOrdersWithOrderItems(new GetOrdersWithOrderItemsCallback() {
            @Override
            public void onOrdersWithOrderItemsLoaded(List<OrderWithOrderItems> ordersWithOrderItems) {
                callback.onOrdersWithOrderItemsLoaded(ordersWithOrderItems);
            }
        });
    }

    @Override
    public void deleteAllOrdersAndOrderItems(DeleteCallback callback) {
        mLocal.deleteAllOrdersAndOrderItems(new DeleteCallback() {
            @Override
            public void onDeleted() {
                callback.onDeleted();
            }
        });
    }
}
