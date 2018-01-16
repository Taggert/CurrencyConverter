package com.currencyConverter.menu;
 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Predicate;

public interface InputOutput {
    String getString(String prompt);
 
    void put(Object object);
 
    default String getString(String prompt, int minLength, int maxLength) {
        String res = "";
        int length = 0;
        String errMsg = "";
        do {
            res = getString(errMsg + prompt);
            length = res.length();
            errMsg = "Wrong string length.\n";
 
        } while (length < minLength || length > maxLength);
 
        return res;
    }
 
    default String getString(String prompt, List<String> from) {
        String res = "";
        String errMsg = "";
        do {
            res = getString(errMsg + prompt);
            errMsg = "Wrong input.\n";
        } while (!from.contains(res));
 
        return res;
    }
 
    default String getString(String prompt, Predicate<String> predicate) {
        String res = "";
        String errMsg = "";
        do {
            res = getString(errMsg + prompt);
            errMsg = "Wrong input.\n";
        } while (!predicate.test(res));
 
        return res;
    }
 
    default Integer getInteger(String prompt) {
        Integer res = null;
        String errMsg = "";
        while (true) {
            try {
                String str = getString(errMsg + prompt);
                res = Integer.parseInt(str);
                return res;
            } catch (NumberFormatException e) {
                errMsg = "Not a number.\n";
            }
        }
    }
 
    default Integer getInteger(String prompt, int minValue, int maxValue) {
        Integer res = null;
        String errMsg = "";
        while (true) {
            try {
                String str = getString(errMsg + prompt);
                res = Integer.parseInt(str);
                if (res < minValue || res > maxValue) {
                    errMsg = "Number is out of range.\n";
                } else
                    return res;
            } catch (NumberFormatException e) {
                errMsg = "Not a number.\n";
            }
        }
    }
 
    default Integer getInteger(String prompt, Predicate<Integer> predicate) {
        Integer res = null;
        String errMsg = "";
        while (true) {
            try {
                String str = getString(errMsg + prompt);
                res = Integer.parseInt(str);
                if (!predicate.test(res)) {
                    errMsg = "Number is not in conditions.\n";
                } else
                    return res;
            } catch (NumberFormatException e) {
                errMsg = "Not a number.\n";
            }
        }
    }
 
    default LocalDate getDate(String prompt, String format) {
        LocalDate res = null;
        String errMsg = "";
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern(format);
        while (true) {
            try {
                String str = getString(errMsg + prompt);
                res = LocalDate.parse(str, dateformat);
                return res;
            } catch (DateTimeParseException e) {
                errMsg = "Wrong date.\n";
            }
        }
    }
 
    default LocalDate getDate(String prompt, String format, LocalDate fromInclusive, LocalDate toExclusive) {
        LocalDate res = null;
        String errMsg = "";
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern(format);
        while (true) {
            try {
                String str = getString(errMsg + prompt);
                res = LocalDate.parse(str, dateformat);
                if (res.isBefore(toExclusive) || !res.isBefore(fromInclusive)) {
                    errMsg = "Date is not in range";
                } else
                    return res;
            } catch (DateTimeParseException e) {
                errMsg = "Wrong date. ";
            }
        }
    }
 
}