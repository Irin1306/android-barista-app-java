package io.brainyapps.barista.data.source.local.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import io.brainyapps.barista.data.data.OrderWithOrderItems;
import io.brainyapps.barista.data.entity.Order;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM orders")
    List<Order> getAllOrders();

    @Query("SELECT * FROM orders WHERE id = :id")
    Order getOrderById(int id);

    @Query("SELECT * FROM orders WHERE orderName = :name")
    Order getOrderByName(String name);

    @Query("SELECT * FROM orders WHERE orderName LIKE '%' || :searchString || '%'")
    List<Order> getSearchResults(String searchString);

    @Query("SELECT * FROM orders ORDER BY date DESC")
    List<Order> getAllOrdersByDate();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   // @TypeConverters({DateConverter.class})
    void saveOrder(Order order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
        // @TypeConverters({DateConverter.class})
    long saveOrderAndReturnId(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("DELETE FROM orders")
    void deleteAllOrders();

    @Transaction
    @Query("SELECT * FROM orders ORDER BY date DESC")
    List<OrderWithOrderItems> getAllOrdersWithOrderItems();
}