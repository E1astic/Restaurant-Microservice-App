package ru.liga.single_resp;

/**
 * <h6>Анти принцип единственной ответственности</h6>
 * Очень универсальный класс мобильного телефона.
 * Позволяет настраивать и яркоть, и звук, и заряд, и память.
 * Было бы лучше для каждого функционала создать отдельный класс
 * со своими методами по детальной настройке
 */
public class Phone {

    int light;
    int sound;
    int battery;
    int memory;

    public Phone(int light, int sound, int battery, int memory) {
        this.light = light;
        this.sound = sound;
        this.battery = battery;
        this.memory = memory;
    }

    void setLight(int light) {
        this.light = light;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }
}
