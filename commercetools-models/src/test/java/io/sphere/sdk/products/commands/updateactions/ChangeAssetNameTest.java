package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.products.Product;
import org.junit.Test;

import static io.sphere.sdk.models.LocalizedString.ofEnglish;
import static org.assertj.core.api.Assertions.assertThat;

public class ChangeAssetNameTest {

    @Test
    public void changeAssetName_ByAssetKeyToJsonString_ShouldSerialiseAssetKeyField() {
        final UpdateAction<Product> action =
            ChangeAssetName.ofAssetKeyAndVariantId(1, "foo", ofEnglish("bar"), true);

        final String actionAsJson = SphereJsonUtils.toJsonString(action);

        assertThat(actionAsJson).contains("\"assetKey\":\"foo\"");
    }
}