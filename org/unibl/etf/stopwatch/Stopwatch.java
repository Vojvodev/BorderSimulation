package org.unibl.etf.stopwatch;

import org.unibl.etf.GUI.Frame1;
import java.util.Date;


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
            }
        }
    }
    
}