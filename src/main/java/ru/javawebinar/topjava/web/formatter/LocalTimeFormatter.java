package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        return StringUtils.hasText(s) ? LocalTime.parse(s) : null;
    }

    @Override
    public String print(LocalTime localTime, Locale locale) {
        return DateTimeFormatter.ISO_LOCAL_TIME.format(localTime);
    }
}
