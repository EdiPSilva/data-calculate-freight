package br.com.java.datacalculatefreight.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fuctions {

    public static LocalDateTime getCreateDate() {
        final String stringDate = "2023-03-20 20:00";
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(stringDate, formatter);
    }
}
