package toric.sportsequipment.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import toric.sportsequipment.model.SportsEquipment;

@Dao
public interface SeDAO {

    @Query("SELECT * FROM sportsequipment ORDER BY id DESC")
    LiveData<List<SportsEquipment>> takeSportsEquipment();

    @Insert
    void addNewItem(SportsEquipment sportsEquipment);

    @Update
    void updateItem(SportsEquipment sportsEquipment);

    @Delete
    void deleteItem(SportsEquipment sportsEquipment);
}
