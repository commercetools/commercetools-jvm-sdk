package io.sphere.sdk.sequencegenerators;

import java.math.BigInteger;
import java.util.concurrent.CompletionStage;

public interface BigIntegerNumberGenerator {
    CompletionStage<BigInteger> getNextNumber();
}
