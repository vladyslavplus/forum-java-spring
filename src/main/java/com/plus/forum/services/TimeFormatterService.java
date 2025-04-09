package com.plus.forum.services;

import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class TimeFormatterService {
    private static final ZoneId KYIV_ZONE = ZoneId.of("Europe/Kyiv");

    public String formatTimeAgo(LocalDateTime creationDate) {
        Duration duration = Duration.between(creationDate, LocalDateTime.now(KYIV_ZONE));
        long minutes = duration.toMinutes();

        if (minutes < 60) {
            return minutes + " " + pluralize(minutes, "minute", "minutes") + " ago";
        } else if (minutes < 1440) {
            long hours = minutes / 60;
            return hours + " " + pluralize(hours, "hour", "hours") + " ago";
        } else {
            long days = minutes / 1440;
            return days + " " + pluralize(days, "day", "days") + " ago";
        }
    }

    private String pluralize(long count, String singular, String plural) {
        return count == 1 ? singular : plural;
    }
}
