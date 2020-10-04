package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DiTests {
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        Context.getDependencies().clear();
    }

    @Test
    void testRegistrationAndInjection() {
        String registeredValue = "asdf";
        context.register(registeredValue);
        String injectedValue = Injector.inject(String.class);
        assertEquals(registeredValue, injectedValue);
    }

    @Test
    void testRegistrationWithQualifierAndInjectionWithQualifier() {
        Charset registeredValue = StandardCharsets.UTF_8;
        String qualifier = "qualifier";
        context.register(registeredValue, qualifier);
        Charset injectedValue = Injector.inject(Charset.class, qualifier);
        assertEquals(registeredValue, injectedValue);
    }

    @Test
    void testRegistrationWithQualifierAndInjectionWithNoQualifier() {
        UUID registeredValue = UUID.randomUUID();
        String qualifier = "qualifier";
        context.register(registeredValue, qualifier);
        UUID injectedValue = Injector.inject(UUID.class, qualifier);
        assertEquals(registeredValue, injectedValue);
    }

    @Test
    void testInjectionWithNoRegistration() {
        TestInterface injectedValue = Injector.inject(TestInterface.class);
        assertNotNull(injectedValue);
        assertThrows(NullPointerException.class, injectedValue::method);
    }

    @Test
    void testInjectionWitnQualifierWithNoRegistration() {
        String qualifier = "qualifier";
        TestInterface injectedValue = Injector.inject(TestInterface.class, qualifier);
        assertNotNull(injectedValue);
        assertThrows(NullPointerException.class, injectedValue::method);
    }

    @Test
    void testInjectionWithDelayedRegistration() {
        TestInterface injectedValue = Injector.inject(TestInterface.class);
        assertNotNull(injectedValue);
        final int methodResult = 777;
        TestInterface registeredValue = () -> methodResult;
        context.register(registeredValue);
        assertEquals(injectedValue.method(), methodResult);
    }

    @Test
    void testInjectionWithQualifierWithDelayedRegistration() {
        String qualifier = "qualifier";
        TestInterface injectedValue = Injector.inject(TestInterface.class, qualifier);
        assertNotNull(injectedValue);
        final int methodResult = 777;
        TestInterface registeredValue = () -> methodResult;
        context.register(registeredValue, qualifier);
        assertEquals(injectedValue.method(), methodResult);
    }

    @Test
    void testInjectionWithNoQualifierWithDelayedRegistrationWithQualifier() {
        String qualifier = "qualifier";
        TestInterface injectedValue = Injector.inject(TestInterface.class);
        assertNotNull(injectedValue);
        final int methodResult = 777;
        TestInterface registeredValue = () -> methodResult;
        context.register(registeredValue, qualifier);
        assertEquals(injectedValue.method(), methodResult);
    }

    @Test
    void testSubclassInjection() {
        Integer registeredValue = 55;
        context.register(registeredValue);
        Number injectedValue = Injector.inject(Number.class);
        assertEquals(registeredValue, injectedValue);
    }

    @Test
    void testSubclassInjectionWithQualifier() {
        Integer registeredValue = 55;
        String qualifier = "qualifier";
        context.register(registeredValue, qualifier);
        Number injectedValue = Injector.inject(Number.class, qualifier);
        assertEquals(registeredValue, injectedValue);
    }

    @Test
    void testSubclassInjectionWithNoQualifierWithRegistrationWithQualifier() {
        Integer registeredValue = 55;
        String qualifier = "qualifier";
        context.register(registeredValue, qualifier);
        Number injectedValue = Injector.inject(Number.class);
        assertEquals(registeredValue, injectedValue);
    }

    @Test
    void testInjectionPriorityOnSubclasses() {
        TestClassA registeredValue1 = new TestClassA();
        context.register(registeredValue1);
        TestClassB registeredValue2 = new TestClassB();
        context.register(registeredValue2);
        TestClassB injectedValue = Injector.inject(TestClassB.class);
        assertEquals(registeredValue2, injectedValue);
    }

    @Test
    void testMultipleInjection() {
        Integer registeredValue1 = 55;
        String registeredValue2 = "aasdf";
        UUID registeredValue3 = UUID.randomUUID();
        context.register(registeredValue1);
        context.register(registeredValue2);
        context.register(registeredValue3);

        assertEquals(registeredValue1, Injector.inject(Integer.class));
        assertEquals(registeredValue2, Injector.inject(String.class));
        assertEquals(registeredValue3, Injector.inject(UUID.class));
    }

    interface TestInterface {
        int method();
    }

    static class TestClassA {}

    static class TestClassB extends TestClassA {}
}
