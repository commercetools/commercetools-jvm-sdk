package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class RetryOperationImpl extends Base implements RetryOperation {
    private static final Logger logger = LoggerFactory.getLogger(RetryOperation.class);

    protected abstract String getDescription();

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("description", getDescription()).build();
    }

    protected <P, R> void giveUpUsingThrowable(final RetryOperationContext<P, R> retryOperationContext, final Throwable throwable) {
        retryOperationContext.getResult().completeExceptionally(throwable);
    }

    protected <P, R> void shutdown(final RetryOperationContext<P, R> retryOperationContext) {
        try {
            retryOperationContext.getService().close();
        } catch (Exception e) {
            logger.error("Error occurred while closing service in retry strategy.", e);
        }
    }

    protected <P, R> void shutdownAndThrow(final RetryOperationContext<P, R> retryOperationContext, final Throwable throwable) {
        shutdown(retryOperationContext);
        giveUpUsingThrowable(retryOperationContext, throwable);
    }
}
