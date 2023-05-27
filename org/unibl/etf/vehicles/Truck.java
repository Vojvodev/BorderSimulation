package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.HashSet;


public class Truck extends Vehicle{
    private static int CAPACITY = 3;
    private int loadCapacity;
    private int realLoad;
    private boolean documentationNeeded;
    

    public Truck(){
        super(CAPACITY);
        
        // Trucks can't transport anyone who isn't a driver - 3 drivers
        for(int i = 1; i <= numOfPeople; i++){
            Passenger p = new Passenger("N.N. driver " + i, true);
                this.passengers.add(p);
        }


        Random rand = new Random();
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


    public void run(){
        
        // Police terminal
        synchronized(){
            int numOfBadDrivers = 0;
            int numOfBadPassengers = 0;
            Passenger temp1 = new Passenger("temp1", true);
            Passenger temp2 = new Passenger("temp2", true);

            for(Passenger p : passengers){
                sleep(500);
                if(p.getIdentification().isFakeId()){
                    // TODO: IZBACITI GA IZ KAMIONA

                    if(numOfBadDrivers == 3){
                        // TODO: EVIDENTIRA SE DA JE IZBACEN KAMION CIJELI
                        // interrupt();  ili   exception       // Da ga prekine provjeravati i da ga izbaci iz liste vozila
                    }
                }
            }

            // TODO : EVIDENCIJA JE BROJ IZBACENIH PUTNIKA - zapamcena su im imena


        }

        // Then they go to the border crossing
        synchronized(){
            sleep(500);

            if(this.isDocumentationNeeded()){
                if(realLoad > loadCapacity){
                    // TODO: IZBACITI KAMION IZ LISTE
                    // TODO: NAPRAVITI EVIDENCIJU   TXT FAJL
                }    
            }
            
        }
    }


    public boolean isDocumentationNeeded(){
        return documentationNeeded;
    }


    @Override
    public String toString(){
        if(documentationNeeded == true)
            return "Truck " + "(Id: " + id + "); mass: " + realLoad + "kg -with documentation)";
        else
            return "Truck " + "(Id: " + id + "); mass: " + realLoad + "kg -without documentation)";
    }
    
}