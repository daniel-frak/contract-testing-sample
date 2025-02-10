package dev.codesoapbox.producerapp.testing.shared;

import java.time.*;

public class FixedClock {

    public static Clock at(LocalDate localDate) {
        ZoneId zoneId = ZoneId.of("UTC");
        return Clock.fixed(localDate.atStartOfDay(zoneId).toInstant(), zoneId);
    }

    public static Clock at(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.of("UTC");
        return Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), zoneId);
    }
}
