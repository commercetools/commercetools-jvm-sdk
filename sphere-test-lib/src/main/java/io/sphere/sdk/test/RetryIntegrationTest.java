package io.sphere.sdk.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;

public class RetryIntegrationTest implements TestRule {
    private final int maxAttempts;
    private final Logger logger;

    public RetryIntegrationTest(final int maxAttempts, final Logger logger) {
        this.maxAttempts = maxAttempts;
        this.logger = logger;
    }

    @Override
    public Statement apply(final Statement test, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable lastThrowable = null;
                for (int attempt = 0; attempt < maxAttempts; attempt++) {
                    try {
                        Thread.sleep(200);
                        test.evaluate();
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Throwable t) {
                        logger.info(String.format("%s failed %d/%d", description.getMethodName(), attempt + 1, maxAttempts));
                        lastThrowable = t;
                    }
                }
                throw lastThrowable;
            }
        };
    }
}