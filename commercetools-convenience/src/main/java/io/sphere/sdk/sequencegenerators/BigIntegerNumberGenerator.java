package io.sphere.sdk.sequencegenerators;

import java.math.BigInteger;
import java.util.concurrent.CompletionStage;

/**
 Defines how to implement an incremental number generator

 */

public interface BigIntegerNumberGenerator {
    CompletionStage<BigInteger> getNextNumber();
}
