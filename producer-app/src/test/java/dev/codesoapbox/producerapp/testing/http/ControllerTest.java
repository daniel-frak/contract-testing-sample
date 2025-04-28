package dev.codesoapbox.producerapp.testing.http;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@WebMvcTest
@ConfigureSharedControllerTestBeans
public @interface ControllerTest {
}
