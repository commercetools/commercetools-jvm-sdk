package io.sphere.sdk.types.query;

import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.TypeFixtures;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TypeByIdGetTest extends IntegrationTest {
    @Test
    public void execution() {
        TypeFixtures.withUpdateableType(client(), type -> {
            final Type fetchedType = execute(TypeByIdGet.of(type.getId()));
            assertThat(fetchedType.getId()).isEqualTo(type.getId());
            return type;
        });
    }
}