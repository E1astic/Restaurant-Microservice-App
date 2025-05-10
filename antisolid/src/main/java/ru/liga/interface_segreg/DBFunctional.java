package ru.liga.interface_segreg;

/**
 * Анти принцип разделения интерфейсов
 * <p>Данный интерфейс объединяет в себе много разного функционала.
 * При его реализации может случиться такое, что классу не будет нужен
 * метод {@link #writeData(String)} или {@link #readAllData()}</p>
 * Будет лучше разделить функционал этого интерфейса на несколько разных интерфейсов
 */
public interface DBFunctional {

    void openConnection();

    void readAllData();

    void writeData(String data);

    void closeConnection();
}
