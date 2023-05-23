package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.TreeSet;


public class Vehicle{
    protected TreeSet<Passenger> passengers;


    public Vehicle(){
        passengers = new TreeSet<Passenger>();
    }
}