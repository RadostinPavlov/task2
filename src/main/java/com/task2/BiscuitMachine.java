package com.task2;

import java.util.Scanner;

public class BiscuitMachine {


    private static final String BISCUIT_RUNNING = "Biscuit machine is already running!";
    private static final String START_BISCUIT = "Biscuit machine must be started first!";
    private static final String MACHINE_ON = "Turning machine ON!";
    private static final String MACHINE_PAUSE = "Pausing machine!";
    private static final String MACHINE_OFF = "Turning machine OFF!";
    private static final String WAITING_OVEN = "Waiting for oven to warm up!";
    private static final String INFO = "\nEnter command (ON, PAUSE, OFF, EXIT): ";
    private static final String EXITING = "Exiting the program!";
    private static final String INVALID_COMMAND = "Invalid command. Please enter ON, PAUSE, OFF, or EXIT.";
    private volatile MachineState currentState;
    private Oven oven;
    private Conveyor conveyor;
    private volatile boolean machineRunning;

    private volatile boolean isrunning;

    public BiscuitMachine() {
        currentState = MachineState.OFF;
        oven = new Oven();
        conveyor = new Conveyor();
        machineRunning = false;
        isrunning = false;
    }

    public synchronized void switchMachineState(MachineState newState) {
        if (newState != currentState) {
            currentState = newState;
            switch (newState) {
                case ON:
                    if (!isrunning) {
                        isrunning = true;
                        turnOn();
                    } else {
                        System.out.println(BISCUIT_RUNNING);
                    }
                    break;
                case PAUSE:
                    if (isrunning) {
                        pause();
                        isrunning = false;
                    } else {
                        System.out.println(START_BISCUIT);
                    }
                    break;
                case OFF:
                    if (isrunning) {
                        isrunning = false;
                        turnOff();
                    } else {
                        System.out.println(START_BISCUIT);
                    }
                    break;
            }
        }
    }

    private void turnOn() {
        System.out.println(MACHINE_ON);
        oven.turnOn();
        machineRunning = true;

        new Thread(() -> {
            while (!oven.isReady() && machineRunning) {
                System.out.println(WAITING_OVEN);
                sleep(1000);
            }
            conveyor.start();

            while (machineRunning) {
                if (currentState == MachineState.ON) {
                    conveyor.move();
                    oven.maintainTemperature();
                } else if (currentState == MachineState.PAUSE) {
                    conveyor.stop();
                    oven.maintainTemperature();
                }
                sleep(10000);
            }
            conveyor.stop();
        }).start();
    }

    private void pause() {
        System.out.println(MACHINE_PAUSE);
    }

    private void turnOff() {
        System.out.println(MACHINE_OFF);
        machineRunning = false;
        oven.turnOff();
        conveyor.stop();
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BiscuitMachine machine = new BiscuitMachine();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                System.out.println(INFO);
                String input = scanner.nextLine().toUpperCase();

                switch (input) {
                    case "ON":
                        machine.switchMachineState(MachineState.ON);
                        break;
                    case "PAUSE":
                        machine.switchMachineState(MachineState.PAUSE);
                        break;
                    case "OFF":
                        machine.switchMachineState(MachineState.OFF);
                        break;
                    case "EXIT":
                        machine.switchMachineState(MachineState.OFF);
                        System.out.println(EXITING);
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println(INVALID_COMMAND);
                        break;
                }
            }
        }).start();
    }
}
