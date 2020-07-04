package com.example.passwordstore.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.passwordstore.model.Password;

import java.util.List;

@Dao
public interface PasswordDao {

    @Insert
    void insertPassword(Password password);

    @Query("Delete FROM password_table WHERE id=:id")
    void deleteOnePassword(int id);

//    @Query("Insert INTO password_table WHERE id=:id")
//    void undoDeletedPassword(Password password);

    @Update
    void updatePassword(Password password);

    @Query("DELETE FROM password_table")
    void deletePassword();

    @Query("SELECT * FROM password_table")
    LiveData<List<Password>> allPasswords();


}