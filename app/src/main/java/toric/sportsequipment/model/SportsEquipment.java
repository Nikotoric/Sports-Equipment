package toric.sportsequipment.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "sportsequipment")
public class SportsEquipment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String name;
    private int type;
    private String description;
    private String photo;

}