package com.example.passwordstore.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.passwordstore.BR;

@Entity(tableName = "password_table")
public class Password extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    public int getId() {
        return id;
    }
    @Bindable
    public String getPassword() {
        return password;
    }
    @Bindable
    public String getTitle() {
        return title;
    }
    @Bindable
    public String getUsername() {
        return username;
    }

    @ColumnInfo(name = "password_column")
    private String password;

    @ColumnInfo(name = "title_column")
    private String title;

    @ColumnInfo(name = "username_column")
    private String username;

    public Password() {
    }

    public Password(String password, String title, String username) {
        this.password = password;
        this.title = title;
        this.username = username;
    }
}
