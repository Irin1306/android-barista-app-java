package io.brainyapps.barista.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.OrderItem;

@Dao
public interface OrderItemDao {
    @Query("SELECT * FROM orderItems")
    List<OrderItem> getAllOrderItems();

    @Query("SELECT * FROM orderItems WHERE id = :id")
    OrderItem getOrderItemById(int id);

    @Query("SELECT * FROM orderItems WHERE order_id = :order_id")
    List<OrderItem> getAllOrderItemsByOrderId(int order_id);

    @Query("SELECT * FROM orderItems WHERE name = :name")
    OrderItem getOrderItemByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveOrderItem(OrderItem orderItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllOrderItems(List<OrderItem> orderItems);

    @Delete
    void deleteOrderItem(OrderItem orderItem);

    @Query("DELETE FROM orderItems")
    void deleteAllOrderItems();
}
