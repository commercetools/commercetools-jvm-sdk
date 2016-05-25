package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

abstract class RetryOperationImpl extends Base implements RetryOperation {
    private static final Logger logger = LoggerFactory.getLogger(RetryOperation.class);

    protected abstract String getDescription();

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("description", getDescription()).build();
    }

    @Nullable
    protected <P, R> RetryOutput<P, R> giveUpUsingThrowable(final RetryOperationContext<P, R> retryOperationContext, final Throwable throwable) {
        retryOperationContext.getResult().completeExceptionally(throwable);
        return null;
    }

    @Nullable
    protected <P, R> RetryOutput<P, R> shutdown(final RetryOperationContext<P, R> retryOperationContext) {
        try {
            retryOperationContext.getService().close();
        } catch (Exception e) {
            logger.error("Error occurred while closing service in retry strategy.", e);
        }
        return null;
    }

    @Nullable
    protected <P, R> RetryOutput<P, R> shutdownAndThrow(final RetryOperationContext<P, R> retryOperationContext, final Throwable throwable) {
        shutdown(retryOperationContext);
        return giveUpUsingThrowable(retryOperationContext, throwable);
    }
}
