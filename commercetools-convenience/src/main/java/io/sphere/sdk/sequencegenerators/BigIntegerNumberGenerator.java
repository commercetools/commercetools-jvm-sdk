package io.sphere.sdk.sequencegenerators;

import java.math.BigInteger;
import java.util.concurrent.CompletionStage;

/**
 *
 * Incremental number generator.
 */
public interface BigIntegerNumberGenerator {

    /**
     * This generator ensures that all calls (even in parallel) generates a different number.
     * @return nextNumber
     */
    CompletionStage<BigInteger> getNextNumber();
}
