package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.BeforeEach;

import java.util.function.Supplier;

public class CommonTest {
    public static final String HELLO_QUALIFIER = "helloWorld";
    public static final String HELLO_WORLD = "Hello World!";
    public static final String EXCEPTION = "exception";

    protected Context context;

    class MockObject {

        public String test() {
            return HELLO_WORLD;
        }
    }

    class CompositeMock {
        private MockObject mock = Injector.inject(MockObject.class);

        public MockObject getMock() {
            return mock;
        }

        void print() {
            this.getMock().test();
        }
    }

    @BeforeEach
    void setUp() {
        context = new Context();
        addBeanFactoryToContext();
    }

    private void addBeanFactoryToContext() {
        Supplier<CompositeMock> compositeFactory = () -> new CompositeMock();
        context.register(CompositeMock.class, compositeFactory);

        Supplier<MockObject> testFactory = () -> new CommonTest.MockObject();
        context.register(CommonTest.MockObject.class, testFactory);

        Supplier<String> stringSupplier = () -> HELLO_WORLD;
        context.register(HELLO_QUALIFIER, stringSupplier);
    }
}
