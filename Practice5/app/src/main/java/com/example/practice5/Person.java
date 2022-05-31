package com.example.practice5;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "first_name")
    private String name;

    @NonNull
    @ColumnInfo(name = "last_name")
    private String surname;

    @NonNull
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    public void setId(@NonNull long name) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSurname(@NonNull String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }


}
