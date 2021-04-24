package com.hotsoup;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class UserProfile implements java.io.Serializable{
    String userName;
    byte[] password;
    double height = 0;
    ArrayList<Double> weight = new ArrayList<>();
    ArrayList<Double> carbonfootprint = new ArrayList<>();
    ArrayList<Double> travelcarbonfootprint = new ArrayList<>();
    HashMap<String, ArrayList<userMeal>> daysMeals = new HashMap<>();
    Calendar yearOfBirth = null;
    String homeCity ="";

    ArrayList<userFoodDiary> comsumption = new ArrayList<>();
    boolean rememberMe =false;


    public UserProfile(String userName, byte[] password, byte[] salt){
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        lastActivity = MainScreenActivity.class.getName();
        Calendar c = Calendar.getInstance();
        yearOfBirth = c;
    }

    public HashMap<String, ArrayList<userMeal>> getDaysMeals() {
        return daysMeals;
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

    public void setDaysMeals(HashMap<String, ArrayList<userMeal>> daysMeals){
        this.daysMeals = daysMeals;
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


    public void setHeight(double height) {
        this.height = height;
    }

    public void addCarbonfootprint(Double carbonfootprint) {
        this.carbonfootprint.add(carbonfootprint);
    }

    public void addtravelCarbonfootprint(Double travelcarbonfootprint) {
        this.travelcarbonfootprint.add(travelcarbonfootprint);
    }

    public void addWeight(Double weight) {
        this.weight.add(weight);
    }

    public void setWeight(ArrayList<Double> weightlist){this.weight = weightlist;}

    public void setCarbonfootprint(ArrayList<Double> carbonfootprintlist){this.carbonfootprint = carbonfootprintlist;}

    public void setTravelCarbonfootprint(ArrayList<Double> travelcarbonfootprintlist){this.travelcarbonfootprint = travelcarbonfootprintlist;}

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

    public double getHeight() {
        return height;
    }

    public ArrayList<Double> getWeight() {
        return weight;
    }

    public ArrayList<Double> getCarbonfootprint() {return carbonfootprint;}

    public ArrayList<Double> getTravelcarbonfootprint() {return travelcarbonfootprint;}


    public String getHomeCity() {
        return homeCity;
    }



}
