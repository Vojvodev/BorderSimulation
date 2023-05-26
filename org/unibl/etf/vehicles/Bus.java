package org.unibl.etf.vehicles;


import org.unibl.etf.passengers.Passenger;


import java.util.HashSet;


public class Bus extends Vehicle{
    private static final int CAPACITY = 52; 

    
    public Bus(){
        super(CAPACITY);
        
        // Each bus has 1 driver or if there's more than 1 passenger - there are 2 drivers
        if(numOfPeople == 1){                                   // Only 1 in the bus and he is the driver
            Passenger p = new Passenger("N.N. driver", true);
            this.passengers.add(p);
        }
        else if(numOfPeople == 2){                              // Only the 2 drivers
            Passenger p1 = new Passenger("N.N. driver1", true);
            this.passengers.add(p1);

            Passenger p2 = new Passenger("N.N. driver2", true);
            this.passengers.add(p2);
        }
        else{                                                   // 2 drivers and the rest
            Passenger p1 = new Passenger("N.N. driver1", true);
            this.passengers.add(p1);

            Passenger p2 = new Passenger("N.N. driver2", true);
            this.passengers.add(p2);

            for(int i = 0; i < numOfPeople - 2; i++){
                Passenger px = new Passenger("N.N.", false);
                this.passengers.add(px);
            }
        }

    }


    public void run(){
        
    }


    @Override
    public String toString(){
        return "Bus with " + numOfPeople + " passengers.";
    }
}