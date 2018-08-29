package io.brainyapps.barista.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "cart")
public class CartDrink extends Drink {

    @ColumnInfo(name = "count")
    private int count;

    // constructor for new drink
    @Ignore
    public CartDrink(String name, int ml, double price) {
        super(name, ml, price);

    }

    // constructor for update
    public CartDrink(int id, String name, int ml, double price) {
        super(id, name, ml, price);

    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double takeTotal() { return (double)this.count * super.getPrice(); }

}
