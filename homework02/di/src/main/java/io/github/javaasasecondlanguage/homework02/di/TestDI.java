package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.*;

public class TestDI {
    private Context context;
    @BeforeEach
    void setUp() {
        context = new Context();
    }

    @Test
    void duplicateClassesLabels(){
        int registeredValue1 = 123;
        int registeredValue2 = 456;
        String registeredQualifier = "aaa";
        assertThrows(KeyAlreadyExistsException.class, () -> context
            .register(registeredValue1, registeredQualifier)
            .register(registeredValue2, registeredQualifier));
    }

    @Test
    void duplicateClassesWithoutLabels(){
        int registeredValue1 = 123;
        int registeredValue2 = 456;
        assertThrows(KeyAlreadyExistsException.class, () -> context
                .register(registeredValue1)
                .register(registeredValue2));
    }

    @Test
    void sameClassesDiffLabels(){
        int registeredValue1 = 123;
        int registeredValue2 = 456;
        String registeredQualifier1 = "aaa";
        String registeredQualifier2 = "bbb";
        assertDoesNotThrow(() -> context
                .register(registeredValue1, registeredQualifier1)
                .register(registeredValue2, registeredQualifier2));
    }

    @Test
    void diffClassesSameLabels(){
        int registeredValue1 = 123;
        String registeredValue2 = "456";
        String registeredQualifier = "aaa";
        assertDoesNotThrow(() -> context
                .register(registeredValue1, registeredQualifier)
                .register(registeredValue2, registeredQualifier));
    }

    @Test
    void findTest() {
        int registeredValue1 = 123;
        int registeredValue2 = 456;
        String registeredQualifier1 = "aaa";
        String registeredQualifier2 = "bbb";
        context.register(registeredValue1, registeredQualifier1)
                .register(registeredValue2, registeredQualifier2);
        assertSame(registeredValue1,
                context.findDependency(Integer.class, registeredQualifier1));
        assertSame(registeredValue2,
                context.findDependency(Integer.class, registeredQualifier2));
    }

    @Test
    void findTestWithNullLabel() {
        int registeredValue1 = 123;
        int registeredValue2 = 456;
        String registeredQualifier1 = "aaa";
        String registeredQualifier2 = null;
        context.register(registeredValue1, registeredQualifier1)
                .register(registeredValue2, registeredQualifier2);
        assertSame(registeredValue1,
                context.findDependency(Integer.class, registeredQualifier1));
        assertSame(registeredValue2,
                context.findDependency(Integer.class, registeredQualifier2));
    }
}
