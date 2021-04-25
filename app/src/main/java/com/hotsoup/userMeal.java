package com.hotsoup;

import java.io.Serializable;

public class userMeal implements Serializable { //Object for users meal contains the nutritional data of a food item
    String foodname;
    double portionsize;
    double fats;
    double protein;
    double carb;
    double kcal;
    double alcohol;
    double fiber;
    double sugar;
    //name, energy, portionsize, protein, carbs, fats, alcohol, fiber, sugar
    public userMeal(String n, double e, double ps, double p, double c, double fa, double a, double fib, double s){
        this.foodname = n;
        this.kcal = e;
        this.portionsize = ps;
        this.protein = p;
        this.carb = c;
        this.fats = fa;
        this.alcohol = a;
        this.fiber = fib;
        this.sugar = s;
    }



    public String getFoodname(){
        return foodname;
    }
    public double getEnergy(){
        return kcal;
    }
    public double getPortionsize(){
        return portionsize;
    }
    public double getProtein(){
        return protein;
    }
    public double getCarb(){
        return carb;
    }
    public double getFats(){
        return fats;
    }
    public double getAlcohol(){
        if(alcohol == 0){
            return 0.0;
        }else{return alcohol;}
    }
    public double getFiber(){
        return fiber;
    }
    public double getSugar(){
        return sugar;
    }

}