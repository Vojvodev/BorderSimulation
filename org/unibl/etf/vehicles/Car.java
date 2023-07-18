package org.unibl.etf.vehicles;


import org.unibl.etf.BorderSimulation;
import org.unibl.etf.passengers.Passenger;

import java.util.HashSet;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


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
        int changed;

        if(this.equals(BorderSimulation.vehicleStack.peek()) == false){      // Only the first in line looks to get to the terminals
            wait();
        }


        checkTerminals();
        if(p1 == "F" && p2 == "F"){
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e);
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

            BorderSimulation.vehicleStack.pop();
            // notifyAll();                                         NOTIFYYYYY



            // Police terminal   
            int numOfBadPassengers = 0;
            for(Passenger p : passengers){
                sleep(500);
                if(p.getIdentification().isFakeId()){
                    numOfBadPassengers++;
                    
                    // Passenger added to the naughty list
                    boolean append1 = (new File(SERIALIZATION_FILE)).exists();
                    try{
                        ObjectOutputStream stream1 = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE, append1));
                        stream1.writeObject(p);
                        stream1.close();
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }

                    if(p.isDriver()){                       
                        // Noted that the car has been rejected
                        boolean append = (new File(POLICE_EVIDENTATION)).exists();
                        try {
                            BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
                            writer1.write("SVI;AUTOMOBIL;" + this.id);
                            writer1.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }   
                    }
                    
                    passengers.remove(p);
                }
            }   

            // How many passengers are thrown out
            boolean append = (new File(POLICE_EVIDENTATION)).exists();
            try {
                BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
                writer1.write("PUTNIK;" + numOfBadPassengers + ";AUTOMOBIL" + this.id);
                writer1.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            


            // Change back to "T" -free
            if(changed == 1){
                changeTerminal("p1");
            }
            else if(changed == 2){
                changeTerminal("p2");
            }
            // notifyAll();                                                 NOTIFYYYYY
        }
        

        // Then they go to the border crossing
        
        checkTerminals();
        if(c1 == "F" && c2 == "F"){
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else if(c1 == "T" || c2 == "T"){
            // Change back to "F" -busy
            if(c1 == "T"){
                changeTerminal("c1");
                changed = 1;
            }
            else if(c1 == "F" && c2 == "T"){
                changeTerminal("c2");
                changed = 2;
            }

            // notifyAll();                                                 NOTIFYYYYY

            // Border logic
            try{
                sleep(2000);
            }
            catch(Exception e){
                System.out.println(e);
            }

            // Change back to "T" -free
            if(changed == 1){
                changeTerminal("c1");
            }
            else if(changed == 2){
                changeTerminal("c2");
            }

            // notifyAll();                                                 NOTIFYYYYY
        }

    }


    @Override
    public String toString(){
        return "Car " + "(id: " + id + ") with " + numOfPeople + " passengers.";
    }
}