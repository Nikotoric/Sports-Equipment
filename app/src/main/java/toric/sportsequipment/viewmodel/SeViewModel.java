package toric.sportsequipment.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import toric.sportsequipment.dao.*;
import toric.sportsequipment.model.SportsEquipment;

public class SeViewModel extends AndroidViewModel {

    SeDAO seDAO;

    private SportsEquipment sportsEquipment;

    public SportsEquipment getSportsEquipment() {
        return sportsEquipment;
    }

    public void setSportsEquipment(SportsEquipment sportsEquipment) {
        this.sportsEquipment = sportsEquipment;
    }

    private LiveData<List<SportsEquipment>> itemList;

    public SeViewModel(Application application) {
        super(application);
        seDAO = SeDataBase.getDataBase(application.getApplicationContext()).seDAO();

    }

    public LiveData<List<SportsEquipment>> takeSportsEquipment() {
        itemList = seDAO.takeSportsEquipment();
        return itemList;
    }

    public void addNewItem() {

        seDAO.addNewItem(sportsEquipment);
    }

    public void updateItem() {

        seDAO.updateItem(sportsEquipment);
    }

    public void deleteItem() {

        seDAO.deleteItem(sportsEquipment);
    }

}