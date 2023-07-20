package org.unibl.etf;


import org.unibl.etf.identifications.*;
import org.unibl.etf.passengers.*;
import org.unibl.etf.vehicles.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;


public class BorderSimulation{
    protected static final String TERMINALS_FILE      = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "terminals.txt";

    public static ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();
    public static Stack<Vehicle> vehicleStack = new Stack<Vehicle>();


    public static void main(String args[]){

        // Filling up the array with Vehicles
        if(createArray() == false){
            System.out.println("Error creating an array of vehicles!");
        }

        for(Vehicle v : vehicleArray){
            vehicleStack.push(v);
        }

        setTerminalsToTrue();

        // Simulation start
        System.out.println("---Simulation started---\n");
        for(Vehicle v : vehicleStack){
            v.start();
        }


        try{
            for(Vehicle v : vehicleStack){
            v.join();
            }
        }
        catch(InterruptedException e){
            System.out.println(e);
        }
        
        
        System.out.println("---Simulation ended---\n");


    }


    private static void setTerminalsToTrue(){
        String text = "p1-T\np2-T\np3-T\nc1-T\nc2-T";

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(TERMINALS_FILE));
            writer.write(text);
            writer.close();
        }
        catch(Exception e){
            System.out.println("Could not write to TERMINALS_FILE in setTerminalsToTrue!");
        }
    }


    private static boolean createArray(){
        for(int i = 0; i < 5; i++){
            Bus b = new Bus();
            vehicleArray.add(b);
        }
        for(int i = 0; i < 10; i++){
            Truck t = new Truck();
            vehicleArray.add(t);
        }
        for(int i = 0; i < 35; i++){
            Car c = new Car();
            vehicleArray.add(c);
        }

        Collections.shuffle(vehicleArray);

        return true;
    }
}