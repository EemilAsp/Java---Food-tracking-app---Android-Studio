package com.example.datahaku;

public class userMeal {
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
        foodname = n;
        kcal = e;
        portionsize = ps;
        protein = p;
        carb = c;
        fats = fa;
        alcohol = a;
        fiber = fib;
        sugar = s;
    }
}