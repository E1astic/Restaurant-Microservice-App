package ru.liga.dependency_invers;

/**
 * Класс-переключатель нарушает принцип инверсии нависимостей,
 * так как зависит от конкретной реализации {@link Light}.
 * <br/>
 * Для применения класса {@link Switch} к реализации {@link Computer}
 * потребуется заменить поле типа Light на поле типа Computer, либо
 * создать отдельный класс SwitchComputer
 *
 * <p>Для решения проблемы нужно будет создать интерфейс Switchable, и реализовать его
 * в классах {@link Light} и {@link Computer}, а в классе {@link Switch}
 * сделать поле типа Switchable, чтобы была зависимость от абстракции, а не конкретной реализации</p>
 */
public class Switch {

    private Light light;

    public Switch(Light light) {
        this.light = light;
    }

    public void switchOn() {
        light.turnOn();
    }

    public void switchOff() {
        light.turnOff();
    }
}
