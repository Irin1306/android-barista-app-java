package io.brainyapps.barista.data.source.local;

import android.util.Log;

import java.util.List;

import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.data.entity.OrderItem;
import io.brainyapps.barista.data.source.DataSource;
import io.brainyapps.barista.data.source.local.dao.CartDao;
import io.brainyapps.barista.data.source.local.dao.DrinkDao;
import io.brainyapps.barista.data.source.local.dao.OrderDao;
import io.brainyapps.barista.data.source.local.dao.OrderItemDao;
import io.brainyapps.barista.data.source.local.dao.OrderOrderItemsDao;
import io.brainyapps.barista.util.AppExecutors;

public class AppLocalDataSource implements DataSource {
    private static final String TAG = AppLocalDataSource.class.getName();

    private static volatile AppLocalDataSource INSTANCE;

    private AppExecutors mExecutors;

    private DrinkDao mDrinkDao;
    private CartDao mCartDao;
    private OrderDao mOrderDao;
    private OrderItemDao mOrderItemDao;
    private OrderOrderItemsDao mOrderOrderItemsDao;

    private AppLocalDataSource(AppExecutors appExecutors,
                               DrinkDao drinkDao,
                               CartDao cartDao,
                               OrderDao orderDao,
                               OrderItemDao orderItemDao,
                               OrderOrderItemsDao orderOrderItemsDao
                               ) {
        mExecutors = appExecutors;
        mDrinkDao = drinkDao;
        mCartDao = cartDao;
        mOrderDao = orderDao;
        mOrderItemDao = orderItemDao;
        mOrderOrderItemsDao = orderOrderItemsDao;
    }

    /**
     * Singleton pattern
     */
    public static AppLocalDataSource
    getInstance(AppExecutors appExecutors,
                DrinkDao drinkDao,
                CartDao cartDao,
                OrderDao orderDao,
                OrderItemDao orderItemDao,
                OrderOrderItemsDao orderOrderItemsDao
                ) {
        if (INSTANCE == null) {
            synchronized (AppLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppLocalDataSource
                            (appExecutors, drinkDao, cartDao, orderDao, orderItemDao, orderOrderItemsDao);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void getAllDrinks(final GetDrinksCallback callback) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Drink> drinks =
                        mDrinkDao.getAllDrinks();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDrinksLoaded(drinks);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getDrinkById(final int id, final DrinkLoadedCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Drink drink = mDrinkDao.getDrinkById(id);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDrinkLoaded(drink);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveDrink(final Drink drink, final SaveCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mDrinkDao.saveDrink(drink);

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSaved();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteDrink(final Drink drink, final DeleteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mDrinkDao.deleteDrink(drink);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDeleted();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllDrinks(DeleteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mDrinkDao.deleteAllDrinks();
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDeleted();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getSearchResults(String string, GetDrinksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Drink> drinks = mDrinkDao.getSearchResults(string);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDrinksLoaded(drinks);
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getAllCartDrinks(final GetCartDrinksCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<CartDrink> cartDrinks = mCartDao.getAllCart();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCartDrinksLoaded(cartDrinks);
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getCartDrinkById(final int id, final CartDrinkLoadedCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final CartDrink cartDrink = mCartDao.getCartDrinkById(id);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCartDrinkLoaded(cartDrink);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getCartDrinkByName(String name, CartDrinkLoadedCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final CartDrink cartDrink = mCartDao.getCartDrinkByName(name);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCartDrinkLoaded(cartDrink);
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveCartDrink(final CartDrink cartDrink, final SaveCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCartDao.saveCartDrink(cartDrink);

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSaved();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteCartDrink(final CartDrink cartDrink, final DeleteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCartDao.deleteCartDrink(cartDrink);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDeleted();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllCartDrinks(DeleteCallback callback) {
//
    }

    @Override
    public void getAllOrders(final GetOrdersCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Order> orders =
                        mOrderDao.getAllOrders();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onOrdersLoaded(orders);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getAllOrdersByDate(GetOrdersCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Order> orders =
                        mOrderDao.getAllOrdersByDate();

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onOrdersLoaded(orders);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }


    @Override
    public void getOrderById(int id, OrderLoadedCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Order order = mOrderDao.getOrderById(id);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onOrderLoaded(order);
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getOrderByName(String name, OrderLoadedCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Order order = mOrderDao.getOrderByName(name);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "AppLocalDataSource getOrderByName " + order.toString());
                        callback.onOrderLoaded(order);
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveOrder(Order order, SaveCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mOrderDao.saveOrder(order);
                Log.i(TAG, "AppLocalDataSource saveOrder " + order.toString());
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSaved();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public long saveOrderAndReturnId(Order order, SaveAndReturnCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            final Long id = mOrderDao.saveOrderAndReturnId(order);
                Log.i(TAG, "AppLocalDataSource saveOrder id " + id);

                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSavedAndReturn(id);
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
        return 0;
    }

    @Override
    public void deleteOrder(Order order, DeleteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mOrderDao.deleteOrder(order);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDeleted();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllOrders(DeleteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mOrderDao.deleteAllOrders();
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDeleted();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getSearchResults(String string, GetOrdersCallback callback) {
//
    }

    @Override
    public void saveOrderItems(List<OrderItem> orderItems, SaveCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mOrderOrderItemsDao.saveOrderItems(orderItems);
                Log.i(TAG, "AppLocalDataSource saveOrderItems " + orderItems.toString());
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSaved();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    //for test
    @Override
    public void getAllOrderItems(GetOrdersItemsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<OrderItem> orderItems = mOrderItemDao.getAllOrderItems();
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onOrderItemsLoaded(orderItems);
                        Log.i(TAG, "AppLocalDataSource getAllOrderItems " + orderItems.toString());
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getOrderItemsByOrderId(int id, GetOrdersItemsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<OrderItem> orderItems = mOrderItemDao.getAllOrderItemsByOrderId(id);
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onOrderItemsLoaded(orderItems);
                        Log.i(TAG, "AppLocalDataSource getOrderItemsByOrderId " + orderItems.toString());
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getAllOrdersWithOrderItems(GetOrdersWithOrderItemsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<OrderWithOrderItems> ordersWithOrderItems =
                       //mOrderOrderItemsDao.getAllOrdersWithOrderItems();
                        mOrderDao.getAllOrdersWithOrderItems();
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onOrdersWithOrderItemsLoaded(ordersWithOrderItems);
                        Log.i(TAG, "AppLocalDataSource getAllOrdersWithOrderItems " + ordersWithOrderItems.toString());
                    }
                });
            }
        };

        mExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllOrdersAndOrderItems(DeleteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               // mOrderOrderItemsDao.deleteAllOrdersAndOrderItems();
                mOrderDao.deleteAllOrders();
                mExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDeleted();
                    }
                });
            }
        };
        mExecutors.diskIO().execute(runnable);
    }
}
