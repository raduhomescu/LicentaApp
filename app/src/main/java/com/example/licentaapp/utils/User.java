package com.example.licentaapp.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class User implements Parcelable {
    private String userId;
    private String fName;
    private String email;
    private String phoneNumber;
    private ArrayList<String> favouritePhones;



    public User() {
    }

    public User(String userId, String fName, String email, String phoneNumber, ArrayList<String> favouritePhones) {
        this.userId = userId;
        this.fName = fName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.favouritePhones = favouritePhones;
    }

    protected User(Parcel in) {
        userId = in.readString();
        fName = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        favouritePhones = new ArrayList<>();
        favouritePhones = in.readArrayList(String.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<String> getFavouritePhones() {
        return favouritePhones;
    }

    public void setFavouritePhones(ArrayList<String> favouritePhones) { this.favouritePhones = favouritePhones; }

    public void addFavoritePhoneId(String phoneId) {
        if (!favouritePhones.contains(phoneId)) {
            favouritePhones.add(phoneId);
        }
    }

    public void removeFavoritePhoneId(String phoneId) {
        favouritePhones.remove(phoneId);
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fName='" + fName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", favouritePhones=" + favouritePhones +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(fName);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeList(favouritePhones);
    }
}