package io.github.javaasasecondlanguage.homework02.di;

import io.github.javaasasecondlanguage.homework02.di.exceptions.BeanAlreadyDefinedException;
import io.github.javaasasecondlanguage.homework02.di.utils.FinalObject;
import io.github.javaasasecondlanguage.homework02.di.utils.TestInterface;
import io.github.javaasasecondlanguage.homework02.di.utils.TestObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ContextTest {
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
    public void contextShouldRegisterSelfInContextRegistry() {
        var context = ContextRegistry.getContext("test_context");
        Assertions.assertEquals(ContextTest.context, context);
    }

    @Test
    public void contextShouldRegisterBeanWithQualifier() {
        var bean = new TestObject();

        context.register(bean, "qual");

        var registered = context.getRawBean(TestObject.class, "qual");
        Assertions.assertEquals(bean, registered);
    }

    @Test
    public void contextShouldRegisterBeanWithoutQualifier() {
        var bean = new TestObject();

        context.register(bean);

        var registered = context.getRawBean(TestObject.class, Context.DEFAULT_QUALIFIER);
        Assertions.assertEquals(bean, registered);
    }

    @Test
    public void contextWithDefaultConstructorShouldRegisterSelfInContextRegistry() {
        var defaultContext = new Context();
        var registeredContext = ContextRegistry.getContext(ContextRegistry.DEFAULT_CONTEXT_NAME);
        Assertions.assertEquals(defaultContext, registeredContext);
    }

    @Test
    public void contextShouldThrowExceptionWhenRegisterTwoSameBeans() {
        var bean1 = new TestObject();
        var bean2 = new TestObject();

        context.register(bean1);

        Assertions.assertThrows(BeanAlreadyDefinedException.class, () -> context.register(bean2));
    }

    @Test
    public void contextShouldThrowRegisterTwoBeansWithSameClassWithDifferentQualifiers() {
        var bean1 = new TestObject();
        var bean2 = new TestObject();

        context.register(bean1, "bean1");
        context.register(bean2, "bean2");

        var registeredBean1 = context.getRawBean(TestObject.class, "bean1");
        var registeredBean2 = context.getRawBean(TestObject.class, "bean2");

        Assertions.assertEquals(bean1, registeredBean1);
        Assertions.assertEquals(bean2, registeredBean2);
    }

    @Test
    public void contextShouldReturnProxyBeanForInterface() {
        var bean = new TestObject().setField(List.of(1, 2));

        context.register(bean);

        var proxy = context.getBean(TestInterface.class);

        Assertions.assertEquals(bean.getField(), proxy.getField());
        Assertions.assertNotEquals(bean, proxy);
    }

    @Test
    public void contextShouldReturnProxyBeanForClass() {
        var bean = new TestObject().setField(List.of(1, 2));

        context.register(bean);

        var proxy = context.getBean(TestObject.class);

        Assertions.assertEquals(bean.getField(), proxy.getField());
        Assertions.assertNotEquals(bean, proxy);
    }

    @Test
    public void contextShouldSameObjectForFinalClass() {
        var bean = new FinalObject().setField(List.of(1, 2));

        context.register(bean);

        var beanFromContext = context.getBean(FinalObject.class);

        Assertions.assertEquals(bean.getField(), beanFromContext.getField());
        Assertions.assertEquals(bean, beanFromContext);
    }
}
