package io.brainyapps.barista.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

@Entity(tableName = "cart")
public class CartDrink extends Drink {

    private int count;

    @Ignore
    public CartDrink(String name, int count) {
        super(name);
        this.count = count;
    }

    public CartDrink(int id, String name, int count) {
        super(id, name);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
