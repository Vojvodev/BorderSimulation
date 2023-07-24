package org.unibl.etf;


import org.unibl.etf.identifications.*;
import org.unibl.etf.passengers.*;
import org.unibl.etf.stopwatch.Stopwatch;
import org.unibl.etf.vehicles.*;

import java.util.Scanner;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.lang.InterruptedException;
import java.util.logging.*;


public class BorderSimulation{
    protected static final String TERMINALS_FILE      = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "terminals.txt";
    protected static final String MAIN_LOG_FILE       = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "logs" + File.separator + "mainLogs.log";
    protected static final String VEHICLE_LOG_FILE    = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "logs" + File.separator + "vehicleLogs.log";
    protected static final String CAR_LOG_FILE        = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "logs" + File.separator + "carLogs.log";
    protected static final String BUS_LOG_FILE        = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "logs" + File.separator + "busLogs.log";
    protected static final String TRUCK_LOG_FILE      = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "logs" + File.separator + "truckLogs.log";

    public static ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();       // Doesn't change after the threads start
    public static Stack<Vehicle> vehicleStack = new Stack<Vehicle>();               // Does change
    
    private static final Logger MAINLOGGER   = Logger.getLogger(BorderSimulation.class.getName());
    public static final Logger VEHICLELOGGER = Logger.getLogger(Vehicle.class.getName());
    public static final Logger CARLOGGER     = Logger.getLogger(Car.class.getName());
    public static final Logger BUSLOGGER     = Logger.getLogger(Bus.class.getName());
    public static final Logger TRUCKLOGGER   = Logger.getLogger(Truck.class.getName());

    public static boolean pause = false;


    public static void main(String args[]){
        Stopwatch timer = new Stopwatch();
        Scanner scan = new Scanner(System.in);
        String option = "";
        
        // Configuring loggers
        configureLogger();


        // Filling up the array with Vehicles
        if(createArray() == false){
            System.out.println("Error creating an array of vehicles!");
        }

        for(Vehicle v : vehicleArray){
            vehicleStack.push(v);
        }


        for(Vehicle v : vehicleArray){
            System.out.println(v);
        }


        setTerminalsToTrue();


        // Simulation start
        System.out.println("\n---Simulation started---\n");

        for(Vehicle v : vehicleArray){
            v.start();
        }
        timer.setDaemon(true);
        timer.start();
        

        //while (!"END".equals(option)) {                           // TODO: Instead of while() I need something to check if PAUSE button is pressed
        //    option = scan.nextLine();
        //    if ("PAUSE".equals(option)) {
        //        pause = true;
        //    }
        //    if ("CONTINUE".equals(option)) {
        //        pause = false;
        //        resumeSimulation();
        //    }
        //}
        //scan.close();


        try{
            for(Vehicle v : vehicleArray){
                v.join();
            }
        }
        catch(InterruptedException e){
            System.out.println("Error!");
            MAINLOGGER.log(Level.SEVERE, "Main could not wait for threads to join!", e);
        }
        
        
        System.out.println("\n---Simulation ended---\n");

    }


    private static void resumeSimulation(){
        for(Vehicle v : vehicleArray){
            synchronized(v){
                try {
                    v.notifyAll();
                } catch (Exception e){
                    System.out.println("Error!");
                    BorderSimulation.CARLOGGER.log(Level.SEVERE, "Problems with notifyAll()!", e);
                }
            }
        }
    }


    private static void setTerminalsToTrue(){
        String text = "p1-T\np2-T\np3-T\nc1-T\nc2-T";

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(TERMINALS_FILE));
            writer.write(text);
            writer.close();
        }
        catch(Exception e){
            System.out.println("Error!");
            MAINLOGGER.log(Level.SEVERE, "Could not write to TERMINALS_FILE in setTerminalsToTrue!", e);
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


    private static void configureLogger(){
        try{
            Handler mainHandler    = new FileHandler(MAIN_LOG_FILE);
            Handler vehicleHandler = new FileHandler(VEHICLE_LOG_FILE);
            Handler carHandler     = new FileHandler(CAR_LOG_FILE);
            Handler busHandler     = new FileHandler(BUS_LOG_FILE);
            Handler truckHandler   = new FileHandler(TRUCK_LOG_FILE);
            
            mainHandler.setFormatter(new SimpleFormatter());
            vehicleHandler.setFormatter(new SimpleFormatter());
            carHandler.setFormatter(new SimpleFormatter());
            busHandler.setFormatter(new SimpleFormatter());
            truckHandler.setFormatter(new SimpleFormatter());

            MAINLOGGER.addHandler(mainHandler);
            VEHICLELOGGER.addHandler(vehicleHandler);
            CARLOGGER.addHandler(carHandler);
            BUSLOGGER.addHandler(busHandler);
            TRUCKLOGGER.addHandler(truckHandler);

            MAINLOGGER.setLevel(Level.ALL);
            VEHICLELOGGER.setLevel(Level.ALL);
            CARLOGGER.setLevel(Level.ALL);
            BUSLOGGER.setLevel(Level.ALL);
            TRUCKLOGGER.setLevel(Level.ALL);
            
        }
        catch(Exception e){
            System.out.println("Error!");
            MAINLOGGER.log(Level.SEVERE, "Exception during the creation of logger!", e);
        }
    }

}