package org.unibl.etf.stopwatch;

import java.util.Date;


public class Stopwatch extends Thread {
    private long startTime;
    private boolean running = false;


    public synchronized void startStopwatch() {
        startTime = new Date().getTime();
        running = true;
    }


    public synchronized long getElapsedTime() {
        if (running) {
            return (long)(new Date().getTime() - startTime) / 1000;
        } else {
            return 0;
        }
    }


    @Override
    public void run() {
        startStopwatch();
        while (running) {
        System.out.println(getElapsedTime());                           // TODO: write time in some corner
            try {
                sleep(500);   
            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
    }
    
}