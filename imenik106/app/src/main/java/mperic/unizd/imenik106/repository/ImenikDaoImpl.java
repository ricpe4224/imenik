package mperic.unizd.imenik106.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import mperic.unizd.imenik106.domain.PersonModel;
@Database(entities = {PersonModel.class}, version = 1, exportSchema = false)
public abstract class ImenikDaoImpl extends RoomDatabase {

    public abstract ImenikDao imenikDao();

    private static ImenikDaoImpl INSTANCE;

    public static ImenikDaoImpl getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ImenikDaoImpl.class, "imenik-impl").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
