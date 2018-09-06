package io.sphere.sdk.types;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Map;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CustomFieldsDraftBuilderIntegrationTest extends IntegrationTest {

    @Test
    public void copyFromCustomField() {
        withUpdateableType(client(), type -> {
            final String value = "foo";
            final String id = type.getId();
            final CategoryDraftBuilder categoryDraftBuilder = CategoryDraftBuilder.of(en("category-name"), en("category-slug"))
                    .custom(CustomFieldsDraft.ofTypeIdAndObjects(id, singletonMap(STRING_FIELD_NAME, value)));
            withCategory(client(), categoryDraftBuilder, category -> {
                //Test builder
                final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.of(category.getCustom()).build();
                assertThat(customFieldsDraft.getType().getId()).isEqualTo(id);

                //Test factory method from draft
                final CustomFieldsDraft customFieldsDraft2 = CustomFieldsDraft.ofCustomFields(category.getCustom());
                assertThat(customFieldsDraft2.getType().getId()).isEqualTo(id);
            });
            return type;
        });
    }

    @Test
    public void customFieldsDraftTest(){

        final CustomFieldsDraft initialDraft = CustomFieldsDraftBuilder
                .ofTypeKey("foo")
                .addObject("field1", "value1")
                .build();
        final CustomFieldsDraft extendedDraft = CustomFieldsDraftBuilder
                .of(initialDraft)
                .addObject("field2", "value2")
                .build();

        assertThat(extendedDraft.getFields()).hasSize(2);
        assertThat(extendedDraft.getFields().get("field1")).isEqualTo(SphereJsonUtils.toJsonNode("value1"));
        assertThat(extendedDraft.getFields().get("field2")).isEqualTo(SphereJsonUtils.toJsonNode("value2"));


    }

}