package io.sphere.sdk.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class TestCleaner {
    private static final SphereInternalLogger LOGGER = SphereInternalLogger.getLogger(TestCleaner.class);

    private final List<Runnable> cleanupFunctions = new ArrayList<>();

    private TestCleaner() {}

    private TestCleaner(List<Runnable> cleanupFunctions) {
        this.cleanupFunctions.addAll(cleanupFunctions);
    }

    public <T> T add(T target, Consumer<T> cleanupFunction) {
        add(() -> cleanupFunction.accept(target));

        return target;
    }

    public void add(Runnable cleanupFunction) {
        cleanupFunctions.add(cleanupFunction);
    }

    public void cleanup() {
        Collections.reverse(cleanupFunctions);

        cleanupFunctions.forEach(cleanupFunction -> {
            try {
                cleanupFunction.run();
            } catch (Exception e) {
                LOGGER.error(() -> "Error during cleanup!", e);
            }
        });

        cleanupFunctions.clear();
    }

    public static TestCleaner empty() {
        return new TestCleaner();
    }

    public static TestCleaner of(List<Runnable> cleanupFunctions) {
        return new TestCleaner(cleanupFunctions);
    }

    public static void withCleaner(Consumer<TestCleaner> fn) {
        TestCleaner cleaner = empty();

        try {
            fn.accept(cleaner);
        } finally {
            cleaner.cleanup();
        }
    }

}
