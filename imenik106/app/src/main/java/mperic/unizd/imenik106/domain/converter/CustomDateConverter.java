package mperic.unizd.imenik106.domain.converter;

import androidx.room.TypeConverter;

import java.util.Date;

public class CustomDateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
