package ru.liga.lisk;

/**
 * Lada Granta не имеет функции круиз контроля, поэтому значение
 * {@link #cruiseControlIsOn} всегда равно false.
 * <br/>
 * Это нарушает контракт базового класса {@link Car}, в котором предполагается
 * именно переключение функции круиз контроля.
 * <p>Следовательно, данный класс-наследник не может заменить базовый класс,
 * так как поведение метода {@link #switchCruiseControl()} будет противоречить
 * ожиданиям функционала базового класса</p>
 */
public class Granta extends Car {

    @Override
    public void switchCruiseControl() {
        cruiseControlIsOn = false;
    }
}
