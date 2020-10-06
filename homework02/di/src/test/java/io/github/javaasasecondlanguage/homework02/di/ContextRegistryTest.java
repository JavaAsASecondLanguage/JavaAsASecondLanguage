package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.exceptions.UnknownContextException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContextRegistryTest {
    @AfterEach
    public void clearContext() {
        ContextRegistry.clearContext();
    }

    @Test
    public void registryShouldReturnFirstRegisteredContextByDefault() {
        var context = new Context("test_context");
        new Context();

        var registryContext = ContextRegistry.getContext();

        Assertions.assertEquals(context, registryContext);
    }

    @Test
    public void registryShouldSwitchContext() {
        var context1 = new Context("context1");
        var context2 = new Context("context2");

        var registryContext1 = ContextRegistry.getContext();

        Assertions.assertEquals(context1, registryContext1);

        ContextRegistry.setDefaultContext("context2");
        var registryContext2 = ContextRegistry.getContext();

        Assertions.assertEquals(context2, registryContext2);
    }

    @Test
    public void registryShouldSwitchContextInThreadsSeparately() throws InterruptedException {
        final var context1 = new Context("context1");
        final var context2 = new Context("context2");

        ContextRegistry.setDefaultContext("context1");

        final Context[] contextHolder = new Context[]{null};
        var thread = new Thread(() -> {
            ContextRegistry.setDefaultContext("context2");
            contextHolder[0] = ContextRegistry.getContext();
        });
        thread.start();
        thread.join();

        var registryContext1 = ContextRegistry.getContext();
        Assertions.assertEquals(context1, registryContext1);
        Assertions.assertEquals(context2, contextHolder[0]);
    }

    @Test
    public void registryShouldReturnFirstDefinedContextInNewThread() throws InterruptedException {
        final var context1 = new Context("context1");
        final var context2 = new Context("context2");

        ContextRegistry.setDefaultContext("context2");

        final Context[] contextHolder = new Context[]{null};
        var thread = new Thread(() -> {
            contextHolder[0] = ContextRegistry.getContext();
        });
        thread.start();
        thread.join();

        Assertions.assertEquals(context1, contextHolder[0]);
    }

    @Test
    public void shouldThrowExceptionOnTryGetUnknownContext() {
        Assertions.assertThrows(UnknownContextException.class,
            () -> ContextRegistry.getContext("smth"));
    }

    @Test
    public void shouldThrowExceptionOnTryToAddContextWithAlreadyDefinedName() {
        String uniqName = "uniqName";
        new Context(uniqName);
        Assertions.assertThrows(RuntimeException.class, () -> new Context(uniqName),
                String.format("Context with name %s already defined", uniqName));
    }

    @Test
    public void shouldThrowExceptionOnTryToGetContextIfNotExists() {
        Assertions.assertThrows(RuntimeException.class,
            () -> ContextRegistry.getContext(), "Context not defined");
        new Context("test");
        Assertions.assertThrows(UnknownContextException.class,
            () -> ContextRegistry.getContext("test2"));
        Assertions.assertThrows(UnknownContextException.class,
            () -> ContextRegistry.setDefaultContext("test2"));
    }

}
