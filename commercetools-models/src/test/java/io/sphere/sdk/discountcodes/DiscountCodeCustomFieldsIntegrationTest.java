package io.sphere.sdk.discountcodes;

import io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommand;
import io.sphere.sdk.discountcodes.commands.DiscountCodeDeleteCommand;
import io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommand;
import io.sphere.sdk.discountcodes.commands.updateactions.SetCustomField;
import io.sphere.sdk.discountcodes.commands.updateactions.SetCustomType;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import java.util.HashMap;

import static io.sphere.sdk.discountcodes.DiscountCodeFixtures.discountCodeDraftBuilder;
import static io.sphere.sdk.discountcodes.DiscountCodeFixtures.withUpdateableDiscountCode;
import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscountCodeCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void createDiscountCodeWithCustomType() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type)
                    .addObject(STRING_FIELD_NAME, "a value")
                    .build();

            final String code = randomString();
            final DiscountCodeDraft discountCodeDraft = discountCodeDraftBuilder(client(), code).custom(customFieldsDraft).build();
            final DiscountCode discountCode = client().executeBlocking(DiscountCodeCreateCommand.of(discountCodeDraft));
            assertThat(discountCode.getCustom().getFieldAsString(STRING_FIELD_NAME))
                    .isEqualTo("a value");

            final DiscountCodeUpdateCommand discountCodeUpdateCommand =
                    DiscountCodeUpdateCommand.of(discountCode, SetCustomField.ofObject(STRING_FIELD_NAME, "a new value"));
            final DiscountCode updatedDiscountCode = client().executeBlocking(discountCodeUpdateCommand);
            assertThat(updatedDiscountCode.getCustom()
                    .getFieldAsString(STRING_FIELD_NAME)).isEqualTo("a new value");

            //clean up
            client().executeBlocking(DiscountCodeDeleteCommand.of(updatedDiscountCode));
            return type;
        });
    }

    @Test
    public void setCustomType() {
        final String code = randomString();
        final DiscountCodeDraft discountCodeDraft = discountCodeDraftBuilder(client(), code).build();

        withUpdateableType(client(), type -> {
            withUpdateableDiscountCode(client(), discountCodeDraft, discountCode -> {
                final HashMap<String, Object> fields = new HashMap<>();
                fields.put(STRING_FIELD_NAME, "hello");

                final DiscountCodeUpdateCommand discountCodeUpdateCommand =
                        DiscountCodeUpdateCommand.of(discountCode, SetCustomType.ofTypeIdAndObjects(type.getId(), fields));

                final DiscountCode updatedDiscountCode = client().executeBlocking(discountCodeUpdateCommand);

                assertThat(updatedDiscountCode.getCustom().getType()).isEqualTo(type.toReference());
                assertThat(updatedDiscountCode.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("hello");

                final DiscountCode updatedDiscountCode1 =
                        client().executeBlocking(DiscountCodeUpdateCommand.of(updatedDiscountCode, SetCustomType.ofRemoveType()));
                assertThat(updatedDiscountCode1.getCustom()).isNull();

                return updatedDiscountCode1;
            });
            return type;
        });
    }
}
