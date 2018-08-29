package io.brainyapps.barista.data.source.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import io.brainyapps.barista.data.converter.DateConverter;
import io.brainyapps.barista.data.converter.ItemsIntConverter;
import io.brainyapps.barista.data.entity.CartDrink;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.entity.Order;
import io.brainyapps.barista.data.entity.OrderItem;
import io.brainyapps.barista.data.source.local.dao.CartDao;
import io.brainyapps.barista.data.source.local.dao.DrinkDao;
import io.brainyapps.barista.data.source.local.dao.OrderDao;
import io.brainyapps.barista.data.source.local.dao.OrderItemDao;
import io.brainyapps.barista.data.source.local.dao.OrderOrderItemsDao;

@Database(entities = {
        Drink.class,
        CartDrink.class,
        Order.class,
        OrderItem.class
}, version = 8)
@TypeConverters({DateConverter.class
        , ItemsIntConverter.class
})
public abstract class LocalDatabase extends RoomDatabase {


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

                                database.execSQL(
                                        "CREATE TABLE `cart` (`id` INTEGER, "
                                                + "`name` TEXT, `count` INTEGER, PRIMARY KEY(`id`))"
                                );
                            }
                        })
                        .addMigrations(new Migration(2, 3) {
                            @Override
                            public void migrate(@NonNull SupportSQLiteDatabase database) {

                                database.execSQL(
                                        "ALTER TABLE `drinks` "
                                                + " ADD COLUMN `price` REAL"
                                );
                                database.execSQL(
                                        "ALTER TABLE `cart` "
                                                + " ADD COLUMN `price` REAL"
                                );
                            }
                        })
                        .addMigrations(new Migration(3, 4) {
                            @Override
                            public void migrate(@NonNull SupportSQLiteDatabase database) {

                                database.execSQL(
                                        "CREATE TABLE `orders` (`id` INTEGER, "
                                                + "`name` TEXT, `date` Long,"
                                                + " `status` String, `items` TEXT, PRIMARY KEY(`id`))"
                                );
                            }
                        })
                        .addMigrations(new Migration(4, 5) {
                            @Override
                            public void migrate(@NonNull SupportSQLiteDatabase database) {
                                database.execSQL(
                                        "ALTER TABLE `drinks` "
                                                + " ADD COLUMN `ml` INTEGER"
                                );
                                database.execSQL(
                                        "ALTER TABLE `cart` "
                                                + " ADD COLUMN `ml` INTEGER"
                                );
                            }
                        })
                        .addMigrations(new Migration(5, 6) {
                            @Override
                            public void migrate(@NonNull SupportSQLiteDatabase database) {
                                database.execSQL(
                                        "CREATE TABLE `orderItems` (`id` INTEGER, `orderId` INTEGER,"
                                                + "`name` TEXT, `ml` INTEGER, `count` INTEGER, PRIMARY KEY(`id`)," +
                                                " FOREIGN KEY(`order_id`) REFERENCES `orders`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)"
                                );
                            }
                        })
                        .addMigrations(new Migration(6, 7) {
                            @Override
                            public void migrate(@NonNull SupportSQLiteDatabase database) {
                                database.execSQL(
                                        "CREATE TABLE IF NOT EXISTS `_orders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `orderName` TEXT, `date` INTEGER, `status` TEXT)"
                                );

                                database.execSQL("INSERT INTO _orders (orderName) SELECT name FROM orders");
                                database.execSQL("INSERT INTO _orders (date) SELECT date FROM orders");
                                database.execSQL("INSERT INTO _orders (status) SELECT status FROM orders");

                                database.execSQL("DROP TABLE orders");

                                database.execSQL("ALTER TABLE _orders RENAME TO orders");
                            }
                        })
                        .addMigrations(new Migration(7, 8) {
                            @Override
                            public void migrate(@NonNull SupportSQLiteDatabase database) {

                                database.execSQL("DROP TABLE drinks");

                                database.execSQL(
                                        "CREATE TABLE IF NOT EXISTS `drinks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `ml` INTEGER NOT NULL, `price` REAL NOT NULL)"
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

    public abstract OrderDao orderDao();

    public abstract OrderItemDao orderItemDao();

    public abstract OrderOrderItemsDao orderOrderItemsDao();
}
