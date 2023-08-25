package org.unibl.etf.vehicles;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.logging.Level;

import javax.swing.ImageIcon;

import org.unibl.etf.BorderSimulation;
import org.unibl.etf.GUI.Frame1;
import org.unibl.etf.passengers.Passenger;


public class Car extends Vehicle{
    private static final int CAPACITY = 5;

    boolean carRejected = false;

    
    public Car(){
        super(CAPACITY);

        // Each car has only 1 driver (car's owner)
        Passenger p = new Passenger("N.N. driver", true);
        this.passengers.add(p);

        for(int i = 0; i < numOfPeople - 1; i++){
            Passenger p1 = new Passenger("N.N." + (i+1), false);
            this.passengers.add(p1);
        }
    }


    @Override
    public void run(){
    	String whichTerminalToSetFree = "p1";
    	
        synchronized(stackLock){
            while(this.equals(BorderSimulation.vehicleStack.peek()) == false){      // Only the first in line looks to get to the terminals
                try {
                    stackLock.wait();
                } catch (EmptyStackException e){
                    System.out.println("Error!");
                    BorderSimulation.CARLOGGER.log(Level.SEVERE, "The vehicleStack is empty!", e);
                }
                catch(Exception e1){
                    System.out.println("Error!");
                    BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with wait()!", e1);
                }
            }
        }
        

        checkTerminals();
        synchronized(queueLock){
            while("F".equals(p1) && "F".equals(p2)){
                try {
                    queueLock.wait();
                } catch (Exception e){
                    System.out.println("Error!");
                    BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with wait()!", e);
                }
            }   
        }
  
        if("T".equals(p1)){
            synchronized(p1Lock){
                // Change back to "F" -busy
                changeTerminal("p1");
                whichTerminalToSetFree = "p1";

                
                Frame1.lblP1IMG.setIcon(new ImageIcon(Frame1.CAR_IMG_FILE));
                Frame1.lblP1IMG.setText("#" + this.id);
                policeCrossingLogic();
                
        
                // Change back to "T" happens after the car has gone into the border customs terminal
            }
        }
        else if("T".equals(p2)){
            synchronized(p2Lock){
                // Change back to "F" -busy
                changeTerminal("p2");
                whichTerminalToSetFree = "p2";
                

                Frame1.lblP2IMG.setIcon(new ImageIcon(Frame1.CAR_IMG_FILE));
                Frame1.lblP2IMG.setText("#" + this.id);
                policeCrossingLogic();
                
        
                // Change back to "T" happens after the car has gone into the border customs terminal
            }
        }

        
        if(carRejected){
        	// Change p1 or p2 back to "T" -free
        	changeTerminal(whichTerminalToSetFree);
        	
        	// Delete the car icon from the terminal
        	if(whichTerminalToSetFree == "p1")
                Frame1.lblP1IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
            else
            	Frame1.lblP2IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
        	
            System.out.println("Vehicle [" + this + "] returned from the border [POLICE]!");
            Frame1.lblCount.setText(Integer.toString(BorderSimulation.processedVehiclesCounter++));
            
            return;
        }
         
        
        // Then they go to the border crossing
        

        checkTerminals();
        synchronized(queueLock){
            while("F".equals(c1)){
                try {
                    queueLock.wait();
                } catch (Exception e){
                    System.out.println("Error!");
                    BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with wait()!", e);
                }
            }
        }
        if("T".equals(c1)){
            synchronized(c1Lock){
                // Change back to "F" -busy
                changeTerminal("c1");
            
                
                // Change p1 or p2 back to "T" -free
                changeTerminal(whichTerminalToSetFree);
                
                // Delete the car icon from the terminal
                if(whichTerminalToSetFree == "p1")
                    Frame1.lblP1IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
                else
                	Frame1.lblP2IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
                
                
                synchronized(queueLock){
                    try {
                        queueLock.notifyAll();
                    } catch (Exception e){
                        System.out.println("Error!");
                        BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with notifyAll()!", e);
                    }
                }
                
                
                Frame1.lblC1IMG.setIcon(new ImageIcon(Frame1.CAR_IMG_FILE));
                Frame1.lblC1IMG.setText("#" + this.id);
                borderCrossingLogic();


                // Change c1 back to "T" -free
                changeTerminal("c1");
                synchronized(queueLock){
                    try {
                        queueLock.notifyAll();
                    } catch (Exception e){
                        System.out.println("Error!");
                        BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with notifyAll()!", e);
                    }
                }
            }   
        }

        System.out.println("Vehicle [" + this + "] has passed the border!");
        Frame1.lblCount.setText(Integer.toString(BorderSimulation.processedVehiclesCounter++));
        Frame1.lblC1IMG.setIcon(new ImageIcon(Frame1.EMPTY_ICON_FILE));
        
    }


 
    // What every car does on the police crossing
    private void policeCrossingLogic(){
        synchronized(stackLock){
            BorderSimulation.vehicleStack.pop();
            Vehicle.printFirstFiveVehicles();
            try {
                stackLock.notifyAll();        // Condition changed, now someone else is the first in line
            } catch (Exception e){
                System.out.println("Error!");
                BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with notifyAll()!", e);
            }
            
        }

        // Police terminal   
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
                BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with sleep()!", e);
            }

            if(p.getIdentification().isFakeId()){
                numOfBadPassengers++;
                
                // Passenger added to the naughty list
                synchronized(badPassengers) {
            		badPassengers.add(p);
            	}

                if(p.isDriver()){                       
                    // Noted that the car has been rejected
                    carRejected = true;
                }
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
    }


    // What every car does on the border customs
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

        try{
            sleep(2000);
        }
        catch(Exception e){
            System.out.println("Error!");
            BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with sleep()!", e);
        }
    }


    // Serializes that the car has been rejected from the border
    synchronized private void createPoliceEvidentationCar(){
        boolean append = (new File(POLICE_EVIDENTATION)).exists();
        try {
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
            writer1.write("SVI;AUTOMOBIL;" + this.id + "\n");
            writer1.close();
        } catch (Exception e){
            System.out.println("Error!");
            BorderSimulation.CARLOGGER.log(Level.SEVERE, "Could not write to POLICE_EVIDENTATION!", e);
        } 
    }


    // Serializes how many passengers are thrown out
    synchronized private void createPoliceEvidentationPassengers(int numOfBadPassengers){
        boolean append = (new File(POLICE_EVIDENTATION)).exists();
        try {
            BufferedWriter writer1 = new BufferedWriter(new FileWriter(POLICE_EVIDENTATION, append));
            writer1.write("PUTNIK;" + numOfBadPassengers + ";AUTOMOBIL;" + this.id + "\n");
            writer1.close();
        } catch (Exception e){
            System.out.println("Error!");
            BorderSimulation.CARLOGGER.log(Level.SEVERE, "Could not write to POLICE_EVIDENTATION!", e);
        }
    }


    @Override
    public String toString(){
        return "Car " + "(id: " + id + ") with " + numOfPeople + " passengers.";
    }

}