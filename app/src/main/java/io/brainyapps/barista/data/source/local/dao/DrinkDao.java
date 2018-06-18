package io.brainyapps.barista.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;

@Dao
public interface DrinkDao {

    @Query("SELECT * FROM drinks")
    List<Drink> getAllDrinks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveDrink(Drink drink);

    @Delete
    void deleteDrink(Drink drink);
}
