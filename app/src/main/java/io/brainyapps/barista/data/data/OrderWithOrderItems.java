package io.brainyapps.barista.data.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.util.Log;

import java.util.Date;
import java.util.List;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.data.entity.OrderItem;



public class OrderWithOrderItems {

   @Embedded
    public Order order;

   @Relation(parentColumn = "id", entityColumn = "order_id", entity = OrderItem.class)
    public List<OrderItem> orderItems;


    public Order getOrder() {
        return order;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getOrderName() {
        return order.getOrderName();
    }

    public int getOrderId() {
        return order.getId();
    }

    public Date getCreationDate() { return order.getCreationDate(); }

    public String getStatus() {
        return order.getStatus();
    }

    public String getName() {
        String name = "";
        for(OrderItem orderItem : orderItems) name = orderItem.getName();
        return name;
    }

    public int getMl() {
        int ml = 0;
        for(OrderItem orderItem : orderItems) ml = orderItem.getMl();
        return ml;
    }

    public int getCount() {
        int count = 0;
        for(OrderItem orderItem : orderItems) count = orderItem.getCount();
        return count;
    }

    public double getPrice() {
        double price = 0;
        for(OrderItem orderItem : orderItems) price = orderItem.getPrice();
        return price;
    }

    public double takeTotalAmount() {
        double total = 0;
        for(OrderItem orderItem : orderItems) total += orderItem.takeTotal();
        return total;
    }

}
