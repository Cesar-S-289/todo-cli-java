package com.cattotech.todo.cli.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {

    public static LocalDate getDate(String givenDate) {

        if (givenDate.equalsIgnoreCase("none")) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(givenDate, formatter);
            return date;
        } catch (DateTimeParseException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException();
        }
    }
    
    public static String formatDate(LocalDate date) {
        
        // manage null case
        if (date == null) {
            return "none";
        }
        // format date and return it
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

}
