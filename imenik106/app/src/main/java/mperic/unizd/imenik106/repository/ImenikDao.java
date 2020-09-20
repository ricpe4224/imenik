package mperic.unizd.imenik106.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import mperic.unizd.imenik106.domain.PersonModel;

@Dao
public interface ImenikDao {

    @Query("SELECT * FROM imenik")
    List<PersonModel> getAll();

/*
    @Query("SELECT * FROM imenik WHERE id=:userId")
    List<PersonModel> loadAllByIds(int userId);
*/

    @Insert
    void savePersonToImenik (PersonModel personModel);

    @Delete
    void deletePersonImenik (PersonModel personModel);

    @Update
    void editPerson(PersonModel personModel);


}
