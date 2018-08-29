package io.brainyapps.barista.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "orderItems",
        foreignKeys = @ForeignKey(entity = Order.class, parentColumns = "id",
                childColumns = "order_id",
                onDelete = CASCADE),
        indices = {@Index(value = {"order_id"}
        )}
)
public class OrderItem extends CartDrink{

    @ColumnInfo(name = "order_id")
    private int orderId;

    // constructor for new
    @Ignore
    public OrderItem(int orderId, String name, int ml, double price) {
        super(name, ml, price);
        this.orderId = orderId;

    }

    // constructor for update
    public OrderItem(int id, int orderId, String name, int ml, double price) {
        super(id, name, ml, price);
        this.orderId = orderId;

    }

//    @Override
//    public int getCount() {
//        return super.getCount();
//    }
//
//    @Override
//    public void setCount(int count) {
//        super.setCount(count);
//    }

    public int getOrderId() {
        return orderId;
    }

//    @Override
//    public String getName() {
//        return super.getName();
//    }
//
//    @Override
//    public double getPrice() {
//        return super.getPrice();
//    }

//
    @Override
    public double takeTotal() { return (double)super.getCount() * super.getPrice(); }


}
