package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereAccessTokenSupplier;
import io.sphere.sdk.client.SphereApiConfig;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.errors.DuplicateFieldError;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.CompletableFutureUtils;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.sphere.sdk.http.HttpStatusCode.*;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@NotThreadSafe
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

    @Ignore
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


    @Test
    public void failWithNotFoundException() {
        final String container = randomKey();
        final String key = randomKey();

        final ErrorResponseHttpClient notFoundRequestClient = new ErrorResponseHttpClient(NOT_FOUND_404);
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(getSphereClient(notFoundRequestClient), key)
                        .container(container)
                        .build();
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(config);
        Throwable thrown = catchThrowable(() -> {
            generator.getNextNumber().toCompletableFuture().join();
        });
        assertThat(thrown).isNotNull();
        assertThat(notFoundRequestClient.getGetRequestsCount()).isEqualTo(1);
    }

    @Test
    public void tryToRecoverWithConflictException() {
        final String container = randomKey();
        final String key = randomKey();

        final ErrorResponseHttpClient concurrentRequestClient = new ErrorResponseHttpClient(CONFLICT_409);
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(getSphereClient(concurrentRequestClient), key)
                        .container(container)
                        .build();
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(config);
        catchThrowable(() -> { generator.getNextNumber().toCompletableFuture().join(); });
        assertThat(concurrentRequestClient.getGetRequestsCount()).isEqualTo(config.getMaxRetryAttempts());
    }

    @Test
    public void tryToRecoverWithDuplicateFieldException() {
        final String container = randomKey();
        final String key = randomKey();

        final ErrorResponseHttpClient concurrentRequestClient = new ErrorResponseHttpClient(BAD_REQUEST_400, DuplicateFieldError.CODE, "");
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(getSphereClient(concurrentRequestClient), key)
                        .container(container)
                        .build();
        final BigIntegerNumberGenerator generator = CustomObjectBigIntegerNumberGenerator.of(config);
        catchThrowable(() -> { generator.getNextNumber().toCompletableFuture().join(); });
        assertThat(concurrentRequestClient.getGetRequestsCount()).isEqualTo(config.getMaxRetryAttempts());
    }

    private SphereClient getSphereClient(final HttpClient httpClient) {
        return SphereClient.of(SphereApiConfig.of("projectKey"), httpClient, SphereAccessTokenSupplier.ofConstantToken("accessToken"));
    }

    private BigIntegerNumberGenerator createGenerator() {
        final CustomObjectBigIntegerNumberGeneratorConfig config =
                CustomObjectBigIntegerNumberGeneratorConfigBuilder.of(client(), randomKey())
                        .container(randomKey())
                        .build();
        return CustomObjectBigIntegerNumberGenerator.of(config);
    }

    private class ErrorResponseHttpClient implements HttpClient {
        private int getRequestsCount = 0;
        private int statusCode;
        private String errorCode;
        private String errorMessage;

        ErrorResponseHttpClient(final int statusCode) {
            this(statusCode, "", "");
        }

        ErrorResponseHttpClient(final int statusCode, final String errorCode, final String errorMessage) {
            this.statusCode = statusCode;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        @Override
        public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
            if (httpRequest.getHttpMethod() == HttpMethod.GET) {
                getRequestsCount++;
            }
            final String message = "{\n" +
                    "  \"statusCode\" : " + this.statusCode + ",\n" +
                    "  \"errors\" : [{ \n" +
                    "       \"code\" : \"" + this.errorCode + "\",\n" +
                    "       \"message\" : \"" + this.errorMessage + "\"\n" +
                    "  }]\n" +
                    "}";
            return CompletableFuture.completedFuture(HttpResponse.of(this.statusCode, message));
        }

        @Override
        public void close() {

        }

        public int getGetRequestsCount() {
            return getRequestsCount;
        }
    }
}
