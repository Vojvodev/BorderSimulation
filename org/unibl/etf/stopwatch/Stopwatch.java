package org.unibl.etf.stopwatch;


import java.util.Date;
import java.util.logging.Level;

import org.unibl.etf.BorderSimulation;
import org.unibl.etf.GUI.Frame1;


public class Stopwatch extends Thread {
    private long startTime;
    private boolean running = false;


    public synchronized void startStopwatch() {
        startTime = new Date().getTime();
        running = true;
    }

    
    public synchronized void stopStopwatch() {
        running = false;
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
        Frame1.timer.setText(Long.toString(getElapsedTime()));
            try {
                sleep(1000);   
            } catch (Exception e) {
                System.out.println("Error!");
                BorderSimulation.MAINLOGGER.log(Level.SEVERE, "Problems with sleep() in stopWatch!", e);
            }
        }
    }
    
}