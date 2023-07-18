package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;

import java.util.Random;
import java.util.HashSet;
import java.lang.Thread;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


public abstract class Vehicle extends Thread{
    protected static final String SERIALIZATION_FILE  = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "evidentations" + File.separator + "NAUGHTY_LIST.binary";
    protected static final String POLICE_EVIDENTATION = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "evidentations" + File.separator + "POLICIJSKA_KONTROLA.txt";
    protected static final String BORDER_EVIDENTATION = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "evidentations" + File.separator + "CARINSKA_KONTROLA.txt";
    protected static final String TERMINALS_FILE      = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "terminals.txt";

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
        }
        catch(Exception e){
            System.out.println("Could not read from TERMINALS_FILE in checkTerminals!");
        }
        
    }


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
        }
        catch(Exception e){
            System.out.println("Could not read from TERMINALS_FILE in changeTerminal!");
        }
        
        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(TERMINALS_FILE));
            writer.write(text);
            writer.close();
        }
        catch(Exception e){
            System.out.println("Could not write to TERMINALS_FILE in changeTerminal!");
        }
        
    }

}