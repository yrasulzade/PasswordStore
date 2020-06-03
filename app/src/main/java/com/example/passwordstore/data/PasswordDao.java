package com.example.passwordstore.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.passwordstore.model.Password;

import java.util.List;

@Dao
public interface PasswordDao {

    @Insert
    void insertPassword(Password password);

    @Query("Delete FROM password_table WHERE id=:id")
    int deleteOnePassword(int id);

    @Query("UPDATE password_table SET password_column =:password WHERE id=:id")
    int updatePassword(int id, String password);

    @Query("DELETE FROM password_table")
    void deletePassword();

    @Query("SELECT * FROM password_table")
    LiveData<List<Password>> allPasswords();


}