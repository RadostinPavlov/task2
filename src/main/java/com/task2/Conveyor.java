package com.task2;

public class Conveyor {

    private static final String CONVEYOR_START = "Conveyor belt is starting!";
    private static final String CONVEYOR_STOP = "Conveyor belt is stopping!";
    private static final String CONVEYOR_MOVING = "Conveyor belt is moving biscuits!";
    private static final String CONVEYOR_NOT_MOVING = "Conveyor belt is not moving!";
    private boolean isRunning;

    public Conveyor() {
        this.isRunning = false;
    }

    public void start() {
        System.out.println(CONVEYOR_START);
        isRunning = true;
    }

    public void stop() {
        if(isRunning) {
            System.out.println(CONVEYOR_STOP);
            isRunning = false;
        }
    }

    public void move() {
        if (isRunning) {
            System.out.println(CONVEYOR_MOVING);
        } else {
            System.out.println(CONVEYOR_NOT_MOVING);
        }
    }
}
