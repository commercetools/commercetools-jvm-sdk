package io.sphere.sdk.customobjects.migrations;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectDraft;
import io.sphere.sdk.customobjects.commands.CustomObjectUpsertCommand;
import io.sphere.sdk.customobjects.migrations.version3.*;
import io.sphere.sdk.customobjects.queries.CustomObjectByKeyGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectsMigrationsIntegrationTest extends IntegrationTest {


    private static final String CONTAINER = CustomObjectsMigrationsIntegrationTest.class.getSimpleName();

    @Test
    public void addingAField() throws Exception {
        final String key = "addingAField";
        final CustomObjectDraft<io.sphere.sdk.customobjects.migrations.version1.Xyz> draft = CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, new io.sphere.sdk.customobjects.migrations.version1.Xyz("foo"), io.sphere.sdk.customobjects.migrations.version1.Xyz.class);
        final CustomObject<io.sphere.sdk.customobjects.migrations.version1.Xyz> customObject = client().executeBlocking(CustomObjectUpsertCommand.of(draft));

        final CustomObject<io.sphere.sdk.customobjects.migrations.version2.Xyz> xyz2CustomObject = client().executeBlocking(CustomObjectByKeyGet.of(CONTAINER, key, io.sphere.sdk.customobjects.migrations.version2.Xyz.class));
        assertThat(xyz2CustomObject.getValue().getBar()).isNull();

        final CustomObjectUpsertCommand<io.sphere.sdk.customobjects.migrations.version2.Xyz> upsertCommand =
                CustomObjectUpsertCommand.of(CustomObjectDraft.ofVersionedUpdate(xyz2CustomObject, new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", "bar"), io.sphere.sdk.customobjects.migrations.version2.Xyz.class));

        assertThat(client().executeBlocking(upsertCommand).getValue().getBar()).isEqualTo("bar");
    }

    @Test
    public void removingAField() throws Exception {
        final String key = "removingAField";
        final CustomObjectUpsertCommand<io.sphere.sdk.customobjects.migrations.version2.Xyz> upsertCommand =
                CustomObjectUpsertCommand.of(CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", "bar"), io.sphere.sdk.customobjects.migrations.version2.Xyz.class));
        client().executeBlocking(upsertCommand);
        final CustomObject<Xyz> xyz3CustomObject = client().executeBlocking(CustomObjectByKeyGet.of(CONTAINER, key, Xyz.class));
        assertThat(xyz3CustomObject.getValue().getBar()).isEqualTo("bar");
    }

    @Test
    public void optionalExample() throws Exception {
        final io.sphere.sdk.customobjects.migrations.version2.Xyz xyz = new io.sphere.sdk.customobjects.migrations.version2.Xyz("foo", "bar");
        final String bar = Optional.ofNullable(xyz.getBar()).orElse("default value");
    }

    @Test
    public void migration() throws Exception {
        final String key = "migration";
        final CustomObjectDraft<io.sphere.sdk.customobjects.migrations.version1.Uvw> draft = CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, new io.sphere.sdk.customobjects.migrations.version1.Uvw("a&b", "anotherField"), io.sphere.sdk.customobjects.migrations.version1.Uvw.class);
        final CustomObject<io.sphere.sdk.customobjects.migrations.version1.Uvw> objectSchema1 = client().executeBlocking(CustomObjectUpsertCommand.of(draft));
        assertThat(objectSchema1.getValue().getFoo()).isEqualTo("a&b");

        final CustomObject<Uvw> uvwCustomObjectSchema1 = client().executeBlocking(CustomObjectByKeyGet.of(CONTAINER, key, Uvw.class));
        assertThat(uvwCustomObjectSchema1.getValue()).isInstanceOf(UvwSchemaVersion1.class);
        assertThat(uvwCustomObjectSchema1.getValue().getFoo()).isEqualTo(new Foo("a", "b"));

        final CustomObjectDraft<Uvw> draftSchema2 = CustomObjectDraft.ofUnversionedUpsert(CONTAINER, key, uvwCustomObjectSchema1.getValue(), Uvw.class);
        final CustomObject<Uvw> schema2Object = client().executeBlocking(CustomObjectUpsertCommand.of(draftSchema2));
        assertThat(schema2Object.getValue()).isInstanceOf(UvwSchemaVersion2.class);
        assertThat(schema2Object.getValue().getFoo()).isEqualTo(new Foo("a", "b"));
    }

    @Test
    public void exampleForMigrationCall() throws Exception {
        final CustomObjectByKeyGet<Uvw> fetch = CustomObjectByKeyGet.of("container", "key", Uvw.class);
    }
}
