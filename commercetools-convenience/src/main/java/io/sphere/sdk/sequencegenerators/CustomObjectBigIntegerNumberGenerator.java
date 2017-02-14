package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.ConcurrentModificationException;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.errors.DuplicateFieldError;
import io.sphere.sdk.models.errors.ErrorResponse;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.CompletionStage;

/**
 Creates an incremental sequence of BigInteger numbers, storing the last used number in a CustomObject. It is thread safe even across multiple application nodes, by using optimistic concurrency control based on the version of the CustomObject.
 When there is a concurrency exception, it is automatically retries to generate the number (with a maximum number of retries configured in {@link CustomObjectBigIntegerNumberGeneratorConfig}).

 <h3 id="create-type">Create number sequence starting with 1</h3>

 <p>Execution example:</p>
 {@include.example io.sphere.sdk.sequencegenerators.BigIntegerNumberGeneratorIntegrationTest#firstNumberIsOne()}

 <h3 id="create-type">Create number sequence with a given initial value</h3>

 <p>It is possible to assign an initial value for the sequence</p>

 <p>Execution example:</p>
 {@include.example io.sphere.sdk.sequencegenerators.BigIntegerNumberGeneratorIntegrationTest#firstNumberCanBeGiven()}

 <h3 id="create-object-with-type">Create a sequence assigning the container and key for the custom object</h3>

 <p>Execution example:</p>
 {@include.example io.sphere.sdk.sequencegenerators.BigIntegerNumberGeneratorIntegrationTest#customObjectContainerAndKeyCanBeGiven()}

 */
public final class CustomObjectBigIntegerNumberGenerator extends Base implements BigIntegerNumberGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CustomObjectBigIntegerNumberGenerator.class);

    private final CustomObjectBigIntegerNumberGeneratorConfig config;

    private CustomObjectBigIntegerNumberGenerator(final CustomObjectBigIntegerNumberGeneratorConfig config) {
        this.config = config;
    }

    @Override
    public CompletionStage<BigInteger> getNextNumber() {
        final int timeToLive = config.getMaxRetryAttempts();
        final Throwable error = null;
        return tryGetNextNumber(timeToLive, error);
    }

    private CompletionStage<BigInteger> tryGetNextNumber(final int timeToLive, final Throwable throwable) {
        if (timeToLive > 0 && isRecoverableException(throwable)) {
            final CompletionStage<BigInteger> bigIntegerCompletionStage = incrementAndGetSequenceNumber();
            return CompletableFutureUtils.recoverWith(bigIntegerCompletionStage, (error) -> tryGetNextNumber(timeToLive - 1, error));
        } else {
            return CompletableFutureUtils.failed(throwable);
        }
    }

    private boolean isRecoverableException(final Throwable throwable) {
        if (throwable != null) {
            final Throwable cause = throwable.getCause();
            return cause instanceof ConcurrentModificationException
                    || (cause instanceof ErrorResponseException
                        && ((ErrorResponse) cause).getErrors().stream().anyMatch(e -> e.getCode().equals(DuplicateFieldError.CODE)));
        }
        return true;
    }

    private CompletionStage<BigInteger> incrementAndGetSequenceNumber() {
        return getLastUsedOrderNumber(config.getSphereClient()).thenCompose(oldCustomObject -> {
            final CustomObjectDraft<BigInteger> draft;
            if (oldCustomObject != null) {
                final long version = oldCustomObject.getVersion();
                final BigInteger nextOrderNumber = oldCustomObject.getValue().add(BigInteger.ONE);
                draft = CustomObjectDraft.ofVersionedUpsert(config.getContainer(), config.getKey(), nextOrderNumber, version, BigInteger.class);
            } else {
                final long initialVersionNumber = 0;
                draft = CustomObjectDraft.ofVersionedUpsert(config.getContainer(), config.getKey(), config.getInitialValue(), initialVersionNumber, BigInteger.class);
            }
            return setLastUsedOrderNumber(config.getSphereClient(), draft).thenApply(CustomObject::getValue);
        });
    }

    private CompletionStage<CustomObject<BigInteger>> getLastUsedOrderNumber(final SphereClient client) {
        return client.execute(CustomObjectByKeyGet.of(config.getContainer(), config.getKey(), BigInteger.class));
    }

    private CompletionStage<CustomObject<BigInteger>> setLastUsedOrderNumber(final SphereClient client, final CustomObjectDraft<BigInteger> draft) {
        return client.execute(CustomObjectUpsertCommand.of(draft));
    }

    public static CustomObjectBigIntegerNumberGenerator of(final CustomObjectBigIntegerNumberGeneratorConfig config) {
        return new CustomObjectBigIntegerNumberGenerator(config);
    }

}
