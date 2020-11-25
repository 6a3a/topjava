package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        return StringUtils.hasText(s) ? LocalDate.parse(s) : null;
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
    }
}
