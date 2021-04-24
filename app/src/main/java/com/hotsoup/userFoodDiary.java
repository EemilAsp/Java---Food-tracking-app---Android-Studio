package com.hotsoup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class userFoodDiary implements Serializable { // used to store users meals, hashmap uses date key, which opens array for the dates meals
    private static userFoodDiary ufd = new userFoodDiary();
    LoadProfile lp = LoadProfile.getInstance();
    UserProfile user = lp.getUser();
    String date;
    userMeal meal;
    HashMap<String, ArrayList<userMeal>> daysMeals = user.getDaysMeals(); //TODO this must be saved to users data!!!


    public void addMeals(String dt, String n, double e, double ps, double p, double c, double fa, double a, double fib, double s){
        date = dt;
        if(daysMeals.containsKey(date)){ // Testing if theres already array with date key
            meal = new userMeal(n,e,ps,p,c,fa,a,fib,s); // if there is adding a new meal made to the date
            daysMeals.get(date).add(meal);
        }else{ // else creating a new array, and adding the meal into the array, and putting it to hashmap
            ArrayList<userMeal> mealArray = new ArrayList<userMeal>();
            meal = new userMeal(n,e,ps,p,c,fa,a,fib,s);
            mealArray.add(meal);
            daysMeals.put(dt, mealArray);
        }
    }

    public ArrayList<userMeal> getArray(String date){
        if(daysMeals.containsKey(date)){
            return daysMeals.get(date);
        }else{
            return null;
        }}

    public HashMap<String, ArrayList<userMeal>> getDaysMeals(){
        return daysMeals;
    }

    public static userFoodDiary getInstance(){return ufd;}
}

