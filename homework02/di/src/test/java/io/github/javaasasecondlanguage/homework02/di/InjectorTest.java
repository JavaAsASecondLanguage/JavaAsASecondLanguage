package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.exceptions.AmbigiousBeanQueryException;
import io.github.javaasasecondlanguage.homework02.di.exceptions.BeanNotFoundException;
import io.github.javaasasecondlanguage.homework02.di.utils.ExtendedTestObject;
import io.github.javaasasecondlanguage.homework02.di.utils.TestInterface;
import io.github.javaasasecondlanguage.homework02.di.utils.TestObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InjectorTest {
    private static Context context;

    @BeforeEach
    public void initContext() {
        context = new Context("test_context");
    }

    @AfterEach
    public void clearContext() {
        ContextRegistry.clearContext();
    }

    @Test
    public void injectByQualifierShouldReturnBean() {
        var testBean = new TestObject().setField(List.of(1, 2, 3));
        context.register(testBean, "testBean");

        var injectedBean = Injector.inject(TestObject.class, "testBean");

        Assertions.assertEquals(testBean.getField(), injectedBean.getField());
    }

    @Test
    public void injectByClassShouldReturnBean() {
        var testBean = new TestObject().setField(List.of(1, 2, 3));
        context.register(testBean, "testBean");

        var injectedBean = Injector.inject(TestObject.class);

        Assertions.assertEquals(testBean.getField(), injectedBean.getField());
    }

    @Test
    public void injectBeansWithTwoPossibleCandidateShouldThrowException() {
        var testBean1 = new TestObject().setField(List.of(1, 2, 3));
        var testBean2 = new ExtendedTestObject().setField(List.of(1, 2, 3));
        context.register(testBean1, "testBean1");
        context.register(testBean2, "testBean2");

        Assertions.assertThrows(AmbigiousBeanQueryException.class,
            () -> Injector.inject(TestObject.class).getField());
    }

    @Test
    public void injectBeansShouldWorkWithSubclasses() {
        var testBean1 = new TestObject().setField(List.of(1, 2, 3));
        var testBean2 = new ExtendedTestObject().setField(List.of(1, 2, 4));
        context.register(testBean1, "testBean1");
        context.register(testBean2, "testBean2");

        var injectedBean2 = Injector.inject(ExtendedTestObject.class);

        Assertions.assertEquals(testBean2.getField(), injectedBean2.getField());
    }

    @Test
    public void injectBeansWithTwoPossibleCandidateShouldWorkWhenUseQualifier() {
        var testBean1 = new TestObject().setField(List.of(1, 2, 3));
        var testBean2 = new TestObject().setField(List.of(1, 2, 4));
        context.register(testBean1, "testBean1");
        context.register(testBean2, "testBean2");

        var injectedBean1 = Injector.inject(TestObject.class, "testBean1");
        var injectedBean2 = Injector.inject(TestObject.class, "testBean2");

        Assertions.assertEquals(testBean1.getField(), injectedBean1.getField());
        Assertions.assertEquals(testBean2.getField(), injectedBean2.getField());
    }

    @Test
    public void injectBeansShouldWorkWithInterface() {
        var testBean = new TestObject().setField(List.of(1, 2, 3));
        context.register(testBean);

        var injectedBean = Injector.inject(TestInterface.class);

        Assertions.assertEquals(testBean.getField(), injectedBean.getField());
    }

    @Test
    public void injectBeansWithoutQualifierShouldReturnBeanWithAnyQualifier() {
        var testBean = new TestObject().setField(List.of(1, 2, 3));
        context.register(testBean, "testBean");

        var injectedBean = Injector.inject(TestInterface.class);
        var injectedBean2 = Injector.inject(TestInterface.class, "testBean");

        Assertions.assertEquals(testBean.getField(), injectedBean.getField());
        Assertions.assertEquals(testBean.getField(), injectedBean2.getField());
    }

    @Test
    public void injectUknownBeanShouldThowException() {
        var injectedBean1 = Injector.inject(TestInterface.class);
        var injectedBean2 = Injector.inject(TestInterface.class, "qual");

        Assertions.assertThrows(BeanNotFoundException.class, () -> injectedBean1.getField());
        Assertions.assertThrows(BeanNotFoundException.class, () -> injectedBean2.getField());
    }
}
