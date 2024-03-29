package org.unibl.etf.vehicles;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.ImageIcon;

import org.unibl.etf.BorderSimulation;
import org.unibl.etf.GUI.Frame1;
import org.unibl.etf.passengers.Passenger;


public class Truck extends Vehicle{
    private static int CAPACITY = 3;
    private int loadCapacity;
    private int realLoad;
    private boolean documentationNeeded;
    
    private boolean truckRejected = false;


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
        synchronized(stackLock){
            while(this.equals(BorderSimulation.vehicleStack.peek()) == false){      // Only the first in line looks to get to the terminals
                try {
                    stackLock.wait();
                } catch (EmptyStackException e){
                    System.out.println("Error!");
                    BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "The vehicleStack is empty!", e);
                }
                catch(Exception e1){
                    System.out.println("Error!");
                    BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Problems with wait()!", e1);
                }
            }
        }


        checkTerminals();
        synchronized(queueLock){
            while("F".equals(p3)){
                try {
                    queueLock.wait();
                } catch (Exception e){
                    System.out.println("Error!");
                    BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Problems with wait()!", e);
                }
            }
        }

        if("T".equals(p3)){
            synchronized(p3Lock){
                // Change back to "F" -busy
                changeTerminal("p3");


                Frame1.lblP3IMG.setIcon(new ImageIcon(Frame1.TRUCK_IMG_FILE));
                Frame1.lblP3IMG.setText("#" + this.id);
                policeCrossingLogic();


                // Change back to "T" happens after the car has gone into the border customs terminal
            }
        }
    

        if(truckRejected){
        	// Change p3 back to "T"
        	changeTerminal("p3");
        	
        	// Delete truck icon from the terminal
        	Frame1.lblP3IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
        	
            System.out.println("Vehicle [" + this + "] returned from the border [POLICE]!");
            Frame1.lblCount.setText(Integer.toString(BorderSimulation.processedVehiclesCounter++));
            return;
        }


        // Then they go to the border crossing


        checkTerminals();
        synchronized(queueLock){
            while("F".equals(c2)){
                try {
                    queueLock.wait();
                } catch (Exception e){
                    System.out.println("Error!");
                    BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Problems with wait()!", e);
                }
            }
        }

        if("T".equals(c2)){
            synchronized(c2Lock){
                // Change back to "F" -busy
                changeTerminal("c2");
            
                
                // Change p3 back to "T"
                changeTerminal("p3");
                
                // Delete truck icon from the terminal
                Frame1.lblP3IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
                
                
                synchronized(queueLock){
                    try {
                        queueLock.notifyAll();
                    } catch (Exception e){
                        System.out.println("Error!");
                        BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Problems with notifyAll()!", e);
                    }
                }
                
                
                Frame1.lblC2IMG.setIcon(new ImageIcon(Frame1.TRUCK_IMG_FILE));
                Frame1.lblC2IMG.setText("#" + this.id);
                borderCrossingLogic();
            

                // Change c2 back to "T"
                changeTerminal("c2");
                synchronized(queueLock){
                    try {
                        queueLock.notifyAll();
                    } catch (Exception e){
                        System.out.println("Error!");
                        BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Problems with notifyAll()!", e);
                    }
                }

                if(truckRejected){
                    System.out.println("Vehicle [" + this + "] returned from the border [BORDER CUSTOMS]!");
                    Frame1.lblCount.setText(Integer.toString(BorderSimulation.processedVehiclesCounter++));
                    Frame1.lblC2IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
                    return;
                }
            }
        }

        System.out.println("Vehicle [" + this + "] has passed the border!");
        Frame1.lblCount.setText(Integer.toString(BorderSimulation.processedVehiclesCounter++));
        Frame1.lblC2IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
        
    }


    // What every truck does on the police crossing
    private void policeCrossingLogic(){
        synchronized(stackLock){
            BorderSimulation.vehicleStack.pop();
            Vehicle.printFirstFiveVehicles();
            try {
                stackLock.notifyAll();        // Condition changed, now someone else is the first in line
            } catch (Exception e){
                System.out.println("Error!");
                BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Problems with notifyAll()!", e);
            }
            
        }
        
        
        int numOfBadDrivers = 0;
        int numOfBadPassengers = 0;

        Iterator<Passenger> iterator = passengers.iterator();
        while(iterator.hasNext()){

            if(BorderSimulation.pause){
                synchronized (this) {
                    try {
                        wait();
                    } catch (Exception e) {
                        System.out.println("Error!");
                        BorderSimulation.CARLOGGER.log(Level.SEVERE, "Could not wait during pause!", e);
                    }
                }
            }
            

            Passenger p = iterator.next();
            try {
                sleep(500);
            } catch (Exception e){
                System.out.println("Error!");
                BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Problems with sleep()!", e);
            }

            if(p.getIdentification().isFakeId()){
                // Passenger added to the naughty list
            	synchronized(badPassengers) {
            		badPassengers.add(p);
            	}

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

        // Only the info that the truck has been rejected from the border
        if(truckRejected)
            createEvidentationTruck(POLICE_EVIDENTATION);

        // How many passengers are thrown out
        if(numOfBadPassengers > 0){
            createEvidentationPassengers(POLICE_EVIDENTATION, numOfBadPassengers);
        }
    }


    private void borderCrossingLogic(){

        if(BorderSimulation.pause){
            synchronized (this) {
                try {
                    wait();
                } catch (Exception e) {
                    System.out.println("Error!");
                    BorderSimulation.CARLOGGER.log(Level.SEVERE, "Could not wait during pause!", e);
                }
            }
        }

        try {
            sleep(500);
        } catch (Exception e){
            System.out.println("Error!");
            BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Problems with sleep()!", e);
        }

        if(this.isDocumentationNeeded()){
            if(realLoad > loadCapacity){
                // Noted that the truck has been rejected
                truckRejected = true;
                createEvidentationTruck(BORDER_EVIDENTATION);
            }    
        }
    }


    // Notes that the truck has been rejected
    synchronized private void createEvidentationTruck(String FILE){
        boolean append = (new File(FILE)).exists();
        try{
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(FILE, append));
            writer1.write("SVI;KAMION;" + this.id + "\n");
            writer1.close();
        }
        catch(Exception e){
            System.out.println("Error!");
            BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Could not write to FILE!", e);
        }
    }


    // Serializes how many passengers are thrown out
    synchronized private void createEvidentationPassengers(String FILE, int numOfBadPassengers){
        boolean append = (new File(FILE)).exists();
            try{
                BufferedWriter writer1 = new BufferedWriter(new FileWriter(FILE, append));
                writer1.write("PUTNIK;" + numOfBadPassengers + ";KAMION;" + this.id + "\n");
                writer1.close();
            }
            catch(Exception e){
                System.out.println("Error!");
                BorderSimulation.TRUCKLOGGER.log(Level.SEVERE, "Could not write to FILE!", e);
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