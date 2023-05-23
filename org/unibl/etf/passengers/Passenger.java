package org.unibl.etf.passengers;


import org.unibl.etf.identifications.PassengerId;

import java.util.Random;


public class Passenger{
    private PassengerId identification;
    private boolean hasLuggage;
    private boolean forbiddenLuggage;
    private boolean isDriver;


    public Passenger(String name, boolean isDriver){
        this.identification = new PassengerId(name);
        this.isDriver = isDriver;

        Random rand = new Random();

        // Luggage
        int x = rand.nextInt(10);  // 0,1,2,3,4,5,6,7,8,9 - 70% chance
        if(x < 7){
            this.hasLuggage = true;

            // Forbidden luggage
            int y = rand.nextInt(10); // 0,1,2,3,4,5,6,7,8,9 - 10% chance
            if(y == 0)
                forbiddenLuggage = true;
            else 
                forbiddenLuggage = false;
        }
        else
            this.hasLuggage = false;

    }

    // Can add some aditional atributes like if he is handicapped or something
}