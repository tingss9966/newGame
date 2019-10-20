package com.example.userinterface;

abstract class Tower {
    private int cost;
    private int firingFrequency;
    private int range;
    private Ammunition ammo;
    private int x;
    private int y;

    Tower(){
    }

    abstract Ammunition attack();

    int getX(){return x;}
    int getY(){return y;}
    int getCost() {
        return cost;
    }

    int getFiringFrequency() {
        return firingFrequency;
    }

    int getRange() {
        return range;
    }

    Ammunition getAmmo() {
        return ammo;
    }

}
