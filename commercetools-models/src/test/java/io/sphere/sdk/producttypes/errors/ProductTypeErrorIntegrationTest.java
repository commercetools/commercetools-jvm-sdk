package io.sphere.sdk.producttypes.errors;

import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Unpublish;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommand;
import io.sphere.sdk.producttypes.commands.updateactions.AddAttributeDefinition;
import io.sphere.sdk.producttypes.commands.updateactions.AddEnumValue;
import io.sphere.sdk.producttypes.commands.updateactions.ChangeEnumKey;
import io.sphere.sdk.producttypes.commands.updateactions.RemoveEnumValues;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.deleteProductType;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withUpdateableProductType;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProductTypeErrorIntegrationTest extends IntegrationTest {

    @Test
    public void attributeDefinitionAlreadyExists() {
        final String productReferenceProductTypeName = "ProductReferenceProductTypeName1";
        ProductType productType1 = ProductTypeFixtures.productReferenceProductType(client());
        ProductType productType2 = ProductTypeFixtures.productReferenceProductType(client());

        final AttributeDefinition productReferenceDefinition = AttributeDefinitionBuilder
                .of(productReferenceProductTypeName, en("suggested product"), ReferenceAttributeType.ofProduct())
                .required(true)
                .build();
        final ProductType updatedProductType1 = client().executeBlocking(ProductTypeUpdateCommand.of(productType1, AddAttributeDefinition.of(productReferenceDefinition)));
        final Throwable throwable = catchThrowable(() -> client().executeBlocking(ProductTypeUpdateCommand.of(productType2, AddAttributeDefinition.of(productReferenceDefinition))));

        assertThat(throwable).isInstanceOf(ErrorResponseException.class);
        final ErrorResponseException e = (ErrorResponseException) throwable;
        assertThat(e.hasErrorCode(AttributeDefinitionAlreadyExistsError.CODE)).isTrue();
        assertThat(e.getErrors().get(0).getCode()).isEqualTo(AttributeDefinitionAlreadyExistsError.CODE);
        final AttributeDefinitionAlreadyExistsError error = e.getErrors().get(0).as(AttributeDefinitionAlreadyExistsError.class);
        assertThat(error.getConflictingAttributeName()).isEqualTo(productReferenceProductTypeName);
        deleteProductType(client(), updatedProductType1);
        deleteProductType(client(), productType2);
    }

//    it doesn't show the error AttributeDefinitionTypeConflict:
//    A given AttributeDefinition name already exists on another product type with a different type.
    @Ignore
    @Test
    public void attributeDefinitionTypeConflict() {
         final String productTypeName = "ProductFlags";
         final String KEY_1 = "key_1";
         final String KEY_2 = "key_2";
         final LocalizedEnumValue ONE = LocalizedEnumValue.of(KEY_1, LocalizedString.of(ENGLISH, "one", GERMAN, "eins"));
         final LocalizedEnumValue TWO = LocalizedEnumValue.of(KEY_2, LocalizedString.of(ENGLISH, "two", GERMAN, "zwei"));

        final LocalizedString label = LocalizedString.of(ENGLISH, "Product flag", GERMAN, "Produkt Flag");
        final AttributeType attributeType1 = SetAttributeType.of(LocalizedEnumAttributeType.of(TWO));
        final AttributeDefinition attributeDefinition1 = AttributeDefinitionBuilder.
                of(productTypeName, label, attributeType1)
                .build();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), productTypeName, "product type description", asList(attributeDefinition1));
        final ProductType productType1 = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));


        final ProductTypeDraft productTypeDraft2 = ProductTypeDraft.of(randomKey(), productTypeName, "product type description", asList(attributeDefinition1));
        final ProductType productType2 = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft2));

        final AttributeType attributeType2 = SetAttributeType.of(LocalizedEnumAttributeType.of(ONE));
        final AttributeDefinition attributeDefinition2 = AttributeDefinitionBuilder.
                of(productTypeName, label, attributeType2)
                .build();
        final Throwable throwable = catchThrowable(() -> client().executeBlocking(ProductTypeUpdateCommand.of(productType2, AddAttributeDefinition.of(attributeDefinition2))));

        assertThat(throwable).isInstanceOf(ErrorResponseException.class);
        final ErrorResponseException e = (ErrorResponseException) throwable;
        assertThat(e.hasErrorCode(AttributeDefinitionTypeConflictError.CODE)).isTrue();
        assertThat(e.getErrors().get(0).getCode()).isEqualTo(AttributeDefinitionTypeConflictError.CODE);
        final AttributeDefinitionTypeConflictError error = e.getErrors().get(0).as(AttributeDefinitionTypeConflictError.class);

        deleteProductType(client(), productType1);
        deleteProductType(client(), productType2);
    }

    @Test
    public void enumKeyAlreadyExists() {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "size";
            assertThat(productType.findAttribute(attributeName)).isPresent();
            final EnumValue value = EnumValue.of("XXXL", "XXXL");

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    AddEnumValue.of(attributeName, value)));

            assertThat(updatedProductType.getAttribute(attributeName).getAttributeType())
                    .isInstanceOf(EnumAttributeType.class)
                    .matches(type -> ((EnumAttributeType) type).getValues().contains(value));

            final Throwable throwable = catchThrowable(() -> client().executeBlocking(ProductTypeUpdateCommand.of(updatedProductType,
                    AddEnumValue.of(attributeName, value))));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.hasErrorCode(EnumKeyAlreadyExistsError.CODE)).isTrue();
            assertThat(e.getErrors().get(0).getCode()).isEqualTo(EnumKeyAlreadyExistsError.CODE);
            final EnumKeyAlreadyExistsError error = e.getErrors().get(0).as(EnumKeyAlreadyExistsError.class);
            assertThat(error.getConflictingAttributeName()).isEqualTo(updatedProductType.getAttributes().get(0).getName());

            return updatedProductType;
        });
    }

    @Test
    public void enumKeyDoesNotExist() {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "size";
            assertThat(productType.findAttribute(attributeName)).isPresent();
            final EnumValue value = EnumValue.of("XXXL", "XXXL");

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    AddEnumValue.of(attributeName, value)));

            final Throwable throwable = catchThrowable(() -> client().executeBlocking(ProductTypeUpdateCommand.of(updatedProductType,
                    ChangeEnumKey.of(attributeName, "value2", "XXXL"))));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.hasErrorCode(EnumKeyDoesNotExistError.CODE)).isTrue();
            assertThat(e.getErrors().get(0).getCode()).isEqualTo(EnumKeyDoesNotExistError.CODE);
            final EnumKeyDoesNotExistError error = e.getErrors().get(0).as(EnumKeyDoesNotExistError.class);

            return updatedProductType;
        });
    }

    @Test
    public void duplicateEnumValues() {
        withUpdateableProductType(client(), productType -> {
            final EnumValue value = EnumValue.of("XXXL", "XXXL");
            final AttributeDefinitionDraft attributeName = AttributeDefinitionDraftBuilder.of(EnumAttributeType.of(value, value), "duplicateEnum", LocalizedString.of(ENGLISH, "duplicateEnum"), false).build();

            final Throwable throwable = catchThrowable(() -> client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    AddAttributeDefinition.of(attributeName))));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.hasErrorCode(DuplicateEnumValuesError.CODE)).isTrue();
            assertThat(e.getErrors().get(0).getCode()).isEqualTo(DuplicateEnumValuesError.CODE);
            final DuplicateEnumValuesError error = e.getErrors().get(0).as(DuplicateEnumValuesError.class);

            return productType;
        });
    }

    @Test
    public void attributeNameDoesNotExist() {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "temperature";

            final EnumValue value = EnumValue.of("cold", "cold");

            final Throwable throwable = catchThrowable(() -> client().executeBlocking(
                    ProductTypeUpdateCommand.of(productType, AddEnumValue.of(attributeName, value))
            ));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.hasErrorCode(AttributeNameDoesNotExistError.CODE)).isTrue();
            assertThat(e.getErrors().get(0).getCode()).isEqualTo(AttributeNameDoesNotExistError.CODE);
            final AttributeNameDoesNotExistError error = e.getErrors().get(0).as(AttributeNameDoesNotExistError.class);
            assertThat(productType.findAttribute(attributeName)).isNotPresent();
            assertThat(error.getInvalidAttributeName()).isEqualTo(attributeName);

            return productType;
        });
    }
}
