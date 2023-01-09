package io.sphere.sdk.productselections.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.productselections.commands.updateactions.AddProduct;
import io.vrap.rmf.base.client.utils.json.JsonUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ProductSelectionUpdateCommandTest {
    @Test
    public void addProduct() throws JsonProcessingException {
        final AddProduct command = AddProduct.of(Product.referenceOfId("abc")
                                                    .toResourceIdentifier());

        String json = JsonUtils.toJsonString(command);

        Assertions.assertThat(json).isEqualTo("{\"action\":\"addProduct\",\"product\":{\"typeId\":\"product\",\"id\":\"abc\"}}");
    }
}
