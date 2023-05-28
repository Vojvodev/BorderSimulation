package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.HashSet;
import java.lang.Thread;

public class Vehicle extends Thread{
    protected static final String SERIALIZATION_FILE  = "org/unibl/etf/evidentations/NAUGHTY_LIST.binary";
    protected static final String POLICE_EVIDENTATION = "org/unibl/etf/evidentations/POLICIJSKA_KONTROLA.txt";
    protected static final String BORDER_EVIDENTATION = "org/unibl/etf/evidentations/CARINSKA_KONTROLA.txt";

    protected String p1, p2, p3, c1, c2;

    private static int counter = 0;

    protected HashSet<Passenger> passengers;
    protected int numOfPeople;
    protected int id;


    public Vehicle(int capacity){
        Random rand = new Random();

        this.numOfPeople = rand.nextInt(1, capacity + 1);
        this.passengers = new HashSet<Passenger>();

        this.id = counter++;
    }



    // protected synchronized String getLock(String what){}
    // protected synchronized void setLock(String what, String TorF){}


}