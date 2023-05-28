package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.HashSet;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


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


    public void run(){
        int numOfBadDrivers = 0;

        // Read p1 & p2

        if(p1 == "F" && p2 == "F"){
            try {
                wait();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else if(p1 == "T"){
            // Change p1 to "F" --- do --- change back p1 to "T"
        }
        else if(p2 == "T"){
            // Change p2 to "F" --- do --- change back p2 to "T"
        }
        

        // Police terminal
        {
            int numOfBadPassengers = 0;

            for(Passenger p : passengers){
                sleep(100);
                if(p.getIdentification().isFakeId()){
                    passengers.remove(p);

                    // Passenger added to the naughty list
                    boolean append1 = (new File(SERIALIZATION_FILE)).exists();
                    ObjectOutputStream stream1 = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE, append1));
                    stream1.writeObject(p);
                    stream1.close();


                    if(p.isDriver()){
                        numOfBadDrivers++;
                    }
                    else{
                        numOfBadPassengers++;
                    }


                    if(numOfBadDrivers == 2){
                        // Noted that the bus has been rejected
                        boolean append = (new File(POLICE_EVIDENTATION)).exists();
                        BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
                        writer1.write("SVI;AUTOBUS;" + this.id);
                        writer1.close();

                        // interrupt();  ili   exception       // Da ga prekine provjeravati i da ga izbaci iz liste vozila
                    }
                }
            }

            // How many passengers are thrown out
            boolean append = (new File(POLICE_EVIDENTATION)).exists();
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
            writer1.write("PUTNIK;" + numOfBadPassengers + ";AUTOBUS;" + this.id + ";" + numOfBadDrivers);
            writer1.close();

        }

        // Read c1

        // Then they go to the border crossing
        if(c1 == "F"){
            try {
                wait();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else{

            // Change c1 to "F"

            int numOfBadPassengers = 0;

            for(Passenger p : passengers){
                sleep(100);

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
                        boolean append = (new File(BORDER_EVIDENTATION)).exists();
                        BufferedWriter writer1 = new BufferedWriter(new FileWriter(BORDER_EVIDENTATION, append));
                        writer1.write("SVI;AUTOBUS;" + this.id);
                        writer1.close();

                        // interrupt();  ili   exception       // Da ga prekine provjeravati i da ga izbaci iz liste vozila
                    }
                }

            }

            // How many passengers are thrown out
            boolean append = (new File(BORDER_EVIDENTATION)).exists();
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(BORDER_EVIDENTATION, append));
            writer1.write("PUTNIK;" + numOfBadPassengers + ";AUTOBUS;" + this.id + ";" + numOfBadDrivers);
            writer1.close();


            // Change c1 back to "T"
        }


    }


    @Override
    public String toString(){
        return "Bus " + "(id: " + id + ") with " + numOfPeople + " passengers.";
    }
}