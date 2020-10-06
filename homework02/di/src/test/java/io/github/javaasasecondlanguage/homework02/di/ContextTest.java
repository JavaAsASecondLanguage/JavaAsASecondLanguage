package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

public class ContextTest extends CommonTest {

    @Test
    void findBeanByClass() {
        var mockFactory = context.findBeanByClass(MockObject.class);
        Assertions.assertTrue(mockFactory != null);
        Assertions.assertTrue(mockFactory.get() != null);
        Assertions.assertTrue(mockFactory.get() instanceof MockObject);
        Assertions.assertTrue(HELLO_WORLD.equals(mockFactory.get().test()));
    }

    @Test
    void findBeanByClassAndQualifier() {
        var helloWorldFactory = context.findBeanByClassAndQualifier(HELLO_QUALIFIER);
        Assertions.assertTrue(helloWorldFactory != null);
        Assertions.assertTrue(helloWorldFactory.get() != null);
        Assertions.assertTrue(helloWorldFactory.get() instanceof String);
        Assertions.assertTrue(HELLO_WORLD.equals(helloWorldFactory.get()));
    }

    @Test
    void register() {
        var otherContext = new Context();
        Supplier<Exception> exceptionFactory = () -> {
            throw new NullPointerException();
        };
        otherContext.register(NullPointerException.class, exceptionFactory);
        var exceptionSupplier = otherContext.findBeanByClass(NullPointerException.class);
        Assertions.assertTrue(exceptionSupplier != null);
        Assertions.assertThrows(NullPointerException.class, () -> exceptionSupplier.get());
    }

    @Test
    void testRegister() {
        var otherContext = new Context();
        Supplier<Exception> exceptionFactory = () -> {
            throw new NullPointerException();
        };
        otherContext.register(EXCEPTION, exceptionFactory);
        var exceptionSupplier = otherContext.findBeanByClassAndQualifier(EXCEPTION);
        Assertions.assertTrue(exceptionSupplier != null);
        Assertions.assertThrows(NullPointerException.class, () -> exceptionSupplier.get());
    }
}