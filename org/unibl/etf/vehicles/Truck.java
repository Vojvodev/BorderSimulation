package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.HashSet;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


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
        
        // Read p3

        // Police terminal
        if(p3 == "F"){
            try {
                wait();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else{

            // Change p3 to "F"

            int numOfBadDrivers = 0;
            int numOfBadPassengers = 0;

            for(Passenger p : passengers){
                sleep(500);
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

                    if(numOfBadDrivers == 3){
                        // Noted that the truck has been rejected
                        boolean append = (new File(POLICE_EVIDENTATION)).exists();
                        BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
                        writer1.write("SVI;KAMION;" + this.id);
                        writer1.close();

                        // interrupt();  ili   exception       // Da ga prekine provjeravati i da ga izbaci iz liste vozila
                    }
                }
            }

            // How many passengers are thrown out
            boolean append = (new File(POLICE_EVIDENTATION)).exists();
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
            writer1.write("PUTNIK;" + numOfBadPassengers + ";KAMION;" + this.id);
            writer1.close();


            // Change p3 back to "T"

        }


        // Read c2


        // Then they go to the border crossing
        if(c2 == "F"){
            try {
                wait();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else{

            // Change c2 to "F"

            sleep(500);
            if(this.isDocumentationNeeded()){
                if(realLoad > loadCapacity){
                    // Noted that the truck has been rejected
                    boolean append = (new File(BORDER_EVIDENTATION)).exists();
                    BufferedWriter writer1 = new BufferedWriter(new FileWriter(BORDER_EVIDENTATION, append));
                    writer1.write("SVI;KAMION;" + this.id);
                    writer1.close();

                    // TODO: IZBACITI KAMION IZ LISTE --- interrupt() neki
                }    
            }
            

            // Change c2 back to "T"

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