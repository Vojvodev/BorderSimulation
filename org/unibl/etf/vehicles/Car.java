package org.unibl.etf.vehicles;


import org.unibl.etf.BorderSimulation;
import org.unibl.etf.passengers.Passenger;

import java.util.HashSet;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Iterator;


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


    @Override
    public void run(){
        int changed = 1;
        boolean carRejected = false;


        synchronized(stackLock){
            while(this.equals(BorderSimulation.vehicleStack.peek()) == false){      // Only the first in line looks to get to the terminals
                try {
                    stackLock.wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        

        synchronized(this){
            checkTerminals();
            while(p1 == "F" && p2 == "F"){
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }   
        }
        if(p1 == "T" || p2 == "T"){
            // Change back to "F" -busy
            if(p1 == "T"){
                changeTerminal("p1");
                changed = 1;
            }
            else if(p1 == "F" && p2 == "T"){
                changeTerminal("p2");
                changed = 2;
            }

            synchronized(stackLock){
                BorderSimulation.vehicleStack.pop();
                stackLock.notifyAll();        // Condition changed, now someone else is the first in line
            }

            // Police terminal   
            int numOfBadPassengers = 0;
            Iterator<Passenger> iterator = passengers.iterator();
            while(iterator.hasNext()){
                Passenger p = iterator.next();
                try {
                    sleep(500);
                } catch (Exception e) {
                    System.out.println(e);
                }

                if(p.getIdentification().isFakeId()){
                    numOfBadPassengers++;
                    
                    // Passenger added to the naughty list
                    badPassengers.add(p);


                    if(p.isDriver()){                       
                        // Noted that the car has been rejected
                        carRejected = true;
                          
                    }
                    
                    // passengers.remove(p);
                }
            }  
            
            // Serialized data about every illegal passenger
            serializeBadPassengers();
            
            // Only the info that the car has been rejected from the border
            if(carRejected) 
                createPoliceEvidentationCar();

            // How many passengers are thrown out
            if(numOfBadPassengers > 0){
                createPoliceEvidentationPassengers(numOfBadPassengers);
            }
        

            // Change back to "T" -free
            if(changed == 1){
                changeTerminal("p1");
            }
            else if(changed == 2){
                changeTerminal("p2");
            }

            synchronized(this){
                try {
                    notifyAll();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        }
        
        if(carRejected){
            System.out.println("Vehicle [" + this + "] returned from the border [POLICE]!");
            return;
        }
            
        // Then they go to the border crossing
        

        checkTerminals();
        if(c1 == "F"){
            synchronized(this){
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        else if(c1 == "T"){
            // Change back to "F" -busy
            changeTerminal("c1");
            // notifyAll();                        // Notify when changed to F ???


            // Border logic
            try{
                sleep(2000);
            }
            catch(Exception e){
                System.out.println(e);
            }


            // Change back to "T" -free
            changeTerminal("c1");

            synchronized(this){
                try {
                    notifyAll();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }  

        }

        System.out.println("Vehicle [" + this + "] has passed the border!");

    }


 

    // Serializes that the car has been rejected from the border
    synchronized private void createPoliceEvidentationCar(){
        boolean append = (new File(POLICE_EVIDENTATION)).exists();
        try {
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
            writer1.write("SVI;AUTOMOBIL;" + this.id + "\n");
            writer1.close();
        } catch (Exception e) {
            System.out.println(e);
        } 
    }


    // Serializes how many passengers are thrown out
    synchronized private void createPoliceEvidentationPassengers(int numOfBadPassengers){
        boolean append = (new File(POLICE_EVIDENTATION)).exists();
        try {
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
            writer1.write("PUTNIK;" + numOfBadPassengers + ";AUTOMOBIL;" + this.id + "\n");
            writer1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public String toString(){
        return "Car " + "(id: " + id + ") with " + numOfPeople + " passengers.";
    }

}