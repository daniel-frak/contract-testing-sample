package dev.codesoapbox.consumerapp.testing.queues;

import dev.codesoapbox.consumerapp.infrastructure.config.SqsBeanConfig;
import dev.codesoapbox.consumerapp.acknowledgments.config.AcknowledgmentsSqsListenerBeanConfig;
import dev.codesoapbox.consumerapp.acknowledgments.application.AcceptAcknowledgmentUseCase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
        SqsBeanConfig.class,
        AcknowledgmentsSqsListenerBeanConfig.class
})
@MockitoBean(types = {
        AcceptAcknowledgmentUseCase.class
})
public @interface ConfigureSharedSqsTestBeans {
}
