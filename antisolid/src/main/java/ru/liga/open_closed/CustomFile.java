package ru.liga.open_closed;

/**
 * Для соответствия принципу открытости-закрытости в этом классе нужно создать
 * абстрактный метод readFile(), а для каждого типа файла создать отдельный класс
 * и реализовать метод readFile() для конкретного типа файла
 */
public class CustomFile {

    private String type;

    public String getType() {
        return type;
    }

    public String readCsv() {
        return "csv";
    }

    public String readXlsx() {
        return "xlsx";
    }

    public String readTxt() {
        return "txt";
    }

    // Сюда будут добавляться новые методы обработки типов файлов
    // вместо создания нового класса - нарушение
}
