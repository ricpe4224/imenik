package mperic.unizd.imenik106;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mperic.unizd.imenik106.domain.PersonModel;
import mperic.unizd.imenik106.listadapter.PersonListAdapter;
import mperic.unizd.imenik106.viewmodel.PersonViewModel;

public class FirstFragment extends Fragment implements PersonListAdapter.OnPersonItemClickListener {
    Button btncall;
    private PersonViewModel personViewModel;
    private ListView listView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        personViewModel = ((MainActivity) getActivity()).getPersonViewModel();
        return inflater.inflate(R.layout.fragment_first, container, false);

    }

    @Override
    public void onItemClick(PersonModel personModel) {
        ((MainActivity) getActivity()).setNewUser(false);
        personViewModel.setPersonModel(personModel);
        goToUserScreen();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PersonListAdapter personList = new PersonListAdapter(this.getContext(), personViewModel.getImenik(), this);
        listView = (ListView) view.findViewById(R.id.personList);
        listView.setAdapter(personList);



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                ((MainActivity) getActivity()).setNewUser(false);
//                personViewModel.setPersonModel(personList.getItem(position));
//                goToUserScreen();
//            }
//        });




















////////////////////////////////////////
        FloatingActionButton rfab = view.findViewById(R.id.fAdd);
        rfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setNewUser(true);
                personViewModel.setPersonModel(new PersonModel());
                goToUserScreen();
            }
        });

    }

    private void goToUserScreen() {
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
    }
}
