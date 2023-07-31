package org.unibl.etf.identifications;


import java.util.Random;

import java.lang.Cloneable;
import java.io.Serializable;
import java.lang.CloneNotSupportedException;


public class PassengerId implements Cloneable, Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
    private boolean fakeId;

    public PassengerId(String name){
        this.name = name;

        Random rand = new Random();
        int x = rand.nextInt(100);      // 0, 1, 2, ... , 99  - 3% chance

        if(x < 3){
            this.fakeId = true;
        }
        else{
            this.fakeId = false;
        }
    }


    public boolean isFakeId(){
        return this.fakeId;
    }


    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    
    @Override
    public String toString() {
    	return name + ((fakeId==true) ? "-with fake ID" : "");
    }
}