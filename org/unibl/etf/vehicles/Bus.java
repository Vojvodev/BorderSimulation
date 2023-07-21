package org.unibl.etf.vehicles;

import org.unibl.etf.BorderSimulation;
import org.unibl.etf.passengers.Passenger;

import java.util.HashSet;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Iterator;


public class Bus extends Vehicle{
    private static final int CAPACITY = 52; 

    private int numOfBadDrivers = 0;
    private boolean busRejected = false;

    public Bus(){
        super(CAPACITY);
        
        // Each bus has at least 1 driver or if there's more than 1 passenger - there are 2 drivers
        if(numOfPeople == 1){                                   // Only 1 in the bus and he is the driver
            Passenger p = new Passenger("N.N. driver", true);
            this.passengers.add(p);
        }
        else if(numOfPeople == 2){                              // Only the 2 drivers
            Passenger p1 = new Passenger("N.N. driver1", true);
            this.passengers.add(p1);

            Passenger p2 = new Passenger("N.N. driver2", true);
            this.passengers.add(p2);
        }
        else{                                                   // 2 drivers and the rest
            Passenger p1 = new Passenger("N.N. driver1", true);
            this.passengers.add(p1);

            Passenger p2 = new Passenger("N.N. driver2", true);
            this.passengers.add(p2);

            for(int i = 0; i < numOfPeople - 2; i++){
                Passenger px = new Passenger("N.N.", false);
                this.passengers.add(px);
            }
        }

    }


    @Override
    public void run(){
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
        synchronized(queueLock){
            while("F".equals(p1) && "F".equals(p2)){
                try {
                    queueLock.wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }  

        if("T".equals(p1)){
            synchronized(p1Lock){
                // Change back to "F" -busy
                changeTerminal("p1");


                policeCrossingLogic();


                // Change back to "T" -free
                changeTerminal("p1");
                synchronized(queueLock){
                    try {
                        queueLock.notifyAll();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
        else if("T".equals(p2)){
            synchronized(p2Lock){
                // Change back to "F" -busy
                changeTerminal("p2");


                policeCrossingLogic();


                // Change p2 back to "T" -free
                changeTerminal("p2");
                synchronized(queueLock){
                    try {
                        queueLock.notifyAll();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
        

        if(busRejected){
            System.out.println("Vehicle [" + this + "] returned from the border [POLICE]!");
            return;
        }


        // Then they go to the border crossing


        checkTerminals();
        synchronized(queueLock){
            while("F".equals(c1)){
                try {
                    queueLock.wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        if("T".equals(c1)){
            synchronized(c1Lock){
                // Change back to "F" -busy
                changeTerminal("c1");


                borderCrossingLogic();


                // Change c1 back to "T" -free
                changeTerminal("c1");
                synchronized(queueLock){
                    try {
                        queueLock.notifyAll();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

                // Only the info that the bus has been rejected from the border
                if(busRejected){
                    createEvidentationBus(BORDER_EVIDENTATION);
                    System.out.println("Vehicle [" + this + "] returned from the border [BORDER CUSTOMS]!");
                    return;
                }
            }
        }
    
        System.out.println("Vehicle [" + this + "] has passed the border!");
        
    }


    // What every bus does on the police crossing
    private void policeCrossingLogic(){
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
                sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }

            if(p.getIdentification().isFakeId()){
                // Passenger added to the naughty list
                badPassengers.add(p);
                // passengers.remove(p);

                if(p.isDriver()){
                    numOfBadDrivers++;
                }
                else{
                    numOfBadPassengers++;
                }

                if(numOfBadDrivers == 2){
                    // Noted that the bus has been rejected
                    busRejected = true;
                }

            }

        }

        // Serialized data about every illegal passenger
        serializeBadPassengers();

        // Only the info that the bus has been rejected from the border
        if(busRejected)
            createEvidentationBus(POLICE_EVIDENTATION);

        // How many passengers are thrown out
        if(numOfBadPassengers > 0){
            createEvidentationPassengers(POLICE_EVIDENTATION, numOfBadPassengers, numOfBadDrivers);
        }
    }


    // What every bus does on the border customs
    private void borderCrossingLogic(){
        // Border logic
        int numOfBadPassengers = 0;

        Iterator<Passenger> iterator = passengers.iterator();
        while(iterator.hasNext()){
            Passenger p = iterator.next();
            try {
                sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }

            if(p.hasForbiddenLuggage()){
                // passengers.remove(p);

                if(p.isDriver()){
                    numOfBadDrivers++;
                }
                else{
                    numOfBadPassengers++;
                }

                if(numOfBadDrivers == 2){
                    // Noted that the bus has been rejected
                    busRejected = true;
                }

            }

        }
            

        // How many passengers are thrown out
        if(numOfBadPassengers > 0 || numOfBadDrivers > 0){
            createEvidentationPassengers(BORDER_EVIDENTATION, numOfBadPassengers, numOfBadDrivers);
        }

    }


    // Notes that the bus has been rejected
    synchronized private void createEvidentationBus(String FILE){
        boolean append = (new File(FILE)).exists();
        try{
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(FILE, append));
            writer1.write("SVI;AUTOBUS;" + this.id + "\n");
            writer1.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    // Serializes how many passengers are thrown out
    synchronized private void createEvidentationPassengers(String FILE, int numOfBadPassengers, int numOfBadDrivers){
        boolean append = (new File(FILE)).exists();
        try{
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(FILE, append));
            writer1.write("PUTNIK;" + numOfBadPassengers + ";AUTOBUS;" + this.id + ";" + numOfBadDrivers + "\n");
            writer1.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    @Override
    public String toString(){
        return "Bus " + "(id: " + id + ") with " + numOfPeople + " passengers.";
    }

}