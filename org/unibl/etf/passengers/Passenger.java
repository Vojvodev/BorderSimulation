package org.unibl.etf.passengers;


import java.util.Random;
import java.lang.Cloneable;
import java.io.Serializable;
import java.lang.CloneNotSupportedException;

import org.unibl.etf.identifications.PassengerId;


public class Passenger implements Cloneable, Serializable{
	private static final long serialVersionUID = 1L;
	
	private PassengerId identification;
    private boolean luggage;
    private boolean forbiddenLuggage;
    private boolean driver;


    public Passenger(String name, boolean driver){
        this.identification = new PassengerId(name);
        this.driver = driver;

        Random rand = new Random();

        // Luggage
        int x = rand.nextInt(10);  // 0,1,2,3,4,5,6,7,8,9 - 70% chance
        if(x < 7){
            this.luggage = true;

            // Forbidden luggage
            int y = rand.nextInt(10); // 0,1,2,3,4,5,6,7,8,9 - 10% chance
            if(y == 0)
                forbiddenLuggage = true;
            else
                forbiddenLuggage = false;
        }
        else{
            this.luggage = false;
            this.forbiddenLuggage = false;
        }
            

    }



    public PassengerId getIdentification(){
        return this.identification;
    }

    public boolean isDriver(){
        return this.driver;
    }

    public boolean hasForbiddenLuggage(){
        return this.forbiddenLuggage;
    }


    public Object clone() throws CloneNotSupportedException{
        Passenger t = (Passenger) super.clone();
        t.identification = (PassengerId)identification.clone();

        return t;
    }

    
    @Override
    public String toString() {
    	return identification.toString() + ( (forbiddenLuggage == true) ? "-with forbidden luggage" : "" );
    }
    
    // Can add some additional attributes like if he is handicapped or something
}