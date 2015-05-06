package io.sphere.sdk.products;

import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.*;
import io.sphere.sdk.producttypes.MetaProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.AttributeDefinition;
import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.json.JsonUtils;
import org.junit.Test;

import java.util.*;

import static io.sphere.sdk.attributes.AttributeAccess.*;
import static java.util.Arrays.asList;
import static java.util.Locale.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductAttributeAccessTest {
    public static final String LOC_STRING_ATTRIBUTE = "loc-string-attribute";
    public static final String NOT_PRESENT = "not-present";
    public static final String STRING_ATTRIBUTE = "string-attribute";
    public static final String BOOLEAN_ATTRIBUTE = "boolean-attribute";
    private final Product product = JsonUtils.readObjectFromResource("product1.json", Product.typeReference());
    private final ProductProjection productProjection = JsonUtils.readObjectFromResource("product-projection1.json", ProductProjection.typeReference());
    private final ProductVariant variant = product.getMasterData().getCurrent().get().getMasterVariant();
    private final ProductType productType = productProjection.getProductType().getObj().get();

    private final AttributeGetterSetter<Product, LocalizedStrings> localizedStringsAttributeGetterSetter = ofLocalizedStrings().ofName(LOC_STRING_ATTRIBUTE);
    private final AttributeGetterSetter<Product, LocalizedStrings> wrongTypeAttributeGetterSetter = ofLocalizedStrings().ofName("boolean-attribute");
    private final AttributeGetterSetter<Product, LocalizedStrings> notPresentAttributeGetterSetter = ofLocalizedStrings().ofName(NOT_PRESENT);

    @Test
    public void size() throws Exception {
        assertThat(variant.getAttributes()).hasSize(20);
    }

    @Test
    public void localizedStrings() throws Exception {
        assertThat(variant.getAttribute(localizedStringsAttributeGetterSetter).get()).
                isEqualTo(LocalizedStrings.of(GERMAN, "val-loc-string-de", ENGLISH, "val-loc-string-en"));
    }

    @Test
    public void attributeNotFound() throws Exception {
        assertThat(variant.getAttribute(notPresentAttributeGetterSetter)).isEmpty();
    }

    @Test(expected = JsonException.class)
    public void wrongAttributeType() throws Exception {
        variant.getAttribute(wrongTypeAttributeGetterSetter);
    }

    @Test
    public void productProjection() throws Exception {
        assertThat(productProjection.getMasterVariant().getAttribute(localizedStringsAttributeGetterSetter).get()).
                isEqualTo(LocalizedStrings.of(GERMAN, "val-loc-string-de", ENGLISH, "val-loc-string-en"));

    }

    @Test
    public void checkExistenceWithProductType() throws Exception {
        assertThat(variant.hasAttribute(LOC_STRING_ATTRIBUTE)).isTrue();
        assertThat(variant.hasAttribute(NOT_PRESENT)).isFalse();
    }

    @Test
    public void mapMatchingAttributeWithIfIs() throws Exception {
        final Attribute attr = variant.getAttribute(LOC_STRING_ATTRIBUTE).get();
        assertThat(map(attr)).isEqualTo("val-loc-string-de");
    }

    @Test
    public void mapMatchingAttributeWithIfGuarded() throws Exception {
        final Attribute attr = variant.getAttribute(STRING_ATTRIBUTE).get();
        assertThat(map(attr)).isEqualTo("val-string-en");
    }

    @Test
    public void mapMatchingAttributeAbsent() throws Exception {
        final Attribute attr = variant.getAttribute(BOOLEAN_ATTRIBUTE).get();
        assertThat(map(attr)).isEqualTo("<no mapping found>");
    }


    private String map(final Attribute attr) {
        final MetaProductType metaProductType = MetaProductType.of(asList(productType));
        final Locale locale = Locale.GERMAN;
        final AttributeDefinition attrDefinition = metaProductType.getAttribute(attr.getName()).get();
        return attr.<String>collect(attrDefinition)
                .ifIs(ofLocalizedStrings(), lString -> lString.get(locale).orElse("<no translation found>"))
                .ifGuarded(ofString(), s -> s.length() > 2000 ? Optional.empty() : Optional.of(s))
                .getValue().orElse("<no mapping found>");
    }
}
