package com.hotsoup;

import java.util.ArrayList;

public class UserProfile implements java.io.Serializable{
    String userName;
    String password;
    int height;
    ArrayList<Integer> weight = null;
    int yearOfBirth;
    String homeCity;
    ArrayList<userFoodDiary> comsumption = null;
    String filepath;


    public void createProfile(String userName,
            String password,
            int height,
            ArrayList<Integer> weight,
            int yearOfBirth,
            String homeCity,
            ArrayList<userFoodDiary> comsumption){
        this.userName = userName;
        this.password = password;
        this.height = height;
        this.yearOfBirth = yearOfBirth;
        this.homeCity = homeCity;
        this.weight = weight;
        this.comsumption = comsumption;

    }

    public ArrayList<userFoodDiary> getComsumption() {
        return comsumption;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
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
