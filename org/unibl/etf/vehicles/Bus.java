package org.unibl.etf.vehicles;

import org.unibl.etf.BorderSimulation;
import org.unibl.etf.passengers.Passenger;

import java.util.HashSet;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;



public class Bus extends Vehicle{
    private static final int CAPACITY = 52; 

    
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
        int numOfBadDrivers = 0;
        boolean busRejected = false;
        int changed = 1;


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
        if(p1 == "F" && p2 == "F"){
            synchronized(this){
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        else if(p1 == "T" || p2 == "T"){
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

            for(Passenger p : passengers){
                try {
                    sleep(100);
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
            createEvidentationPassengers(POLICE_EVIDENTATION, numOfBadPassengers, numOfBadDrivers);



            // Change back to "T" -free
            if(changed == 1){
                changeTerminal("p1");
            }
            else if(changed == 2){
                changeTerminal("p2");
            }
            notifyAll();

        }
        

        if(busRejected)
            return;


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
            notifyAll();                            // Notify when changing to F ???


            // Border logic
            int numOfBadPassengers = 0;

            for(Passenger p : passengers){
                try {
                    sleep(100);
                } catch (Exception e) {
                    System.out.println(e);
                }

                if(p.hasForbiddenLuggage()){
                    passengers.remove(p);

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

            // Only the info that the bus has been rejected from the border
            if(busRejected)
                createEvidentationBus(BORDER_EVIDENTATION);

            // How many passengers are thrown out
            createEvidentationPassengers(BORDER_EVIDENTATION, numOfBadPassengers, numOfBadDrivers);


            // Change back to "T" -free
            changeTerminal("c1");
            notifyAll();

        }
    
    }


    // Notes that the bus has been rejected
    synchronized private void createEvidentationBus(String FILE){
        boolean append = (new File(FILE)).exists();
        try{
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(FILE, append));
            writer1.write("SVI;AUTOBUS;" + this.id);
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
            writer1.write("PUTNIK;" + numOfBadPassengers + ";AUTOBUS;" + this.id + ";" + numOfBadDrivers);
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