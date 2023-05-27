package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.HashSet;
import java.lang.Thread;

public class Vehicle extends Thread{
    private static int counter = 0;

    protected HashSet<Passenger> passengers;
    protected int numOfPeople;
    protected int id;


    public Vehicle(int capacity){
        Random rand = new Random();

        this.numOfPeople = rand.nextInt(1, capacity + 1);
        this.passengers = new HashSet<Passenger>();

        this.id = counter++;
    }
}