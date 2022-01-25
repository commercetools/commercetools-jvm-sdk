package io.sphere.sdk.types;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CustomLineItem;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.inventory.InventoryEntry;
import io.sphere.sdk.models.*;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.Parcel;
import io.sphere.sdk.orders.ReturnItem;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.Transaction;
import io.sphere.sdk.payments.commands.updateactions.AddInterfaceInteraction;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.TextLineItem;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.commands.TypeCreateCommand;
import io.sphere.sdk.types.commands.TypeDeleteCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.stream.Collectors.toList;

public class TypeFixtures {

    public static final String LOC_STRING_FIELD_NAME = "locstring-field-name";
    public static final String BOOLEAN_FIELD_NAME = "bool-field-name";
    public static final String DATE_FIELD_NAME = "date-field-name";
    public static final String TIME_FIELD_NAME = "time-field-name";
    public static final String DATETIME_FIELD_NAME = "datetime-field-name";
    public static final String MONEY_FIELD_NAME = "money-field-name";
    public static final String INT_FIELD_NAME = "int-field-name";
    public static final String BIG_DECIMAL_FIELD_NAME = "big-dec-field-name";
    public static final String DOUBLE_FIELD_NAME = "double-field-name";
    public static final String ENUM_FIELD_NAME = "enum-field-name";
    public static final String CAT_REFERENCE_FIELD_NAME = "catref";
    public static final String LOCALIZED_ENUM_FIELD_NAME = "localized-enum-field-name";
    public static final Set<String> TYPE_IDS = new HashSet<>(
            asList(
                    Category.resourceTypeId(),
                    Customer.resourceTypeId(),
                    Cart.resourceTypeId(),
                    Order.resourceTypeId(),
                    LineItem.resourceTypeId(),
                    CustomLineItem.resourceTypeId(),
                    Payment.resourceTypeId(),
                    AddInterfaceInteraction.resourceTypeId(),
                    Price.resourceTypeId(),
                    Review.resourceTypeId(),
                    Channel.referenceTypeId(),
                    InventoryEntry.resourceTypeId(),
                    Asset.resourceTypeId(),
                    ShoppingList.referenceTypeId(),
                    TextLineItem.resourceTypeId(),
                    DiscountCode.referenceTypeId(),
                    CartDiscount.referenceTypeId(),
                    CustomerGroup.referenceTypeId(),
                    OrderEdit.referenceTypeId(),
                    Store.referenceTypeId(),
                    ShippingMethod.referenceTypeId(),
                    Address.resourceTypeId(),
                    Transaction.referenceTypeId(),
                    ReturnItem.referenceTypeId(),
                    Parcel.referenceTypeId()
            )
    );
    public static final String STRING_FIELD_NAME = "string-field-name";
    public static final String STRING_SET_FIELD_NAME = "string-set-field-name";
    public static final String TYPE_NAME = "name of the custom type";

    public static void withType(final BlockingSphereClient client, final UnaryOperator<TypeDraftBuilder> b, final Consumer<Type> consumer) {
        final TypeDraftBuilder typeDraftBuilder = b.apply(createTypeDraftBuilder());
        final TypeDraft draft = typeDraftBuilder.build();
        withType(client, draft, consumer);
    }

    public static void withType(final BlockingSphereClient client, final TypeDraft draft, final Consumer<Type> consumer) {
        final Type type = client.executeBlocking(TypeCreateCommand.of(draft));
        consumer.accept(type);
        client.executeBlocking(TypeDeleteCommand.of(type));
    }

    public static void withUpdateableType(final BlockingSphereClient client, final UnaryOperator<Type> operator) {
        final TypeDraftBuilder typeDraftBuilder = createTypeDraftBuilder();
        final TypeDraft typeDraft = typeDraftBuilder
                .build();
        final Type type = client.executeBlocking(TypeCreateCommand.of(typeDraft));
        final Type updatedType = operator.apply(type);
        client.executeBlocking(TypeDeleteCommand.of(updatedType));
    }

    public static TypeDraftBuilder createTypeDraftBuilder() {
        final String typeKey = randomKey();
        return TypeDraftBuilder.of(typeKey, en(TYPE_NAME), TYPE_IDS)
                .description(en("description"))
                .fieldDefinitions(asList(stringfieldDefinition(), enumFieldDefinition(), localizedEnumFieldDefinition(), catRefDefinition(),
                        booleanDefinition(), LocalizedStringDefinition(), intDefinition(), doubleDefinition(), bigDecimalDefinition(), moneyDefinition(),
                        dateDefinition(), dateTimeDefinition(), timeDefinition(), stringSetDefinition()));
    }

    private static FieldDefinition stringSetDefinition() {
        return fieldDefinition(SetFieldType.of(StringFieldType.of()), STRING_SET_FIELD_NAME);
    }

    private static FieldDefinition dateDefinition() {
        return fieldDefinition(DateFieldType.of(), DATE_FIELD_NAME);
    }

    private static FieldDefinition dateTimeDefinition() {
        return fieldDefinition(DateTimeFieldType.of(), DATETIME_FIELD_NAME);
    }

    private static FieldDefinition timeDefinition() {
        return fieldDefinition(TimeFieldType.of(), TIME_FIELD_NAME);
    }

    private static FieldDefinition booleanDefinition() {
        return fieldDefinition(BooleanFieldType.of(), BOOLEAN_FIELD_NAME);
    }

    private static FieldDefinition moneyDefinition() {
        return fieldDefinition(MoneyFieldType.of(), MONEY_FIELD_NAME);
    }

    private static FieldDefinition LocalizedStringDefinition() {
        return fieldDefinition(LocalizedStringFieldType.of(), LOC_STRING_FIELD_NAME);
    }

    private static FieldDefinition catRefDefinition() {
        return fieldDefinition(ReferenceFieldType.of(Category.referenceTypeId()), CAT_REFERENCE_FIELD_NAME);
    }

    private static FieldDefinition intDefinition() {
        return fieldDefinition(NumberFieldType.of(), INT_FIELD_NAME);
    }

    private  static FieldDefinition bigDecimalDefinition(){
        return fieldDefinition(NumberFieldType.of(), BIG_DECIMAL_FIELD_NAME);
    }

    private static FieldDefinition doubleDefinition() {
        return fieldDefinition(NumberFieldType.of(), DOUBLE_FIELD_NAME);
    }

    private static FieldDefinition localizedEnumFieldDefinition() {
        final List<LocalizedEnumValue> localizedEnumValues = asList("1", "2").stream()
                .map(s -> LocalizedEnumValue.of("key" + s, en("label " + s)))
                .collect(toList());
        return fieldDefinition(LocalizedEnumFieldType.of(localizedEnumValues), LOCALIZED_ENUM_FIELD_NAME);
    }

    private static FieldDefinition enumFieldDefinition() {
        final List<EnumValue> enumValues = asList(EnumValue.of("key1", "label1"), EnumValue.of("key2", "label2"));
        return fieldDefinition(EnumFieldType.of(enumValues), ENUM_FIELD_NAME);
    }

    private static FieldDefinition stringfieldDefinition() {
        return fieldDefinition(StringFieldType.of(), STRING_FIELD_NAME);
    }

    private static FieldDefinition fieldDefinition(final FieldType fieldType, final String fieldName) {
        return FieldDefinition
                .of(fieldType, fieldName, en(fieldName), false, TextInputHint.SINGLE_LINE);
    }
}
