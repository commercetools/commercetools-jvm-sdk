package io.sphere.sdk.types;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.commands.TypeCreateCommand;
import io.sphere.sdk.types.commands.TypeDeleteCommand;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.*;

public class CustomTest extends IntegrationTest {
    private static Type type;

    public static void deleteType() {
        execute(TypeDeleteCommand.of(type));
        //TODO only if no refs exist
    }


    @BeforeClass
    public static void setup() {
        type = CreateTypeDemo.createType(client());
    }

    @AfterClass
    public static void cleanUp() {
        type = CreateTypeDemo.createType(client());
    }
}