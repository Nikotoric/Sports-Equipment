package toric.sportsequipment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toric.sportsequipment.R;
import toric.sportsequipment.adapter.SeAdapter;
import toric.sportsequipment.adapter.SeClickListener;
import toric.sportsequipment.model.SportsEquipment;
import toric.sportsequipment.viewmodel.SeViewModel;
import toric.sportsequipment.view.MainActivity;

public class ReadFragment extends Fragment {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private SeViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getViewModel();

        defineList();
        defineSwipe();
        refeshData();

        return view;
    }

    private void refeshData(){
        model.takeSportsEquipment().observe(this, new Observer<List<SportsEquipment>>() {
            @Override
            public void onChanged(@Nullable List<SportsEquipment> itemList) {
                swipeRefreshLayout.setRefreshing(false);
                ((SeAdapter)listView.getAdapter()).setItemList(itemList);
                ((SeAdapter) listView.getAdapter()).refreshItem();

            }
        });
    }
    
	private void defineSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refeshData();
            }
        });

    }

    private void defineList() {

        listView.setAdapter( new SeAdapter(getActivity(), R.layout.red_liste, new SeClickListener() {
            @Override
            public void onItemClick(SportsEquipment sportsEquipment) {
                model.setSportsEquipment(sportsEquipment);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void addNew(){
        model.setSportsEquipment(new SportsEquipment());
        ((MainActivity)getActivity()).cud();
    }

}