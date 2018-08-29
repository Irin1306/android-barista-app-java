package io.brainyapps.barista.data.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Long fromDate(Date date) {
        if (date==null) {
            return(null);
        }

        return(date.getTime());
    }

    @TypeConverter
    public static Date toDate(Long millisSinceEpoch) {
        if (millisSinceEpoch==null) {
            return(null);
        }

        return(new Date(millisSinceEpoch));
    }
   /* @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }*/
}
