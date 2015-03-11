package io.sphere.sdk.utils;

@FunctionalInterface
public interface WithCleanerFunction {
    void run(TestCleaner cleaner);
}
