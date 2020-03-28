package toric.sportsequipment.adapter;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.List;
import toric.sportsequipment.R;
import toric.sportsequipment.model.SportsEquipment;
import lombok.NonNull;

public class SeAdapter extends ArrayAdapter<SportsEquipment> implements View.OnTouchListener {

    private List<SportsEquipment> itemList;
    private SeClickListener seClickListener;
    private int resource;
    private Context context;

    public SeAdapter(@NonNull Context context, int resource, SeClickListener seClickListener) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.seClickListener = seClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }

    private static class ViewHolder {

        private TextView id;
        private TextView name;
        private ImageView photo;
        private TextView sportType;
        private TextView description;
      
    }

    @androidx.annotation.NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {

        View view = convertView;
        SportsEquipment sportsEquipment;
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);

                viewHolder.id = view.findViewById(R.id.id);
                viewHolder.name = view.findViewById(R.id.nameType);
                viewHolder.photo = view.findViewById(R.id.photo);
                viewHolder.sportType = view.findViewById(R.id.sport_type);
                viewHolder.description = view.findViewById(R.id.description);

                viewHolder.description.setOnTouchListener(this::onTouch);
                viewHolder.description.setMovementMethod(ScrollingMovementMethod.getInstance());
            } else {
                viewHolder = (ViewHolder) view.getTag();

            }

            sportsEquipment = getItem(position);

            if (sportsEquipment != null) {
                //viewHolder.name.setText(sportsEquipment.getName() + " - " + context.getResources().getStringArray(R.array.type_sport)[sportsEquipment.getType()]);

                viewHolder.id.setText(String.valueOf(sportsEquipment.getId()));
                viewHolder.name.setText(sportsEquipment.getName());
                viewHolder.sportType.setText(context.getResources().getStringArray(R.array.type_sport)[sportsEquipment.getType()]);
                viewHolder.description.setText(sportsEquipment.getDescription());

                viewHolder.description.setOnTouchListener(this::onTouch);
                viewHolder.description.setMovementMethod(ScrollingMovementMethod.getInstance());

                if (sportsEquipment.getPhoto() == null) {
                    Picasso.get().load(R.drawable.sportsequipment).fit().centerCrop().into(viewHolder.photo);
                } else {
                    Picasso.get().load(sportsEquipment.getPhoto()).fit().centerCrop().into(viewHolder.photo);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seClickListener.onItemClick(sportsEquipment);
                }
            });
        }
        return view;
    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Nullable
    @Override
    public SportsEquipment getItem(int position) {
        return itemList.get(position);
    }

    public void setItemList(List<SportsEquipment> itemList) {
        this.itemList = itemList;
    }

    public void refreshItem() {
        notifyDataSetChanged();
    }
}
