package io.sphere.sdk.sequencegenerators;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.models.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.concurrent.CompletionStage;

public final class CustomObjectBigIntegerNumberGenerator extends Base implements BigIntegerNumberGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CustomObjectBigIntegerNumberGenerator.class);

    private final BigInteger initialValue;
    private final String container;
    private final String key;
    private final SphereClient sphereClient;

    private CustomObjectBigIntegerNumberGenerator(final BigInteger initialValue, final String container, final String key, final SphereClient sphereClient) {
        this.initialValue = initialValue;
        this.container = container;
        this.key = key;
        this.sphereClient = sphereClient;
    }

    @Override
    public CompletionStage<BigInteger> getNextNumber() {
        return getLastUsedOrderNumber(sphereClient).thenCompose(oldCustomObject -> {
            final CustomObjectDraft<BigInteger> draft;
            if (oldCustomObject != null) {
                final long version = oldCustomObject.getVersion();
                final BigInteger nextOrderNumber = oldCustomObject.getValue().add(BigInteger.ONE);
                draft = CustomObjectDraft.ofVersionedUpsert(container, key, nextOrderNumber, version, BigInteger.class);
            } else {
                final long initialVersionNumber = 0;
                draft = CustomObjectDraft.ofVersionedUpsert(container, key, initialValue, initialVersionNumber, BigInteger.class);
            }
            return setLastUsedOrderNumber(sphereClient, draft).thenApply(CustomObject::getValue);
        });
    }

    private CompletionStage<CustomObject<BigInteger>> getLastUsedOrderNumber(final SphereClient client) {
        return client.execute(CustomObjectByKeyGet.of(container, key, BigInteger.class));
    }

    private CompletionStage<CustomObject<BigInteger>> setLastUsedOrderNumber(final SphereClient client, final CustomObjectDraft<BigInteger> draft) {
        return client.execute(CustomObjectUpsertCommand.of(draft));
    }

    public static CustomObjectBigIntegerNumberGenerator of(final SphereClient sphereClient, final String container, final String key) {
        return new CustomObjectBigIntegerNumberGenerator(BigInteger.ONE, container, key, sphereClient);
    }

    public static CustomObjectBigIntegerNumberGenerator of(final SphereClient sphereClient, final String container, final String key, final BigInteger initialValue) {
        return new CustomObjectBigIntegerNumberGenerator(initialValue, container, key, sphereClient);
    }
}
