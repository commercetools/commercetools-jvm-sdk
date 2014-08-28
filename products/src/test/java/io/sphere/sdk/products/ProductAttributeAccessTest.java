package io.sphere.sdk.products;

import io.sphere.sdk.models.*;
import io.sphere.sdk.models.exceptions.AttributeMappingException;
import io.sphere.sdk.utils.JsonUtils;
import org.junit.Test;

import static java.util.Locale.*;
import static org.fest.assertions.Assertions.assertThat;

import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class ProductAttributeAccessTest {
    private final Product product = JsonUtils.readObjectFromResource("product1.json", Product.typeReference());
    private final ProductVariant variant = product.getMasterData().getCurrent().getMasterVariant();

    private final AttributeAccessor<Product, LocalizedString> localizedStringAttributeAccessor = AttributeAccessor.ofLocalizedString("loc-string-attribute");
    private final AttributeAccessor<Product, LocalizedString> wrongTypeAttributeAccessor = AttributeAccessor.ofLocalizedString("boolean-attribute");
    private final AttributeAccessor<Product, LocalizedString> notPresentAttributeAccessor = AttributeAccessor.ofLocalizedString("not-present");

    @Test
    public void size() throws Exception {
        assertThat(variant.getAttributes()).hasSize(20);
    }

    @Test
    public void localizedString() throws Exception {
        assertThat(variant.getAttribute(localizedStringAttributeAccessor).get()).
                isEqualTo(LocalizedString.of(GERMAN, "val-loc-string-de", ENGLISH, "val-loc-string-en"));
    }

    @Test
    public void attributeNotFound() throws Exception {
        assertThat(variant.getAttribute(notPresentAttributeAccessor)).isAbsent();
    }

    @Test(expected = AttributeMappingException.class)
    public void wrongAttributeType() throws Exception {
        variant.getAttribute(wrongTypeAttributeAccessor);
    }
}
