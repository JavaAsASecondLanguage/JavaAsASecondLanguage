package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class InjectorTest {
    @Test
    void shouldInjectLazyProxyForAnInterface() {
        var injectedRunnable = Injector.inject(Runnable.class);
        assertNotNull(injectedRunnable);
        var context = new Context();
        var registeredRunnable = mock(Runnable.class);
        context.register(registeredRunnable);
        verify(registeredRunnable, times(0)).run();
        injectedRunnable.run();
        injectedRunnable.run();
        verify(registeredRunnable, times(2)).run();
    }

    @Test
    void shouldInjectInObjectFromContext() {
        var context = new Context();
        context.register("abc", "def");
        var injected = Injector.inject(String.class, "def");
        assertEquals("abc", injected);
    }
}
