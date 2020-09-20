package mperic.unizd.imenik106.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import mperic.unizd.imenik106.domain.PersonModel;
import mperic.unizd.imenik106.repository.ImenikDao;
import mperic.unizd.imenik106.repository.ImenikDaoImpl;


public class PersonViewModel extends AndroidViewModel {

    private ImenikDao imenikDao;
    private PersonModel personModel;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        imenikDao = ImenikDaoImpl.getInstance(application.getApplicationContext()).imenikDao();
    }

    public List<PersonModel> getImenik() {
        return imenikDao.getAll();
    }

    public void addPerson() {
        imenikDao.savePersonToImenik(personModel);
    }

    public void editPerson() {
        imenikDao.editPerson(personModel);
    }

    public void deletePerson() {
        imenikDao.deletePersonImenik(personModel);
    }

    public PersonModel getPersonModel() {
        return personModel;
    }

    public void setPersonModel(PersonModel personModel) {
        this.personModel = personModel;
    }
}
