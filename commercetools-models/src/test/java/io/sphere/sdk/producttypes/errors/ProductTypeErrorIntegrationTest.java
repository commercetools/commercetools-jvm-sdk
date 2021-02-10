package io.sphere.sdk.producttypes.errors;

import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeDefinitionBuilder;
import io.sphere.sdk.products.attributes.EnumAttributeType;
import io.sphere.sdk.products.attributes.ReferenceAttributeType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommand;
import io.sphere.sdk.producttypes.commands.updateactions.AddAttributeDefinition;
import io.sphere.sdk.producttypes.commands.updateactions.AddEnumValue;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.deleteProductType;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withUpdateableProductType;
import static io.sphere.sdk.test.SphereTestUtils.en;
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
    public void duplicateEnumValues() {
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
            assertThat(e.hasErrorCode(DuplicateEnumValuesError.CODE)).isTrue();
            assertThat(e.getErrors().get(0).getCode()).isEqualTo(DuplicateEnumValuesError.CODE);
            final DuplicateEnumValuesError error = e.getErrors().get(0).as(DuplicateEnumValuesError.class);
            assertThat(error.getDuplicates()).isEqualTo(updatedProductType.getAttributes().get(0).getName());

            return updatedProductType;
        });
    }

}
