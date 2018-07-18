package io.brainyapps.barista.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;

@Dao
public interface CartDao {

    @Query("SELECT * FROM cart")
    List<CartDrink> getALlCart();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCartDrink(Drink drink);

    @Delete
    void deleteCartDrink(CartDrink cartDrink);

    // TODO: Delete all?
}
