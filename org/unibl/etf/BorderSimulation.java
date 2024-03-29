package org.unibl.etf;


import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

import org.unibl.etf.GUI.Frame1;
import org.unibl.etf.stopwatch.Stopwatch;
import org.unibl.etf.vehicles.Bus;
import org.unibl.etf.vehicles.Car;
import org.unibl.etf.vehicles.Truck;
import org.unibl.etf.vehicles.Vehicle;


public class BorderSimulation{
    protected static final String TERMINALS_FILE   = "terminals.txt";
    protected static final String MAIN_LOG_FILE    = "logs" + File.separator + "mainLogs.log";
    protected static final String VEHICLE_LOG_FILE = "logs" + File.separator + "vehicleLogs.log";
    protected static final String CAR_LOG_FILE     = "logs" + File.separator + "carLogs.log";
    protected static final String BUS_LOG_FILE     = "logs" + File.separator + "busLogs.log";
    protected static final String TRUCK_LOG_FILE   = "logs" + File.separator + "truckLogs.log";
    protected static final String GUI_LOG_FILE     = "logs" + File.separator + "guiLogs.log";


    public static ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();       // Doesn't change after the threads start
    public static Stack<Vehicle> vehicleStack = new Stack<Vehicle>();               // Does change
    public static int processedVehiclesCounter = 1;
    
    public static final Logger MAINLOGGER   = Logger.getLogger(BorderSimulation.class.getName());
    public static final Logger VEHICLELOGGER = Logger.getLogger(Vehicle.class.getName());
    public static final Logger CARLOGGER     = Logger.getLogger(Car.class.getName());
    public static final Logger BUSLOGGER     = Logger.getLogger(Bus.class.getName());
    public static final Logger TRUCKLOGGER   = Logger.getLogger(Truck.class.getName());
    public static final Logger GUILOGGER     = Logger.getLogger(Frame1.class.getName());

    public static boolean pause = false;


    public static void main(String args[]){
        Stopwatch sWatch = new Stopwatch();
        
        
        // Configuring loggers
        configureLogger();

        // Erase evidentation files
        clearEvidentations();

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

        
        // Summon the GUI
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        
        // Start the counter
        sWatch.setDaemon(true);
        sWatch.start();

        Vehicle.printFirstFiveVehicles();
        
        for(Vehicle v : vehicleArray){
            v.start();
        }
        

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
        JOptionPane.showMessageDialog(null, "Vehicles processed: " + (processedVehiclesCounter - 1) + " \nSimulation time: " + sWatch.getElapsedTime(), "Simulation ended!", 1);
        sWatch.stopStopwatch();
        
    }


    private static void clearEvidentations(){
        File border = new File(Vehicle.BORDER_EVIDENTATION);
        File police = new File(Vehicle.POLICE_EVIDENTATION);
        File serialization = new File(Vehicle.SERIALIZATION_FILE);
        
        if(border.exists())
            border.delete();

        if(police.exists())
            police.delete();

        if(serialization.exists())
            serialization.delete();
    
    }


    public static void resumeSimulation(){
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
            Handler guiHandler     = new FileHandler(GUI_LOG_FILE);
            

            mainHandler.setFormatter(new SimpleFormatter());
            vehicleHandler.setFormatter(new SimpleFormatter());
            carHandler.setFormatter(new SimpleFormatter());
            busHandler.setFormatter(new SimpleFormatter());
            truckHandler.setFormatter(new SimpleFormatter());
            guiHandler.setFormatter(new SimpleFormatter());


            MAINLOGGER.addHandler(mainHandler);
            VEHICLELOGGER.addHandler(vehicleHandler);
            CARLOGGER.addHandler(carHandler);
            BUSLOGGER.addHandler(busHandler);
            TRUCKLOGGER.addHandler(truckHandler);
            GUILOGGER.addHandler(guiHandler);


            MAINLOGGER.setLevel(Level.ALL);
            VEHICLELOGGER.setLevel(Level.ALL);
            CARLOGGER.setLevel(Level.ALL);
            BUSLOGGER.setLevel(Level.ALL);
            TRUCKLOGGER.setLevel(Level.ALL);
            GUILOGGER.setLevel(Level.ALL);

        }
        catch(Exception e){
            System.out.println("Error!");
            MAINLOGGER.log(Level.SEVERE, "Exception during the creation of logger!", e);
        }
    }

}