package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.TreeSet;


public class Car extends Vehicle{
    private static final int capacity = 5;
    

    
    public Car(){
        super();

        Random rand = new Random();
        int numOfPeople = rand.nextInt(1, capacity + 1);


        // Each car has only 1 driver (car's owner)
        Passenger p = new Passenger("N.N. driver", true);
        this.passengers.add(p);

        for(int i = 0; i < numOfPeople - 1; i++){
            Passenger p1 = new Passenger("N.N.", false);
            this.passengers.add(p1);
        }
    }

}