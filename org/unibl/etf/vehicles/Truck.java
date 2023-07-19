package org.unibl.etf.vehicles;

import org.unibl.etf.BorderSimulation;
import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.HashSet;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;



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


    @Override
    public void run(){
        boolean truckRejected = false;

        synchronized(stackLock){
            while(this.equals(BorderSimulation.vehicleStack.peek()) == false){      // Only the first in line looks to get to the terminals
                try {
                    stackLock.wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }


        checkTerminals();
        if(p3 == "F"){
            synchronized(this){
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        else if(p3 == "T"){
            // Change back to "F" -busy
            changeTerminal("p3");


            synchronized(stackLock){
                BorderSimulation.vehicleStack.pop();
                stackLock.notifyAll();        // Condition changed, now someone else is the first in line
            }
            
            // Police terminal
            
            int numOfBadDrivers = 0;
            int numOfBadPassengers = 0;
    
            for(Passenger p : passengers){
                try {
                    sleep(500);
                } catch (Exception e) {
                    System.out.println(e);
                }

                if(p.getIdentification().isFakeId()){
                    // Passenger added to the naughty list
                    badPassengers.add(p);
                    passengers.remove(p);

                    if(p.isDriver()){
                        numOfBadDrivers++;
                    }
    
                    if(numOfBadDrivers == 3){
                        // Noted that the truck has been rejected
                        truckRejected = true;
                    }

                }

            }

            // Serialized data about every illegal passenger
            serializeBadPassengers();

            // Only the info that the bus has been rejected from the border
            if(truckRejected)
                createEvidentationTruck(POLICE_EVIDENTATION);

            // How many passengers are thrown out
            createEvidentationPassengers(POLICE_EVIDENTATION, numOfBadPassengers);
    

            // Change p3 back to "T"
            changeTerminal("p3");
            notifyAll();

        }
    
        if(truckRejected)
            return;


        // Then they go to the border crossing


        checkTerminals();
        if(c2 == "F"){
            synchronized(this){
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        else if(c2 == "T"){
            // Change back to "F" -busy
            changeTerminal("c2");
            notifyAll();                    // Notify nakon promjene u F ???

            try {
                sleep(500);
            } catch (Exception e) {
                System.out.println(e);
            }

            if(this.isDocumentationNeeded()){
                if(realLoad > loadCapacity){
                    // Noted that the truck has been rejected
                    truckRejected = true;
                    createEvidentationTruck(BORDER_EVIDENTATION);
                }    
            }
            

            // Change c2 back to "T"
            changeTerminal("c2");
            notifyAll();

        }

    }



    public boolean isDocumentationNeeded(){
        return documentationNeeded;
    }

    // Notes that the bus has been rejected
    synchronized private void createEvidentationTruck(String FILE){
        boolean append = (new File(FILE)).exists();
        try{
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(FILE, append));
            writer1.write("SVI;KAMION;" + this.id);
            writer1.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    // Serializes how many passengers are thrown out
    synchronized private void createEvidentationPassengers(String FILE, int numOfBadPassengers){
        boolean append = (new File(FILE)).exists();
            try{
                BufferedWriter writer1 = new BufferedWriter(new FileWriter(FILE, append));
                writer1.write("PUTNIK;" + numOfBadPassengers + ";KAMION;" + this.id);
                writer1.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
    }


    @Override
    public String toString(){
        if(documentationNeeded == true)
            return "Truck " + "(Id: " + id + "); mass: " + realLoad + "kg -with documentation)";
        else
            return "Truck " + "(Id: " + id + "); mass: " + realLoad + "kg -without documentation)";
    }
    
}