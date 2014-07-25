package example;

import io.sphere.sdk.utils.SphereInternalLogger;

public class Logging {
    private static class FooBar {

    }

    private static class BigComplexObjectWithExpensiveToString {

    }

    public void showLazyToString() {
        BigComplexObjectWithExpensiveToString object = new BigComplexObjectWithExpensiveToString();
        SphereInternalLogger.getLogger("myLogger.hierarchy").trace(() -> "failed doing xyz with " + object);
    }
}
