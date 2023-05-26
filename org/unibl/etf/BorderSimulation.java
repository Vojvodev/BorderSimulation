package org.unibl.etf;


import org.unibl.etf.identifications.*;
import org.unibl.etf.passengers.*;
import org.unibl.etf.vehicles.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class BorderSimulation{
    private static ArrayList<Vehicle> vehicleArray = new ArrayList<Vehicle>();


    public static void main(String args[]){

        // Filling up the queue with Vehicles
        if(createQueue() == false){
            // throw Exception
        }

        // Simulation start
        System.out.println("---Simulation started---\n");
        for(Vehicle v : vehicleArray){
            v.start();
        }

        

    }



    private static boolean createQueue(){
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