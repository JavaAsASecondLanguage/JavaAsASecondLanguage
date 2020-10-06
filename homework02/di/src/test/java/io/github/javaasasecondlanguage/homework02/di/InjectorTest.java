package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.exceptions.NotFoundBeanFactoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest extends CommonTest {

    @Test
    void inject() {
        var mockObject = Injector.inject(MockObject.class);
        Assertions.assertTrue(mockObject != null);
        Assertions.assertThrows(NotFoundBeanFactoryException.class, () -> Injector.inject(String.class));

        var mockСomposite = Injector.inject(CompositeMock.class);
        Assertions.assertTrue(mockСomposite != null);
        Assertions.assertTrue(mockСomposite.getMock() != null);
    }

    @Test
    void testInject() {
        var string = Injector.inject(HELLO_QUALIFIER);
        Assertions.assertTrue(string != null);
        Assertions.assertEquals(HELLO_WORLD, string);
    }
}