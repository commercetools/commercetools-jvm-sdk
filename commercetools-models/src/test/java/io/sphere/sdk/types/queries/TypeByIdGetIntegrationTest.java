package io.sphere.sdk.types.queries;

import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.TypeFixtures;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TypeByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        TypeFixtures.withUpdateableType(client(), type -> {
            final Type fetchedType = client().executeBlocking(TypeByIdGet.of(type.getId()));
            assertThat(fetchedType.getId()).isEqualTo(type.getId());
            return type;
        });
    }
}