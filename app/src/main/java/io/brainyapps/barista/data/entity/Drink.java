package io.brainyapps.barista.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "drinks")
public class Drink {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "ml")
    private int ml;

    @ColumnInfo(name = "price")
    private double price;

    // constructor for new drink
    @Ignore
    public Drink(String name, int ml, double price) {
        this.name = name;
        this.ml = ml;
        this.price = price;
    }

    // constructor for update
    public Drink(int id, String name, int ml, double price) {
        this.id = id;
        this.name = name;
        this.ml = ml;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMl(int ml) {
        this.ml = ml;
    }

    public int getMl() {
        return ml;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

}
