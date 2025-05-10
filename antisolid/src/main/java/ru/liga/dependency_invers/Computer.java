package ru.liga.dependency_invers;

/**
 * Класс, который может реализовывать интерфейс Switchable
 */
public class Computer {

    public void turnOn() {
        System.out.println("Компьютер включен");
    }

    public void turnOff() {
        System.out.println("Компьютер выключен");
    }

    public void doSomeOperations() {
        System.out.println("Компьютер работает");
    }
}
