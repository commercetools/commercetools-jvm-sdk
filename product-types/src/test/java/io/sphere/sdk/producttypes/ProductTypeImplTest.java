package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.DefaultModelBuilder;
import io.sphere.sdk.models.DefaultModelSubclassTest;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.fest.assertions.Assertions.assertThat;

public final class ProductTypeImplTest extends DefaultModelSubclassTest<ProductType> {

    public static final String NAME_1 = "product type 1";
    public static final String DESCRIPTION_1 = "description 1";
    public static final List<AttributeDefinition> ATTRIBUTES_1 = emptyList();

    @Override
    public DefaultModelBuilder<ProductType> newExample1Builder() {
        return ProductTypeBuilder.of("dummy", NAME_1, DESCRIPTION_1, ATTRIBUTES_1);
    }

    @Override
    public DefaultModelBuilder<ProductType> newExample2Builder() {
        return ProductTypeBuilder.of("dummy 2", "product type 2", "description 2", emptyList());
    }

    @Override
    public void toStringContainsSubclassAttributes() {
        assertThat(newExample1().toString()).contains(NAME_1).contains(DESCRIPTION_1);
    }

    @Override
    public void testSubclassGetters() {
        final ProductType example = newExample1();
        assertThat(example.getName()).isEqualTo(NAME_1);
        assertThat(example.getDescription()).isEqualTo(DESCRIPTION_1);
        assertThat(example.getAttributes()).isEqualTo(ATTRIBUTES_1);
    }
}
