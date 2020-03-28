package toric.sportsequipment.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import toric.sportsequipment.model.SportsEquipment;

//singleton
@Database(entities = {SportsEquipment.class}, version = 1, exportSchema = false)
public abstract class SeDataBase extends RoomDatabase {

    public abstract SeDAO seDAO();

    private static SeDataBase INSTANCE;

    public static SeDataBase getDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SeDataBase.class, "seDataBase").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
