package io.sphere.sdk.products;

import io.sphere.sdk.models.*;
import io.sphere.sdk.products.exceptions.AttributeMappingException;
import io.sphere.sdk.producttypes.MetaProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.attributes.AttributeDefinition;
import io.sphere.sdk.utils.JsonUtils;
import org.junit.Test;

import java.util.*;

import static io.sphere.sdk.products.AttributeTypes.*;
import static java.util.Arrays.asList;
import static java.util.Locale.*;
import static org.fest.assertions.Assertions.assertThat;

import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class ProductAttributeAccessTest {
    public static final String LOC_STRING_ATTRIBUTE = "loc-string-attribute";
    public static final String NOT_PRESENT = "not-present";
    public static final String STRING_ATTRIBUTE = "string-attribute";
    public static final String BOOLEAN_ATTRIBUTE = "boolean-attribute";
    private final Product product = JsonUtils.readObjectFromResource("product1.json", Product.typeReference());
    private final ProductProjection productProjection = JsonUtils.readObjectFromResource("product-projection1.json", ProductProjection.typeReference());
    private final ProductVariant variant = product.getMasterData().getCurrent().getMasterVariant();
    private final ProductType productType = productProjection.getProductType().getObj().get();

    private final AttributeAccessor<Product, LocalizedString> localizedStringAttributeAccessor = ofLocalizedString().access(LOC_STRING_ATTRIBUTE);
    private final AttributeAccessor<Product, LocalizedString> wrongTypeAttributeAccessor = ofLocalizedString().access("boolean-attribute");
    private final AttributeAccessor<Product, LocalizedString> notPresentAttributeAccessor = ofLocalizedString().access(NOT_PRESENT);

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

    @Test
    public void productProjection() throws Exception {
        assertThat(productProjection.getMasterVariant().getAttribute(localizedStringAttributeAccessor).get()).
                isEqualTo(LocalizedString.of(GERMAN, "val-loc-string-de", ENGLISH, "val-loc-string-en"));

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
                .ifIs(ofMoney(), money -> money.format(3) + " " + money.getCurrencyCode(), money -> money.getCentAmount() > 0)
                .ifIs(ofLocalizedString(), lString -> lString.get(locale).orElse("<no translation found>"))
                .ifGuarded(ofString(), s -> s.length() > 2000 ? Optional.empty() : Optional.of(s))
                .getValue().orElse("<no mapping found>");
    }
}
