package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.commands.CartCreateCommand;
import io.sphere.sdk.carts.commands.CartDeleteCommand;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetCustomField;
import io.sphere.sdk.carts.commands.updateactions.SetCustomType;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import org.junit.Test;

import java.util.HashMap;

import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class CartsCustomFieldsIntegrationTest extends IntegrationTest {
    @Test
    public void createCartWithCustomType() {
        withUpdateableType(client(), type -> {
            final CustomFieldsDraft customFieldsDraft = CustomFieldsDraftBuilder.ofType(type).addObject(STRING_FIELD_NAME, "a value").build();
            final CartDraft categoryDraft = CartDraftBuilder.of(EUR).country(CountryCode.DE).custom(customFieldsDraft).build();
            final Cart category = client().executeBlocking(CartCreateCommand.of(categoryDraft));
            assertThat(category.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a value");

            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(category, SetCustomField.ofObject(STRING_FIELD_NAME, "a new value")));
            assertThat(updatedCart.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("a new value");

            //cleanup
            client().executeBlocking(CartDeleteCommand.of(updatedCart));

            return type;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            final Cart cart = CartFixtures.createCartWithShippingAddress(client());
            final HashMap<String, Object> fields = new HashMap<>();
            fields.put(STRING_FIELD_NAME, "hello");
            final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetCustomType.ofTypeIdAndObjects(type.getId(), fields)));
            assertThat(updatedCart.getCustom().getType()).isEqualTo(type.toReference());
            assertThat(updatedCart.getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference())).isEqualTo("hello");

            //cleanup
            client().executeBlocking(CartDeleteCommand.of(updatedCart));

            return type;
        });
    }
}
