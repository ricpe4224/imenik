package mperic.unizd.imenik106;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.R.layout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mperic.unizd.imenik106.domain.PersonModel;
import mperic.unizd.imenik106.domain.Sex;
import mperic.unizd.imenik106.viewmodel.PersonViewModel;

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;

public class SecondFragment extends Fragment {
    private final static int PICK_IMAGE = 1312;
    private final static int PERMISSION_REQUEST = 1321;
    private PersonModel personModel;
    private PersonViewModel personViewModel;

    EditText name;
    EditText email;
    Spinner spinnerSex;
    DatePicker datePicker;
    Button btnBack;
    Button btnDel;
    Button btnEdit;
    Button btnSave;
    Button btnPhoto;
    EditText broj;
    ImageView profileImage;
    Calendar calendar;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        personViewModel = ((MainActivity) getActivity()).getPersonViewModel();
        personModel = personViewModel.getPersonModel();

        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        broj= view.findViewById(R.id.broj);
        spinnerSex = view.findViewById(R.id.spinnerSex);
        datePicker = view.findViewById(R.id.datePicker);
        btnPhoto = view.findViewById(R.id.btnPhoto);
        btnBack = view.findViewById(R.id.btnBack);
        btnSave = view.findViewById(R.id.btnSave);
        btnDel = view.findViewById(R.id.btnDel);
        btnEdit= view.findViewById(R.id.btnEdit);
        profileImage = view.findViewById(R.id.imageCud);
        calendar = Calendar.getInstance(Locale.getDefault());

        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("male");
        list.add("female");
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapter);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year,monthOfYear,dayOfMonth);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //personModel.setDateOfBirth(datePicker.);
                personModel.setEmail(email.getText().toString());
                personModel.setName(name.getText().toString());
                 personModel.setPhoneNumber(broj.getText().toString());
                 personModel.setSex(spinnerSex.getSelectedItem().toString());
                personModel.setDateOfBirth(calendar.getTime());
                personViewModel.addPerson();
                returnToListView();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToListView();
            }
        });

       btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personModel.setEmail(email.getText().toString());
                personModel.setName(name.getText().toString());
                personModel.setPhoneNumber(broj.getText().toString());
                personModel.setSex(spinnerSex.getSelectedItem().toString());
                personModel.setDateOfBirth(calendar.getTime());
                personViewModel.editPerson();
                returnToListView();
            }
        });
       btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personViewModel.deletePerson();
                returnToListView();
            }
        });

       btnPhoto.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                  requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
              }else {
                  Intent intent = new Intent();
                  intent.setType("image/*");
                  intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                  startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
              }
           }
       });




        if( ((MainActivity) getActivity()).isNewUser()){
            this.btnDel.setVisibility(View.GONE);
            this.btnEdit.setVisibility(View.GONE);
        }else {
            this.btnSave.setVisibility(View.GONE);
            name.setText(personModel.getName());
            email.setText(personModel.getEmail());
            broj.setText(personModel.getPhoneNumber());
            if (personModel.getSex() != null ){
                if (personModel.getSex().equals("male"))
                    spinnerSex.setSelection(0);
                else
                    spinnerSex.setSelection(1);

            }
            if (personModel.getDateOfBirth() != null){
                calendar.setTime(personModel.getDateOfBirth());
                datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
            if (personModel.getPathImage() != null){
                try{
                    Uri imageURI = Uri.parse(personModel.getPathImage());
                    if (imageURI != null){
                        profileImage.setImageURI(imageURI);
                    }
                }catch (Throwable throwable){

                }
            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null){
            Uri uri = data.getData();
            if (uri != null) {
                personModel.setPathImage(uri.toString());
                profileImage.setImageURI(uri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

    private void returnToListView(){
        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

}
