package ru.liga.open_closed;

import java.util.List;

public class CustomFileReader {

    private List<CustomFile> files;

    public CustomFileReader(List<CustomFile> files) {
        this.files = files;
    }

    /**
     * Такой подход нарушает принцип открытости-закрытости.
     * При каждом добавлении нового типа файла придется вносить
     * изменения в существующий код, добавлять новые if'ы
     */
    public void readAllFiles() {

        for (CustomFile file : files) {
            String type = file.getType();
            if (type.equals("csv")) {
                file.readCsv();
            } else if (type.equals("xlsx")) {
                file.readXlsx();
            } else if (type.equals("txt")) {
                file.readTxt();
            }
            // каждый раз сюда будут добавляться новые if'ы
        }
    }
}
