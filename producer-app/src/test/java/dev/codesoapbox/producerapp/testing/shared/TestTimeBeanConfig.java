package dev.codesoapbox.producerapp.testing.shared;

import jakarta.validation.ClockProvider;
import org.springframework.boot.autoconfigure.validation.ValidationConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.LocalDateTime;

public class TestTimeBeanConfig {

    public static final LocalDateTime FIXED_DATE_TIME = LocalDateTime.of(
            2025, 1, 1, 0, 0);

    @Bean
    public Clock fixedClock() {
        return FixedClock.at(FIXED_DATE_TIME);
    }

    /**
     * Freezes time for bean validation, so that annotations like {@code @FutureOrPresent} don't break in the future.
     */
    @Bean
    public ValidationConfigurationCustomizer validationConfigurationCustomizer(Clock clock) {
        FixedClockProvider fixedClockProvider = new FixedClockProvider(clock);
        return c -> c.clockProvider(fixedClockProvider);
    }

    // For Jakarta validation
    private record FixedClockProvider(Clock clock) implements ClockProvider {

        @Override
        public Clock getClock() {
            return clock;
        }
    }
}