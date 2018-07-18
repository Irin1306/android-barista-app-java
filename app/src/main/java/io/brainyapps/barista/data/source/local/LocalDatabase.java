package io.brainyapps.barista.data.source.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.local.dao.CartDao;
import io.brainyapps.barista.data.source.local.dao.DrinkDao;

@Database(entities = {
        Drink.class,
        CartDrink.class
}, version = 2)
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
                        .addMigrations(new Migration(1, 2) {
                            @Override
                            public void migrate(@NonNull SupportSQLiteDatabase database) {
                                // TODO: DZ - write SQL query for migration
                                database.execSQL(
                                        ""
                                );
                            }
                        })
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract DrinkDao drinkDao();

    public abstract CartDao cartDao();
}
