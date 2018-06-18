package io.brainyapps.barista.data;

import android.content.Context;

import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.local.AppLocalDataSource;
import io.brainyapps.barista.data.source.local.LocalDatabase;

public class AppDataInjector {

    public static DataRepository provideDataRepository(Context context) {
        LocalDatabase database = LocalDatabase.getInstance(context);
        return DataRepository.getInstance(
                AppLocalDataSource.getInstance(
                        database.drinkDao()
                )
        );
    }
}