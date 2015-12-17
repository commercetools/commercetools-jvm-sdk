package io.sphere.sdk.client;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;

import java.util.concurrent.TimeUnit;

public class BlockingClientCreationDemo {
    public static BlockingSphereClient createBlockingClient(final SphereClient sphereClient) {
        return BlockingSphereClient.of(sphereClient, 500, TimeUnit.MILLISECONDS);
    }
}
