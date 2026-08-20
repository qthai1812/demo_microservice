package com.example.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class DateTimeFormartter {


    Map<Long, Function<Instant, String>> strategyMap = new LinkedHashMap<>();


    public DateTimeFormartter() {
        strategyMap.put(60L, this::formartInseconds);         // Dưới 1 phút
        strategyMap.put(3600L, this::formartInMinutes);       // Dưới 1 giờ
        strategyMap.put(86400L, this::formartInHours);        // Dưới 1 ngày
        strategyMap.put(Long.MAX_VALUE, this::formartInDay);
    }

    public String formartInseconds(Instant instant){
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());
        return elapseSeconds + " seconds ago";
    }
    public String formartInMinutes(Instant instant){
        long elapseMinutes = ChronoUnit.MINUTES.between(instant, Instant.now());
        return elapseMinutes + " minutes ago";
    }
    public String formartInHours(Instant instant){
        long elapseHours = ChronoUnit.HOURS.between(instant, Instant.now());
        return elapseHours + " hours ago";
    }
    public String formartInDay(Instant instant){
        long elapseDays = ChronoUnit.DAYS.between(instant, Instant.now());
        return elapseDays + " days ago";
    }

    public String formart(Instant instant){
        long elapseSeconds = ChronoUnit.SECONDS.between(instant, Instant.now());

        var strategy = strategyMap.entrySet()
                .stream()
                .filter(entry -> elapseSeconds < entry.getKey())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chiến lược phù hợp!"));

        return strategy.getValue().apply(instant);
    }
}