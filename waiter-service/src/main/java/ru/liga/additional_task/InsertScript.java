package ru.liga.additional_task;

import com.github.javafaker.Faker;

import java.sql.*;
import java.util.Locale;

public class InsertScript {

    private static String URL = "jdbc:postgresql://localhost:5433/waiter_service";
    private static String USER_NAME = "postgres";
    private static String PASSWORD = "postgres";
    private static int BATCH_SIZE = 50_000;


    public static void main(String[] args) {

        String sql = "insert into waiter_account (name, employment_date, sex) values (?,?,?)";
        Faker faker = new Faker(new Locale("ru"));
        long start = System.currentTimeMillis();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            connection.setAutoCommit(false);
            for (int i = 0; i <= 19_850_000; i++) {
                if (i % 1000 == 0) {
                    statement.setString(1, "Иванов Иван");
                } else {
                    statement.setString(1, faker.name().fullName());
                }
                statement.setTimestamp(2, new Timestamp(2025, 4, 2, 10,10,10, 10));
                statement.setString(3, faker.demographic().sex());

                statement.addBatch();
                if (i % BATCH_SIZE == 0) {
                    statement.executeBatch();
                    connection.commit();
                    System.out.println("Батч вставлен успешно! " + "Значение счетчика: " + i);
                }
            }
            statement.executeBatch();
            connection.commit();

            long end = System.currentTimeMillis();
            System.out.println("Бд успешно заполнено");
            System.out.println("Время выполнения: " + (end - start));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
