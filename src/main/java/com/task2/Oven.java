package com.task2;

public class Oven {
    private static final int MIN_TEMP = 220;
    private static final int MAX_TEMP = 240;
    private static final String HEATING = "Oven is heating...";
    private static final String OVEN_TEMPERATURE = "Oven temperature: ";
    private static final String DEGREES = "°C";
    private static final String OVEN_TO_HOT = "Oven is too hot! Turning off heating element.";
    private static final String OVEN_COOLING_DOWN = "Oven is cooling down. Heating back on.";
    private static final String OVEN_TURN_OFF = "Oven is turned off.";
    private int currentTemp;
    private boolean isHeating;

    public Oven() {
        currentTemp = 0;
        isHeating = false;
    }

    public void turnOn() {
        System.out.println(HEATING);
        isHeating = true;
    }

    public boolean isReady() {
        if (currentTemp < MIN_TEMP) {
            currentTemp += 10;
            System.out.println(OVEN_TEMPERATURE + currentTemp + DEGREES);
            return false;
        } else {
            return true;
        }
    }

    public void maintainTemperature() {
        if (currentTemp >= MAX_TEMP) {
            System.out.println(OVEN_TO_HOT);
            isHeating = false;
        } else if (currentTemp <= MIN_TEMP && !isHeating) {
            System.out.println(OVEN_COOLING_DOWN);
            isHeating = true;
        }

        if (isHeating) {
            currentTemp += 5;
        } else {
            currentTemp -= 5;
        }
        System.out.println(OVEN_TEMPERATURE + currentTemp + DEGREES);
    }

    public void turnOff() {
        System.out.println(OVEN_TURN_OFF);
        currentTemp = 0;
        isHeating = false;
    }
}
