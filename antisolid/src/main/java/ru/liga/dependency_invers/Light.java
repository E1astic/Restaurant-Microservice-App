package ru.liga.dependency_invers;

/**
 * Класс, который может реализовывать интерфейс Switchable
 */
public class Light {

    private int lightLevel;

    public Light() {
        this.lightLevel = 0;
    }

    public void turnOn() {
        lightLevel = 100;
        System.out.println("Свет включен");
    }

    public void turnOff() {
        lightLevel = 0;
        System.out.println("Свет выключен");
    }

    public void setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
    }
}
