package dev.codesoapbox.producerapp.testing.http;

import dev.codesoapbox.producerapp.greetings.application.GetGreetingUseCase;
import dev.codesoapbox.producerapp.greetings.config.GreetControllerBeanConfig;
import dev.codesoapbox.producerapp.testing.shared.TestTimeBeanConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Motivation for the class:
 * <p>
 * While creating an application context for controller tests does not take a long time, it can lead to the context
 * cache filling up and evicting other, more expensive contexts (such as those for testing repositories).
 * <p>
 * Thus, making all controller tests share a single application context should protect against cache eviction slowing
 * down the tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        // Common
        TestTimeBeanConfig.class,
        // ... More configs common to all controllers (e.g. pagination DTO mappers) here

        // Specific
        GreetControllerBeanConfig.class
        // ... More controller-specific configs here
})
@MockitoBean(types = {
        GetGreetingUseCase.class
        // ... More mocks here
})
public @interface ConfigureSharedControllerTestBeans {
}
