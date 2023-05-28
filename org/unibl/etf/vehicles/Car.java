package org.unibl.etf.vehicles;


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


    public void run(){

        // Read p1 & p2

        if(p1 == "F" && p2 == "F"){
            try {
                wait();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else if(p1 == "T"){
            // Change p1 to "F" --- do --- change back to "T"
        }
        else if(p2 == "T"){
            // Change p2 to "F" --- do --- change back to "T"
        }
        

        
        // Police terminal
        {                         // Treba da se upisuje u fajl kad moze a kad ne moze da se pristupa terminalima    
            int numOfBadPassengers = 0;
            for(Passenger p : passengers){
                sleep(500);
                if(p.getIdentification().isFakeId()){
                    numOfBadPassengers++;
                    
                    // Passenger added to the naughty list
                    boolean append1 = (new File(SERIALIZATION_FILE)).exists();
                    ObjectOutputStream stream1 = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE, append1));
                    stream1.writeObject(p);
                    stream1.close();

                    if(p.isDriver()){                       //  TODO: DODATI TRY - CATCH BLOKOVE ...
                        // Noted that the car has been rejected
                        boolean append = (new File(POLICE_EVIDENTATION)).exists();
                        BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
                        writer1.write("SVI;AUTOMOBIL;" + this.id);
                        writer1.close();
                        
                        // interrupt();         // Da ga prekine provjeravati i da ga izbaci iz liste vozila
                    }
                    
                    passengers.remove(p);
                }
            }   

            // How many passengers are thrown out
            boolean append = (new File(POLICE_EVIDENTATION)).exists();
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
            writer1.write("PUTNIK;" + numOfBadPassengers + ";AUTOMOBIL" + this.id);
            writer1.close();


            // Change p1 or p2 back to "T" - notify 
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

            sleep(2000);

            // Change c1 to "T"
        }

    }


    @Override
    public String toString(){
        return "Car " + "(id: " + id + ") with " + numOfPeople + " passengers.";
    }
}