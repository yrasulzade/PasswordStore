package com.example.passwordstore;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.passwordstore.data.PasswordDao;
import com.example.passwordstore.data.PasswordRoomDatabase;
import com.example.passwordstore.model.Password;

import java.util.List;

public class PasswordRepository {
    private PasswordDao passwordDao;
    private LiveData<List<Password>> allPasswords;

    public PasswordRepository(Application application) {
        PasswordRoomDatabase database = PasswordRoomDatabase.getDatabase(application);
        passwordDao = database.passwordDao();
        allPasswords = passwordDao.allPasswords();
    }

    public LiveData<List<Password>> getAllPasswords() {
        return allPasswords;
    }

    public void insert(Password password) {
        new insertAsyncTask(passwordDao).execute(password);
    }

    public void deleteAll() {
        new deleteAsyncTask(passwordDao).execute();
    }

    public void deleteOnePassword(int id) {
        new deleteOneAsyncTask(passwordDao).execute(id);
    }

    public void updatePassword(Password pass) {
        new updateAsyncTask(passwordDao).execute(pass);
    }

    private class insertAsyncTask extends AsyncTask<Password, Void, Void> {
        private PasswordDao asyncDao;

        public insertAsyncTask(PasswordDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
            asyncDao.insertPassword(passwords[0]);
            return null;
        }
    }

    private class updateAsyncTask extends AsyncTask<Password, Void, Void> {
        private PasswordDao asyncDao;

        public updateAsyncTask(PasswordDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
            asyncDao.updatePassword(passwords[0]);
            return null;
        }
    }

    private class deleteAsyncTask extends AsyncTask<Password, Void, Void> {
        private PasswordDao asyncDao;

        public deleteAsyncTask(PasswordDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Password... passwords) {
//            asyncDao.insertPassword(passwords[0]);
            asyncDao.deletePassword();
            return null;
        }
    }

    private class deleteOneAsyncTask extends AsyncTask<Integer, Void, Void> {
        private PasswordDao asyncDao;

        public deleteOneAsyncTask(PasswordDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            asyncDao.deleteOnePassword(integers[0]);
            return null;
        }
    }
}