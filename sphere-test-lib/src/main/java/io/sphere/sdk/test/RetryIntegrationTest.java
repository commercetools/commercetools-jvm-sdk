package io.sphere.sdk.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;

public class RetryIntegrationTest implements TestRule {
    private final int maxRetries;
    private final int baseMillisBetweenRetries;
    private final Logger logger;

    public RetryIntegrationTest(final int maxRetries, final int maxMillisBetweenRetries, final Logger logger) {
        this.maxRetries = maxRetries;
        this.baseMillisBetweenRetries = maxMillisBetweenRetries / maxRetries;
        this.logger = logger;
    }

    @Override
    public Statement apply(final Statement test, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable lastThrowable = null;
                for (int attempt = 0; attempt <= maxRetries; attempt++) {
                    try {
                        if (lastThrowable != null) {
                            sleepBeforeRetry(attempt, description.getMethodName());
                        }
                        test.evaluate();
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        lastThrowable = e;
                        break;
                    } catch (Throwable t) {
                        lastThrowable = t;
                    }
                }
                throw lastThrowable;
            }
        };
    }

    private void sleepBeforeRetry(final int attempt, final String methodName) throws InterruptedException {
        final int sleepDuration = attempt * baseMillisBetweenRetries;
        logger.info(String.format("%s failed, retry in %d ms (%d/%d)",
                methodName,
                sleepDuration,
                attempt,
                maxRetries));
        Thread.sleep(sleepDuration);
    }
}