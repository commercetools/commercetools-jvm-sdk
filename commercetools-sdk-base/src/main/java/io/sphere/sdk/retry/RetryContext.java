package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * Immutable data container which contains information about the first and latest failed attempt to do somethings.
 *
 */
public interface RetryContext {
    /**
     * The timestamp when the first error occurred.
     * @return time
     */
    Instant getStartTimestamp();

    /**
     * The first error that occurred for one service method execution.
     * @return first error
     */
    Throwable getFirstError();

    /**
     * The latest error that occurred for one service method execution. For the first attempt this returns the same as {@link #getFirstError()}.
     * @return first error
     */
    Throwable getLatestError();

    /**
     * Gets the parameter or parameter object for the original service method execution.
     *
     * @return null or parameter
     */
    @Nullable
    Object getFirstParameter();

    /**
     * Gets the parameter or parameter object for the latest service method execution. Is identical to {@link #getFirstParameter()} for the first attempt.
     *
     * @return null or parameter
     */
    @Nullable
    Object getLatestParameter();

    /**
     * Counts the failed attempts so far for one original service method call. For the first error the value is 1.
     *
     * @return attempt
     */
    Long getAttempt();
}
