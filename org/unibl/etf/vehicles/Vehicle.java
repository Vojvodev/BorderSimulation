package org.unibl.etf.vehicles;


import org.unibl.etf.BorderSimulation;
import org.unibl.etf.GUI.Frame1;
import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.HashSet;
import java.lang.Thread;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.NullPointerException;
import java.util.logging.*;


public abstract class Vehicle extends Thread{
    public static final String SERIALIZATION_FILE  = "evidentations" + File.separator + "NAUGHTY_LIST.binary";
    public static final String POLICE_EVIDENTATION = "evidentations" + File.separator + "POLICIJSKA_KONTROLA.txt";
    public static final String BORDER_EVIDENTATION = "evidentations" + File.separator + "CARINSKA_KONTROLA.txt";
    protected static final String TERMINALS_FILE   = "terminals.txt";
 
    protected static volatile String p1, p2, p3, c1, c2;                    // To read terminal states
    public static final Object stackLock = new Object();
    protected static final Object queueLock = new Object();
    
    protected static final Object p1Lock = new Object();
    protected static final Object p2Lock = new Object();
    protected static final Object p3Lock = new Object();
    protected static final Object c1Lock = new Object();
    protected static final Object c2Lock = new Object();

    private static int counter = 0;
    protected HashSet<Passenger> passengers;
    protected HashSet<Passenger> badPassengers;
    protected int numOfPeople;
    protected int id;


    public Vehicle(int capacity){
        Random rand = new Random();

        this.numOfPeople = rand.nextInt(1, capacity + 1);
        this.passengers = new HashSet<Passenger>();
        this.badPassengers = new HashSet<Passenger>();
        this.id = counter++;
    }


    // Reads which terminals are free
    protected synchronized void checkTerminals(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(TERMINALS_FILE));
            String line;

            while((line = reader.readLine()) != null){
                String parts[] = line.split("-");
            
                if("p1".equals(parts[0])){
                    if("T".equals(parts[1])){
                        p1 = "T";
                    }
                    else if("F".equals(parts[1])){
                        p1 = "F";
                    }
                }

                if("p2".equals(parts[0])){
                    if("T".equals(parts[1])){
                        p2 = "T";
                    }
                    else if("F".equals(parts[1])){
                        p2 = "F";
                    }
                }

                if("p3".equals(parts[0])){
                    if("T".equals(parts[1])){
                        p3 = "T";
                    }
                    else if("F".equals(parts[1])){
                        p3 = "F";
                    }
                }

                if("c1".equals(parts[0])){
                    if("T".equals(parts[1])){
                        c1 = "T";
                    }
                    else if("F".equals(parts[1])){
                        c1 = "F";
                    }
                }

                if("c2".equals(parts[0])){
                    if("T".equals(parts[1])){
                        c2 = "T";
                    }
                    else if("F".equals(parts[1])){
                        c2 = "F";
                    }
                }
            }

            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error!");
            BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not read from TERMINALS_FILE!", e);
        } 
        catch(IOException e1){
            System.out.println("Error!");
            BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not read using a reader!", e1);
        } 
    }


    // Changes the value of argument terminal
    protected synchronized void changeTerminal(String terminal){
        String text = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(TERMINALS_FILE));

            String line = "";

            while((line = reader.readLine()) != null){
                String parts[] = line.split("-");

                if(terminal.equals(parts[0])){
                    if("T".equals(parts[1])){
                        parts[1] = "F";
                    }
                    else if("F".equals(parts[1])){
                        parts[1] = "T";
                    }

                    line = parts[0] + "-" + parts[1];
                }

                text += (line + "\n");
            }

            reader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error!");
            BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not read from TERMINALS_FILE!", e);
        }
        catch(IOException e1){
            System.out.println("Error!");
            BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not read using a reader!", e1);
        }

        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(TERMINALS_FILE));
            writer.write(text);
            writer.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error!");
            BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not write to TERMINALS_FILE!", e);
        }
        catch(IOException e1){
            System.out.println("Error!");
            BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not write using a writer!", e1);
        }

        checkTerminals();
    }


    // Serializes data about every illegal passenger
    protected synchronized void serializeBadPassengers(){
        if(!badPassengers.isEmpty()){
            boolean append1 = (new File(SERIALIZATION_FILE)).exists();
            try{
                ObjectOutputStream stream1 = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE, append1));
                stream1.writeObject(badPassengers);
                stream1.close();
            }
            catch(FileNotFoundException e){
                System.out.println("Error!");
                BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not serialize to SERIALIZATION_FILE!", e);
            }
            catch(NullPointerException e1){
                System.out.println("Error!");
                BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "SERIALIZATION_FILE argument is null!", e1);
            }
            catch(Exception e2){
                System.out.println("Error!");
                BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Something wrong with I/O!", e2);
            }
        }
        
    }

    
    // Prints first five vehicles in the stack, must be called every time the stack changes
    public static void printFirstFiveVehicles() {
    	Vehicle[] tempVehicleArray = new Vehicle[5];
    	
    	for (int i = 0; i < 5; i++) {
        	if (!BorderSimulation.vehicleStack.isEmpty()) {
                tempVehicleArray[i] = BorderSimulation.vehicleStack.pop();
            } else {
                tempVehicleArray[i] = null; // In case the original stack has less than five elements
            }
		}
		
		Frame1.lblFifthVehicle.setText( tempVehicleArray[4] != null ? tempVehicleArray[4].toString() : "X");
		Frame1.lblFourthVehicle.setText(tempVehicleArray[3] != null ? tempVehicleArray[3].toString() : "X");
        Frame1.lblThirdVehicle.setText( tempVehicleArray[2] != null ? tempVehicleArray[2].toString() : "X");
        Frame1.lblSecondVehicle.setText(tempVehicleArray[1] != null ? tempVehicleArray[1].toString() : "X");
        Frame1.lblFirstVehicle.setText( tempVehicleArray[0] != null ? tempVehicleArray[0].toString() : "X");
        
        
        // Push the elements back to the original stack to restore the order
       	for (int i = 4; i >= 0; i--) {
       		BorderSimulation.vehicleStack.push(tempVehicleArray[i]);
        }
    }
    
    
    // Reads everything from one of the evidentation files
    public static synchronized String readEvidentation(String file) {
    	String line = "";
    	String text = "";
    	if((new File(file)).exists()) {
    		try {
    			BufferedReader reader = new BufferedReader(new FileReader(file));
    			
    			while((line = reader.readLine()) != null) {
    				text += line + "\n";
    			}
    		
    			reader.close();
    		}
    		catch(FileNotFoundException e){
    			System.out.println("Error!");
    			BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not read from POLICE_EVIDENTATION!", e);
    		} 
    		catch(IOException e1){
    			System.out.println("Error!");
    			BorderSimulation.VEHICLELOGGER.log(Level.SEVERE, "Could not read using a reader!", e1);
    		} 
    	}
    	
    	return text;
    }
    

    public int getVehicleId() {
    	return this.id;
    }
    
    
    public String writePassengers() {
    	String line = "";
    	for(Passenger p : this.passengers) {
    		line += p.toString() + "\n";
    	}
    	
    	return line;
    }
}