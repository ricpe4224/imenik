package mperic.unizd.imenik106.listadapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import mperic.unizd.imenik106.MainActivity;
import mperic.unizd.imenik106.R;
import mperic.unizd.imenik106.domain.PersonModel;

public class PersonListAdapter extends ArrayAdapter<PersonModel> {
    public interface OnPersonItemClickListener{
        void onItemClick(PersonModel personModel);
    }
    private OnPersonItemClickListener listener;
    public PersonListAdapter(Context context, List<PersonModel> personModelList, OnPersonItemClickListener listener) {
        super(context, 0, personModelList);
        this.listener = listener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PersonModel personModel = (PersonModel) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.person_list, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.lblName);

        TextView email = (TextView) convertView.findViewById(R.id.lblEmail);

        TextView broj = (TextView) convertView.findViewById(R.id.lblBroj);

        TextView sex = (TextView) convertView.findViewById(R.id.lblSex);

        TextView dateOfBirth = (TextView) convertView.findViewById(R.id.lblDateOfBirth);

        ImageView dial = (ImageView) convertView.findViewById(R.id.btnCall);

        ImageView btnEmail = (ImageView) convertView.findViewById(R.id.btnEamil);

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personModel.getPhoneNumber() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", personModel.getPhoneNumber(),null));
                    getContext().startActivity(intent);
                }
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personModel.getEmail() != null){
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{personModel.getEmail()});
                    try {
                        getContext().startActivity(Intent.createChooser(intent, "Odaberi aplikaciju"));
                    }catch (Throwable throwable){

                    }
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(personModel);
            }
        });


        name.setText(personModel.getName());

        email.setText(personModel.getEmail());

        broj.setText(personModel.getPhoneNumber());

        sex.setText(personModel.getSex());

        if (personModel.getPathImage() != null && ((MainActivity) getContext()).isImagePermissionGranted()){
            try{
                Uri imageURI = Uri.parse(personModel.getPathImage());
                if (imageURI != null){
                    ImageView profile = (ImageView) convertView.findViewById(R.id.image);
                    profile.setImageURI(imageURI);
                }
            }catch (Throwable throwable){
                throwable.printStackTrace();
            }
        }



        if(personModel.getDateOfBirth() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            dateOfBirth.setText(simpleDateFormat.format(personModel.getDateOfBirth()));
        }

        /*Button btnSave= (Button) convertView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(getContext(),"Hello Javatpoint",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        });*/

        return convertView;
    }
}
