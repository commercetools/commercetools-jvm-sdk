package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.CompletionStage;

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
        if (timeToLive > 0){
            final CompletionStage<BigInteger> bigIntegerCompletionStage = incrementAndGetSequenceNumber();
            return CompletableFutureUtils.recoverWith(bigIntegerCompletionStage, (error) -> tryGetNextNumber(timeToLive - 1, error));
        }else{
            return CompletableFutureUtils.failed(throwable);
        }
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
