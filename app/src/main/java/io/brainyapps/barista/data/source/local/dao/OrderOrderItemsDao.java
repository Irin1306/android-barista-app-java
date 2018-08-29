package io.brainyapps.barista.data.source.local.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.data.entity.OrderItem;

@Dao
public abstract class OrderOrderItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveAllOrderItems(List<OrderItem> orderItems);

    @Query("DELETE FROM cart")
    public abstract void deleteAllCart();

    @Transaction
    public void saveOrderItems(List<OrderItem> orderItems) {

        saveAllOrderItems(orderItems);
        deleteAllCart();
    }


/*
    @Query("SELECT * FROM orders")
    public abstract List<Order> getAllOrders();

    @Query("SELECT * FROM orderItems")
    public abstract List<OrderItem> getAllOrderItems();

    @Transaction
    public void getAllOrderAndOrderItems() {
        getAllOrders();
        getAllOrderItems();
    }*/

   /* @Query("DELETE FROM orders")
    public abstract void deleteAllOrders();

    @Query("DELETE FROM orderItems")
    public abstract void deleteAllOrderItems();

    @Transaction
    public void deleteAllOrdersAndOrderItems() {
        deleteAllOrders();
        deleteAllOrderItems();
    }*/
}