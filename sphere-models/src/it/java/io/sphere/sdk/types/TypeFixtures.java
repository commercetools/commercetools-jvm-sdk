package io.sphere.sdk.types;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.commands.TypeCreateCommand;
import io.sphere.sdk.types.commands.TypeDeleteCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.stream.Collectors.toList;

public class TypeFixtures {

    public static final String ENUM_FIELD_NAME = "enum-field-name";
    public static final String LOCALIZED_ENUM_FIELD_NAME = "localized-enum-field-name";
    public static final Set<String> TYPE_IDS = new HashSet<>(asList(Category.resourceTypeId(), Customer.resourceTypeId(), Cart.resourceTypeId(), Order.resourceTypeId(), LineItem.resourceTypeId(), CustomLineItem.resourceTypeId()));
    public static final String STRING_FIELD_NAME = "string-field-name";

    public static void withUpdateableType(final TestClient client, final UnaryOperator<Type> operator) {
        final FieldDefinition stringFieldDefinition =
                FieldDefinition.of(StringType.of(), STRING_FIELD_NAME, en("label"), false, TextInputHint.SINGLE_LINE);
        final List<EnumValue> enumValues = asList(EnumValue.of("key1", "label1"), EnumValue.of("key2", "label2"));
        final FieldDefinition enumFieldDefinition =
                FieldDefinition.of(EnumType.of(enumValues), ENUM_FIELD_NAME, en("enum label"), false, TextInputHint.SINGLE_LINE);
        final List<LocalizedEnumValue> localizedEnumValues = asList("1", "2").stream()
                .map(s -> LocalizedEnumValue.of("key" + s, en("label " + s)))
                .collect(toList());
        final FieldDefinition localizedEnumFieldDefinition =
                FieldDefinition.of(LocalizedEnumType.of(localizedEnumValues), LOCALIZED_ENUM_FIELD_NAME, en("localized enum label"), false, TextInputHint.SINGLE_LINE);
        final String typeKey = randomKey();
        final TypeDraft typeDraft = TypeDraftBuilder.of(typeKey, en("name of the custom type"), TYPE_IDS)
                .description(en("description"))
                .fieldDefinitions(asList(stringFieldDefinition, enumFieldDefinition, localizedEnumFieldDefinition))
                .build();
        final Type type = client.execute(TypeCreateCommand.of(typeDraft));
        final Type updatedType = operator.apply(type);
        client.execute(TypeDeleteCommand.of(updatedType));
    }
}
