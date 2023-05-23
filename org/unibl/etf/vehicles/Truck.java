package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.TreeSet;


public class Truck extends Vehicle{
    private static int capacity = 3;
    private int loadCapacity;
    private int realLoad;
    private boolean documentationNeeded;
    

    public Truck(){
        Random rand = new Random();
        int numOfPeople = rand.nextInt(1, capacity + 1);

        // Trucks can't transport anyone who isn't a driver
        for(int i = 1; i <= numOfPeople; i++){
            Passenger p = new Passenger("N.N. driver " + i, true);
                this.passengers.add(p);
        }



        // Documentation
        int x = rand.nextInt(2);  //  0 or 1 - 50% chance
        if(x == 0)
            this.documentationNeeded = true;
        else 
            this.documentationNeeded = false;
        

        // Load 
        this.loadCapacity = rand.nextInt(2, 11) * 1000;  // Random capacity between 2_000kg and 10_000kg
        this.realLoad = this.loadCapacity;

        int y = rand.nextInt(5);  // 0, 1, 2, 3, 4 - 20% chance

        if(y == 0){
            int weightCap = (int)(0.3 * this.loadCapacity);
            int overload = rand.nextInt(1, weightCap);

            this.realLoad += overload;
        }
    }
    
}