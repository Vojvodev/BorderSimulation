package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.HashSet;


public class Car extends Vehicle{
    private static final int CAPACITY = 5;
    

    
    public Car(){
        super(CAPACITY);

        // Each car has only 1 driver (car's owner)
        Passenger p = new Passenger("N.N. driver", true);
        this.passengers.add(p);

        for(int i = 0; i < numOfPeople - 1; i++){
            Passenger p1 = new Passenger("N.N.", false);
            this.passengers.add(p1);
        }
    }


    public void run(){
        // Kreirati tri objekta pa ih zakljucati i provjeravati kakvo je vozilo?    KAKO?
    }


    @Override
    public String toString(){
        return "Car with " + numOfPeople + " passengers.";
    }
}