package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.DefaultModelBuilder;
import io.sphere.sdk.models.DefaultModelSubclassTest;
import io.sphere.sdk.attributes.AttributeDefinition;
import io.sphere.sdk.json.JsonUtils;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

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
    public void example1ToStringContainsSubclassAttributes(final String string) {
        assertThat(string).contains(NAME_1).contains(DESCRIPTION_1);
    }

    @Override
    public void testSubclassGettersOfExample1(final ProductType example) {
        assertThat(example.getName()).isEqualTo(NAME_1);
        assertThat(example.getDescription()).isEqualTo(DESCRIPTION_1);
        assertThat(example.getAttributes()).isEqualTo(ATTRIBUTES_1);
    }

    @Test
    public void jsonDeserialization() throws Exception {
        final String jsonString = "{\n" +
                "    \"id\":\"3a870271-933b-4b83-af1f-de70fcd5fa29\",\n" +
                "    \"version\":1,\n" +
                "    \"name\":\"Sample Product Type\",\n" +
                "    \"description\":\"A demo product type\",\n" +
                "    \"classifier\":\"Complex\",\n" +
                "    \"attributes\":[\n" +
                "        {\n" +
                "            \"type\":{\"name\":\"text\"},\n" +
                "            \"name\":\"custom-attribute\",\n" +
                "            \"label\":{\"en\":\"Custom attribute\"},\n" +
                "            \"isRequired\":false,\n" +
                "            \"inputHint\":\"SingleLine\",\n" +
                "            \"displayGroup\":\"Other\",\n" +
                "            \"isSearchable\":true,\n" +
                "            \"attributeConstraint\":\"CombinationUnique\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        final ProductType productType = JsonUtils.readObjectFromJsonString(ProductType.typeReference(), jsonString);
        assertThat(productType.getName()).isEqualTo("Sample Product Type");
    }
}
