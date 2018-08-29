package io.brainyapps.barista.ui.cart;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.data.AppDataInjector;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.data.entity.OrderItem;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;


public class CartPresenter implements CartContract.Presenter {
    private String mTAG = MainActivity.TAG;

    private CartContract.View mView;

    private CartContract.Adapter mAdapter;

    private DataRepository mData;

    public CartPresenter(CartContract.View view, Context context) {
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
    public void setAdapter(CartContract.Adapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void getDrinks() {
        mData.getAllCartDrinks(new DataSource.GetCartDrinksCallback() {
            @Override
            public void onCartDrinksLoaded(List<CartDrink> cartDrinks) {
                mView.setDrinks(cartDrinks);
            }
        });
    }

    @Override
    public void getCount(CartDrink _cartDrink, CartAdapter.ViewHolder holder, int count) {

        mData.getCartDrinkById(_cartDrink.getId(), new DataSource.CartDrinkLoadedCallback() {
            @Override
            public void onCartDrinkLoaded(final CartDrink cartDrink) {
                Log.i(mTAG, "mPresenter.getCount cartDrink " + cartDrink + " mPresenter.getCount cartDrink " + (cartDrink.getCount() + count));
                cartDrink.setCount(cartDrink.getCount() + count);

                mData.saveCartDrink(cartDrink, new DataSource.SaveCallback() {
                    @Override
                    public void onSaved() {
                        mAdapter.setCount(cartDrink, holder);
                        Log.i(mTAG, "mPresenter.mAdapter.setCount " + cartDrink.getCount());
                    }
                });
            }
        });

    }


    @Override
    public void deleteDrink(CartDrink cartDrink, int position) {
        mData.deleteCartDrink(cartDrink, new DataSource.DeleteCallback() {
            @Override
            public void onDeleted() {
                mAdapter.deleteItem(cartDrink, position);
            }
        });
    }

    @Override
    public void saveOrderAndReturnId(Order order, List<CartDrink> cartDrinks) {
        order.setStatus("ready");

        mData.getAllOrdersByDate(new DataSource.GetOrdersCallback() {
            @Override
            public void onOrdersLoaded(List<Order> orders) {

                if (orders.size() > 4) {
                    mData.deleteOrder(orders.get(4), new DataSource.DeleteCallback() {
                        @Override
                        public void onDeleted() {
                            Log.i(mTAG, "CartPresenter getAllOrdersByDate.size()" + orders.size());
                        }
                    });
                }

                mData.saveOrderAndReturnId(order, new DataSource.SaveAndReturnCallback() {
                    @Override
                    public void onSavedAndReturn(Long id) {
                        Log.i(mTAG, "CartPresenter saveOrderAndReturnId " + id);
                        mData.getOrderById(id.intValue(), new DataSource.OrderLoadedCallback() {
                            @Override
                            public void onOrderLoaded(Order order) {
                                Log.i(mTAG, "CartPresenter getOrderById " + order.toString() + " order id " + order.getId());

                                ArrayList<OrderItem> mItems = new ArrayList<OrderItem>();

                                for (CartDrink cartDrink : cartDrinks) {
                                    final OrderItem orderItem = new OrderItem(
                                            id.intValue(),
                                            cartDrink.getName(),
                                            cartDrink.getMl(),
                                            cartDrink.getPrice()
                                    );
                                    mData.getCartDrinkById(cartDrink.getId(), new DataSource.CartDrinkLoadedCallback() {
                                        @Override
                                        public void onCartDrinkLoaded(CartDrink cartDrink) {

                                            orderItem.setCount(cartDrink.getCount());

                                            mItems.add(orderItem);

                                            if (mItems.size() == cartDrinks.size()) {
                                                mData.saveOrderItems(mItems, new DataSource.SaveCallback() {
                                                    @Override
                                                    public void onSaved() {
                                                        Log.i(mTAG, "save OrderAndOrderItem mItems.size()" + mItems.size());
                                                        mAdapter.refresh();
                                                        mView.takeTotalAmount(0);
                                                        mView.startDialog();
                                                    }
                                                });
                                            }
                                        }
                                    });

                                }

                            }
                        });
                    }
                });
            }
        });

    }

    /*@Override
    public void saveOrder(Order order) {
        order.setStatus("ready");

        mData.saveOrder(order, new DataSource.SaveCallback() {
            @Override
            public void onSaved () {
                Log.i(mTAG, "CartPresenter saveOrder");

            }
        });

    }*/
 /*
    @Override
    public void saveOrderItems(String name, List<CartDrink> cartDrinks) {
       // try {
            mData.getOrderByName(name, new DataSource.OrderLoadedCallback() {
                @Override
                public void onOrderLoaded(Order order) {
                    Log.i(mTAG, "CartPresenter getOrderByName " + order.toString() + " order id " + order.getId());

                    ArrayList<OrderItem> mItems = new ArrayList<OrderItem>();

                    for (CartDrink cartDrink : cartDrinks) {
                        final OrderItem orderItem = new OrderItem(
                                order.getId(),
                                cartDrink.getName(),
                                cartDrink.getMl(),
                                cartDrink.getPrice()
                        );
                        mData.getCartDrinkByName(cartDrink.getName(), new DataSource.CartDrinkLoadedCallback() {
                            @Override
                            public void onCartDrinkLoaded(CartDrink cartDrink) {

                                orderItem.setCount(cartDrink.getCount());

                                mItems.add(orderItem);

                                if (mItems.size() == cartDrinks.size()) {
                                    mData.saveOrderItems(mItems, new DataSource.SaveCallback() {
                                        @Override
                                        public void onSaved() {
                                            Log.i(mTAG, "save OrderAndOrderItem mItems.size()" + mItems.size());
                                            mAdapter.refresh();

                                            mView.startDialog();
                                        }
                                    });
                                }
                            }
                        });

                    }

                }
            });

    }*/

}
