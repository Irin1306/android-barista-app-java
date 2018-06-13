package io.brainyapps.barista.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.brainyapps.barista.data.entity.Drink;

@Dao
public interface DrinkDao {

    @Query("SELECT * FROM drinks")
    List<Drink> getAllDrinks();
}
