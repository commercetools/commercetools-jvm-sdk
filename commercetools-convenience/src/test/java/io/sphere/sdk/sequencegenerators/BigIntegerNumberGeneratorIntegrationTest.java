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
        final String key = randomKey();//this could be something like "orderNumber"
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(client(), key)
                        .build();
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(config);
        final BigInteger firstNumber = generator.getNextNumber().toCompletableFuture().join();
        assertThat(firstNumber).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void checkNumbersBiggerThanLongMax() {
        final BigInteger overSized = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.TEN);
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(client(), randomKey())
                        .initialValue(overSized)
                        .build();
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(config);
        final BigInteger firstNumber = generator.getNextNumber().toCompletableFuture().join();
        assertThat(firstNumber).isEqualTo(new BigInteger("9223372036854775817"));
    }

    @Test
    public void firstNumberCanBeGiven() throws Exception {
        final BigInteger initialCounterValue = new BigInteger("5001");
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(client(), randomKey())
                        .container(randomKey())
                        .initialValue(initialCounterValue)
                        .build();
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(config);
        final BigInteger firstNumber = generator.getNextNumber().toCompletableFuture().join();
        assertThat(firstNumber).isEqualTo(initialCounterValue);
    }

    @Test
    public void customObjectContainerAndKeyCanBeGiven() throws Exception {
        final String container = randomKey();
        final String key = randomKey();
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(client(), key)
                        .container(container)
                        .build();
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(config);
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
        final int firstNumber = 1;
        final int lastNumber = 20;
        final BigIntegerNumberGenerator generator = createGenerator();
        final List<CompletionStage<BigInteger>> completionStageNumbers = IntStream.range(firstNumber, lastNumber)
                .mapToObj(i -> generator.getNextNumber())
                .collect(Collectors.toList());
        final List<BigInteger> numbersGenerated = CompletableFutureUtils.listOfFuturesToFutureOfList(completionStageNumbers).toCompletableFuture().join();
        final List<BigInteger> expectedNumbers = IntStream.range(firstNumber, lastNumber).mapToObj(i -> BigInteger.valueOf(i)).collect(Collectors.toList());
        assertThat(numbersGenerated.containsAll(expectedNumbers)).isTrue();
    }

    private BigIntegerNumberGenerator createGenerator() {
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(client(), randomKey())
                        .container(randomKey())
                        .build();
        return CustomObjectBigIntegerNumberGenerator.of(config);
    }
}