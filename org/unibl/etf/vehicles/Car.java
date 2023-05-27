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
        // Kreirati tri objekta pa ih zakljucati     KAKO?

        // Police terminal
        synchronized(){
            int numOfBadPassengers = 0;
            for(Passenger p : passengers){
                sleep(500);
                if(p.getIdentification().isFakeId()){
                    if(p.isDriver()){
                        // TODO: EVIDENTIRA SE DA JE AUTO IZBACENO
                        // interrupt();         // Da ga prekine provjeravati i da ga izbaci iz liste vozila
                    }
                    // TODO: IZBACITI PUTNIKA IZ AUTA
                }
            }

            // TODO : EVIDENTIRATI KOLIKO PUTNIKA JE IZBACENO
                    
        }

        // Then they go to the border crossing
        synchronized(){
            sleep(2000);
        }

    }


    @Override
    public String toString(){
        return "Car " + "(id: " + id + ") with " + numOfPeople + " passengers.";
    }
}