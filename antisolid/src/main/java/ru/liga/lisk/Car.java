package ru.liga.lisk;

/**
 * Класс, описывающий автомобиль с функциями включения и выключения,
 * а также переключения режима работы кондиционера и круиз-контроля
 */
public class Car {

    protected boolean conditionerIsOn;
    protected boolean cruiseControlIsOn;

    public Car() {
        this.conditionerIsOn = false;
        this.cruiseControlIsOn = false;
    }

    public void start() {
        System.out.println("Car started");
    }

    public void stop() {
        System.out.println("Car stopped");
    }

    public void switchConditioner() {
        if (conditionerIsOn) {
            conditionerIsOn = false;
        } else {
            conditionerIsOn = true;
        }
    }

    public void switchCruiseControl() {
        if (cruiseControlIsOn) {
            cruiseControlIsOn = false;
        } else {
            cruiseControlIsOn = true;
        }
    }
}
