package io.brainyapps.barista.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.List;


@Entity(tableName = "orders")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "orderName")
    private String orderName;

    @ColumnInfo(name = "date")
    private Date creationDate;

    @ColumnInfo(name = "status")
    private String status;


    //not in used
   // @ColumnInfo(name = "items")
   // public List<Integer> items;



    // constructor for new order
    @Ignore
    public Order(String orderName, Date creationDate) {
        this.orderName = orderName;
        this.creationDate = creationDate;

    }

    // constructor for update
    public Order(int id, String orderName, Date creationDate) {
        this.id = id;
        this.orderName = orderName;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    //public void setId(int id) {
       // this.id = id;
    //}

    public String getOrderName() {
        return orderName;
    }

    //public void setName(String name) {
       // this.name = name;
   // }

    public Date getCreationDate() { return this.creationDate; }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
