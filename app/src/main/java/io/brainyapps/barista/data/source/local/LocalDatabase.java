package io.brainyapps.barista.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.local.dao.DrinkDao;

@Database(entities = {
        Drink.class
}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    // TODO: доклад о Object
    private static final Object sLock = new Object();
    private static LocalDatabase INSTANCE;

    /**
     * Singleton pattern
     */
    public static LocalDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        LocalDatabase.class,
                        "database.db")
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract DrinkDao drinkDao();
}
