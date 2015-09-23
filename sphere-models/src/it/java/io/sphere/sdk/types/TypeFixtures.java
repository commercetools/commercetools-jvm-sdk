package io.sphere.sdk.types;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.types.commands.TypeCreateCommand;
import io.sphere.sdk.types.commands.TypeDeleteCommand;

import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;

public class TypeFixtures {
    public static void withUpdateableType(final TestClient client, final UnaryOperator<Type> operator) {
        final FieldDefinition stringFieldDefinition =
                FieldDefinition.of(StringType.of(), "string-field-name", en("label"), false, TextInputHint.SINGLE_LINE);
        final String typeKey =randomKey();
        final TypeDraft typeDraft = TypeDraftBuilder.of(typeKey, en("name of the custom type"), singleton(Category.typeId()))
                .description(en("description"))
                .fieldDefinitions(singletonList(stringFieldDefinition))
                .build();
        final Type type = client.execute(TypeCreateCommand.of(typeDraft));
        final Type updatedType = operator.apply(type);
        client.execute(TypeDeleteCommand.of(updatedType));
    }
}
