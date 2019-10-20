package com.example.userinterface;

abstract class Tower {
    private int cost;
    private int firingFrequency;
    private int range;
    private Ammunition ammo;

    Tower(){
    }

    abstract Ammunition attack();


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
