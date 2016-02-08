package example;

import io.sphere.sdk.utils.SphereInternalLogger;

import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;

public final class LoggingExample {
    //replace topic with the related endpoint path segment, like categories or product-types.
    private static final SphereInternalLogger LOGGER = getLogger("topic.objects");

    public void showLazyToString() {
        BigComplexObjectWithExpensiveToString object = new BigComplexObjectWithExpensiveToString();
        //object.toString will only be called if trace is enabled
        LOGGER.trace(() -> "failed doing xyz with " + object);
    }
}
