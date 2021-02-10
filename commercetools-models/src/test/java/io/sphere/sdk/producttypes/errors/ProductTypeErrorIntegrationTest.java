package io.sphere.sdk.producttypes.errors;

import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeDefinitionBuilder;
import io.sphere.sdk.products.attributes.ReferenceAttributeType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeFixtures;
import io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommand;
import io.sphere.sdk.producttypes.commands.updateactions.AddAttributeDefinition;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.deleteProductType;
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
}
