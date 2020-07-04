package com.example.passwordstore.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.passwordstore.PasswordRepository;

import java.util.List;

public class PasswordViewModel extends AndroidViewModel {
    private PasswordRepository passwordRepository;
    private LiveData<List<Password>> passwords;


    public PasswordViewModel(@NonNull Application application) {
        super(application);
        passwordRepository = new PasswordRepository(application);
        passwords = passwordRepository.getAllPasswords();
    }

    public LiveData<List<Password>> getPasswords(){
        return passwords;
    }

    public void insert(Password password){
        passwordRepository.insert(password);
    }
    public void deleteItem(int id){
        passwordRepository.deleteOnePassword(id);
    }
    public void updateItem(Password password){passwordRepository.updatePassword(password);}
    public void deleteAll(){
        passwordRepository.deleteAll();
    }


}
