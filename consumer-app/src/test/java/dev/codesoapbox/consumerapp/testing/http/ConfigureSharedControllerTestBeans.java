package dev.codesoapbox.consumerapp.testing.http;

import dev.codesoapbox.consumerapp.greetings.application.GetGreetingUseCase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        // Common
        // ... More configs common to all controllers (e.g. pagination DTO mappers) here

        // Specific
        // ... More controller-specific configs here
})
@MockitoBean(types = {
        GetGreetingUseCase.class,
        // ... More mocks here
})
public @interface ConfigureSharedControllerTestBeans {
}
