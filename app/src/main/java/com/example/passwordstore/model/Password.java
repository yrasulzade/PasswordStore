package com.example.passwordstore.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "password_table")
public class Password {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    @ColumnInfo(name = "password_column")
    private String password;

    @ColumnInfo(name = "title_column")
    private String title;

    @ColumnInfo(name = "username_column")
    private String username;

    public Password(String password, String title, String username) {
        this.password = password;
        this.title = title;
        this.username = username;
    }
}
