package toric.sportsequipment.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import toric.sportsequipment.R;
import toric.sportsequipment.viewmodel.SeViewModel;
import toric.sportsequipment.view.MainActivity;

public class CUDFragment extends Fragment {

    static final int PHOTONUM = 1;

    private String imagePath;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.type_sport)
    Spinner sportType;
    @BindView(R.id.desc)
    EditText description;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.addNew)
    Button newItem;
    @BindView(R.id.takePhoto)
    Button takePhoto;
    @BindView(R.id.updateItem)
    Button updateItemBtn;
    @BindView(R.id.deleteItem)
    Button deleteItemBtn;

    SeViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_cud, container, false);
        ButterKnife.bind(this, view);

        model = ((MainActivity) getActivity()).getViewModel();

        if (model.getSportsEquipment().getId() == 0) {
            defineNewItem();
            return view;
        }

        defineUpdateDeleteItem();

        return view;

    }

    private void defineUpdateDeleteItem() {
        newItem.setVisibility(View.GONE);
        sportType.setSelection(model.getSportsEquipment().getType());
        name.setText(model.getSportsEquipment().getName());
        description.setText(model.getSportsEquipment().getDescription());
        Picasso.get().load(model.getSportsEquipment().getPhoto()).error(R.drawable.sportsequipment).into(photo);


        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        updateItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem();
            }
        });

        deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }

    private void updateItem() {
        model.getSportsEquipment().setName(name.getText().toString());
        //model.getSportsEquipment().setPhoto(model.getSportsEquipment().getPhoto());
        model.getSportsEquipment().setType(sportType.getSelectedItemPosition());
        model.getSportsEquipment().setDescription(description.getText().toString());
        model.updateItem();
        back();
    }

    private void defineNewItem() {
        updateItemBtn.setVisibility(View.GONE);
        deleteItemBtn.setVisibility(View.GONE);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem();
            }
        });
    }

    private void addNewItem() {
        model.getSportsEquipment().setName(name.getText().toString());
        //model.getSportsEquipment().setPhoto("file://" + imagePath);
        model.getSportsEquipment().setType(sportType.getSelectedItemPosition());
        model.getSportsEquipment().setDescription(description.getText().toString());
        model.addNewItem();
        back();
    }

    private void deleteItem() {
        model.getSportsEquipment().setName(name.getText().toString());
        //model.getSportsEquipment().setPhoto("file://" + imagePath);
        model.getSportsEquipment().setType(sportType.getSelectedItemPosition());
        model.getSportsEquipment().setDescription(description.getText().toString());
        model.deleteItem();
        back();
    }

    @OnClick(R.id.back)
    public void back() {
        ((MainActivity) getActivity()).read();
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;

        }

        File photo = null;
        try {
            photo = createImageData();
        } catch (IOException ex) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
        }

        if (photo == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
        }

        Uri photoURI = FileProvider.getUriForFile(getActivity(),"toric.sportsequipment.provider", photo);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, PHOTONUM);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTONUM && resultCode == Activity.RESULT_OK) {

            model.getSportsEquipment().setPhoto("file://" + imagePath);
            model.updateItem();
            Picasso.get().load(model.getSportsEquipment().getPhoto()).into(photo);

        }
    }

    private File createImageData() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String photoName = "SportsEquipment" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                photoName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imagePath = image.getAbsolutePath();
        return image;
    }

}
