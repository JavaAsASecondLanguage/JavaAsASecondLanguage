package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.exceptions.NotFoundBeanFactoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InjectorTest extends CommonTest {

    @Test
    void inject() {
        var mockObject = Injector.inject(MockObject.class);
        Assertions.assertTrue(mockObject != null);
        Assertions.assertThrows(NotFoundBeanFactoryException.class,
            () -> Injector.inject(String.class));

        var compositeMock = Injector.inject(CompositeMock.class);
        Assertions.assertTrue(compositeMock != null);
        Assertions.assertTrue(compositeMock.getMock() != null);
    }

    @Test
    void testInject() {
        var string = Injector.inject(HELLO_QUALIFIER);
        Assertions.assertTrue(string != null);
        Assertions.assertEquals(HELLO_WORLD, string);
    }
}