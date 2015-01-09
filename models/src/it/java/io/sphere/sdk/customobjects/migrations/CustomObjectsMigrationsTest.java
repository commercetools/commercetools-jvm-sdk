package io.sphere.sdk.customobjects.migrations;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.migrations.version3.Xyz;
import io.sphere.sdk.customobjects.queries.CustomObjectFetchByKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class CustomObjectsMigrationsTest extends IntegrationTest {


    private static final String CONTAINER = CustomObjectsMigrationsTest.class.getSimpleName();

    @Test
    public void addingAField() throws Exception {
        final String key = "addingAField";
        final CustomObjectDraft<io.sphere.sdk.customobjects.migrations.version1.Xyz> draft = CustomObjectDraft.of(CONTAINER, key, new io.sphere.sdk.customobjects.migrations.version1.Xyz("foo"), io.sphere.sdk.customobjects.migrations.version1.Xyz.customObjectTypeReference());
        final CustomObject<io.sphere.sdk.customobjects.migrations.version1.Xyz> customObject = execute(CustomObjectUpsertCommand.of(draft));

        final CustomObject<io.sphere.sdk.customobjects.migrations.version2.Xyz> xyz2CustomObject = execute(CustomObjectFetchByKey.of(CONTAINER, key, io.sphere.sdk.customobjects.migrations.version2.Xyz.customObjectTypeReference())).get();
        assertThat(xyz2CustomObject.getValue().getBar()).isAbsent();

        final CustomObjectUpsertCommand<io.sphere.sdk.customobjects.migrations.version2.Xyz> upsertCommand =
                CustomObjectUpsertCommand.of(CustomObjectDraft.ofVersionedDraft(xyz2CustomObject, new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", Optional.of("bar")), io.sphere.sdk.customobjects.migrations.version2.Xyz.customObjectTypeReference()));

        assertThat(execute(upsertCommand).getValue().getBar()).isPresentAs("bar");
    }

    @Test
    public void removingAField() throws Exception {
        final String key = "removingAField";
        final CustomObjectUpsertCommand<io.sphere.sdk.customobjects.migrations.version2.Xyz> upsertCommand =
                CustomObjectUpsertCommand.of(CustomObjectDraft.of(CONTAINER, key, new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", Optional.of("bar")), io.sphere.sdk.customobjects.migrations.version2.Xyz.customObjectTypeReference()));

        final CustomObject<Xyz> xyz3CustomObject = execute(CustomObjectFetchByKey.of(CONTAINER, key, Xyz.customObjectTypeReference())).get();
        assertThat(xyz3CustomObject.getValue().getBar()).isPresentAs("bar");
    }

    @Test
    public void optionalExample() throws Exception {
        final io.sphere.sdk.customobjects.migrations.version2.Xyz xyz = new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", Optional.of("bar"));
        final String bar = xyz.getBar().orElse("default value");
    }
}
