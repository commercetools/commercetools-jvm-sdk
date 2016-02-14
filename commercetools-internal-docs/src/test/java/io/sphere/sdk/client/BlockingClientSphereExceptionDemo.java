package io.sphere.sdk.client;

import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.models.SphereException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class BlockingClientSphereExceptionDemo {
    public static void demo(final BlockingSphereClient client) {
        final CartCreateCommand sphereRequest = CartCreateCommand.of(CartDraft.of(null));

        final Throwable throwable = catchThrowable(() -> client.executeBlocking(sphereRequest));

        assertThat(throwable)
                .isInstanceOf(ErrorResponseException.class)
                .isInstanceOf(SphereException.class);
    }
}
