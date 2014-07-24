package example;

import io.sphere.sdk.utils.Log;

public class Logging {
    private static class FooBar {

    }

    private static class BigComplexObjectWithExpensiveToString {

    }

    public void showObjectToString() {
        FooBar fooBar = new FooBar();
        //you can directly put an Object into the logger, if the level debug is enabled, it will call fooBar.toString()
        Log.debug(fooBar);
        Log.debug("xyz happened");//you still can provide Strings for logging
    }

    public void showLazyToString() {
        BigComplexObjectWithExpensiveToString object = new BigComplexObjectWithExpensiveToString();
        Log.trace(() -> "failed doing xyz with " + object);
    }
}
