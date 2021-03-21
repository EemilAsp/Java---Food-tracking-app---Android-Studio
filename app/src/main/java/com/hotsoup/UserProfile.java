package com.hotsoup;

import java.util.ArrayList;

public class UserProfile implements java.io.Serializable{
    String userName;
    byte[] password;
    int height;
    ArrayList<Integer> weight = null;
    int yearOfBirth;
    String homeCity;
    ArrayList<userFoodDiary> comsumption = null;
    String filepath;
    byte[] salt;

    public UserProfile(String userName, byte[] password, String filepath, byte[] salt){
        this.userName = userName;
        this.filepath = filepath;
        this.password = password;
        this.salt = salt;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getFilepath() {
        return filepath;
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

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public void setComsumption(ArrayList<userFoodDiary> comsumption) {
        this.comsumption = comsumption;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void upodateUserData(){
        //TODO päivittää käyttäjän tiedoston eli tyhjentää sen ja kirjoittaa itsensä sinne
    }
}
