package io.brainyapps.barista.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.brainyapps.barista.data.entity.CartDrink;


@Dao
public interface CartDao {

    @Query("SELECT * FROM cart")
    List<CartDrink> getAllCart();

    @Query("SELECT * FROM cart WHERE id = :id")
    CartDrink getCartDrinkById(int id);

    @Query("SELECT * FROM cart WHERE name = :name")
    CartDrink getCartDrinkByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCartDrink(CartDrink cartDrink);

    @Delete
    void deleteCartDrink(CartDrink cartDrink);

    @Query("DELETE FROM cart")
    void deleteAllCart();
}
