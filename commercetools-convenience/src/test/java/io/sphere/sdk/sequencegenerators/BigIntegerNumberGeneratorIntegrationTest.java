package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class BigIntegerNumberGeneratorIntegrationTest extends IntegrationTest {

    @Test
    public void increasesNumberWithNextCall() throws Exception {
        final BigIntegerNumberGenerator generator = createGenerator();
        final BigInteger firstNumber = generator.getNextNumber().toCompletableFuture().join();
        final BigInteger secondNumber = generator.getNextNumber().toCompletableFuture().join();
        assertThat(secondNumber).isGreaterThan(firstNumber);
    }

    @Test
    public void firstNumberIsOne() throws Exception {
        final BigIntegerNumberGenerator generator = createGenerator();
        final BigInteger firstNumber = generator.getNextNumber().toCompletableFuture().join();
        assertThat(firstNumber).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void firstNumberCanBeGiven() throws Exception {
        final BigInteger initialCounterValue = new BigInteger("5001");
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(client(), randomKey(), randomKey(), initialCounterValue);
        final BigInteger firstNumber = generator.getNextNumber().toCompletableFuture().join();
        assertThat(firstNumber).isEqualTo(initialCounterValue);
    }

    @Test
    public void customObjectContainerAndKeyCanBeGiven() throws Exception {
        final String container = randomKey();
        final String key = randomKey();
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(client(), container, key);
        final BigInteger firstNumber = generator.getNextNumber().toCompletableFuture().join();
        assertThat(firstNumber).isEqualTo(BigInteger.ONE);
        final CustomObject<BigInteger> customObject = client().executeBlocking(CustomObjectByKeyGet.of(container, key, BigInteger.class));
        assertThat(customObject.getValue()).isEqualTo(BigInteger.ONE);
        generator.getNextNumber().toCompletableFuture().join();
        final CustomObject<BigInteger> customObject2 = client().executeBlocking(CustomObjectByKeyGet.of(container, key, BigInteger.class));
        assertThat(customObject2.getValue()).isEqualTo(BigInteger.valueOf(2));
    }

    @Test
    public void concurrentUsageTest() throws Exception {
        final BigIntegerNumberGenerator generator = createGenerator();
        final List<CompletionStage<BigInteger>> completionStages = IntStream.range(1, 20)
                .mapToObj(i -> generator.getNextNumber())
                .collect(Collectors.toList());
        final List<BigInteger> numbers = CompletableFutureUtils.listOfFuturesToFutureOfList(completionStages).toCompletableFuture().join();

        throw new RuntimeException();
    }

    private BigIntegerNumberGenerator createGenerator() {
        return CustomObjectBigIntegerNumberGenerator.of(client(), randomKey(), randomKey());
    }
}