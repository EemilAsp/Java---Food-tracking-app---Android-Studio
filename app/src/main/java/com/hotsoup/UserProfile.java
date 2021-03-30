package com.hotsoup;


import java.util.ArrayList;
import java.util.Calendar;

public class UserProfile implements java.io.Serializable{
    String userName;
    byte[] password;
    int height;
    ArrayList<Integer> weight = null;
    Calendar yearOfBirth;
    String homeCity;
    ArrayList<userFoodDiary> comsumption = null;
    boolean rememberMe =false;


    public UserProfile(String userName, byte[] password, byte[] salt){
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        lastActivity = MainScreenActivity.class.getName();
        Calendar c = Calendar.getInstance();
        yearOfBirth = c;
    }




    public Calendar getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Calendar yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    String lastActivity;
    byte[] salt;

    public String getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity = lastActivity;
    }



    public void setPassword(byte[] password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(ArrayList<Integer> weight) {
        this.weight = weight;
    }



    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public void setComsumption(ArrayList<userFoodDiary> comsumption) {
        this.comsumption = comsumption;
    }

    public ArrayList<userFoodDiary> getComsumption() {
        return comsumption;
    }

    public String getUserName() {
        return userName;
    }

    public byte[] getPassword() {
        return password;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Integer> getWeight() {
        return weight;
    }



    public String getHomeCity() {
        return homeCity;
    }



}
