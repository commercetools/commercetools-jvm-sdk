package io.sphere.sdk.products.commands.updateactions;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.Product;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SetAssetCustomFieldTest {

    @Test
    public void setAssetCustomField_ByAssetKeyToJsonString_ShouldSerialiseAssetKeyField() {
        final UpdateAction<Product> action =
            SetAssetCustomField.ofVariantIdUsingJsonAndAssetKey(1, "foo", "bar",
                JsonNodeFactory.instance.numberNode(1), true);

        final String actionAsJson = SphereJsonUtils.toJsonString(action);

        assertThat(actionAsJson).contains("\"assetKey\":\"foo\"");
    }
}