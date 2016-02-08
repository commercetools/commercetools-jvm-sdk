package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.products.attributes.AttributeExtraction;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.*;
import io.sphere.sdk.producttypes.MetaProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.*;

import static io.sphere.sdk.products.attributes.AttributeAccess.*;
import static java.util.Arrays.asList;
import static java.util.Locale.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductAttributeAccessTest {
    public static final String LOC_STRING_ATTRIBUTE = "loc-string-attribute";
    public static final String NOT_PRESENT = "not-present";
    public static final String STRING_ATTRIBUTE = "string-attribute";
    public static final String BOOLEAN_ATTRIBUTE = "boolean-attribute";
    private final Product product = SphereJsonUtils.readObjectFromResource("product1.json", Product.typeReference());
    private final ProductProjection productProjection = SphereJsonUtils.readObjectFromResource("product-projection1.json", ProductProjection.typeReference());
    private final ProductVariant variant = product.getMasterData().getCurrent().getMasterVariant();
    private final ProductType productType = productProjection.getProductType().getObj();

    private final NamedAttributeAccess<LocalizedString> localizedStringNamedAttributeAccess = ofLocalizedString().ofName(LOC_STRING_ATTRIBUTE);
    private final NamedAttributeAccess<LocalizedString> wrongTypeNamedAttributeAccess = ofLocalizedString().ofName("boolean-attribute");
    private final NamedAttributeAccess<LocalizedString> notPresentNamedAttributeAccess = ofLocalizedString().ofName(NOT_PRESENT);

    @Test
    public void size() throws Exception {
        assertThat(variant.getAttributes()).hasSize(20);
    }

    @Test
    public void getterWithJsonAttributeAccess() throws Exception {
        final JsonNode actual = variant.findAttribute(LOC_STRING_ATTRIBUTE, ofJsonNode()).get();
        final JsonNode expected = SphereJsonUtils.parse("{\"de\":\"val-loc-string-de\",\"en\":\"val-loc-string-en\"}");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getterWithAttributeAccess() throws Exception {
        assertThat(variant.findAttribute(LOC_STRING_ATTRIBUTE, ofLocalizedString()).get()).
                isEqualTo(LocalizedString.of(GERMAN, "val-loc-string-de", ENGLISH, "val-loc-string-en"));
    }

    @Test
    public void localizedString() throws Exception {
        assertThat(variant.findAttribute(localizedStringNamedAttributeAccess).get()).
                isEqualTo(LocalizedString.of(GERMAN, "val-loc-string-de", ENGLISH, "val-loc-string-en"));
    }

    @Test
    public void attributeNotFound() throws Exception {
        assertThat(variant.findAttribute(notPresentNamedAttributeAccess)).isEmpty();
    }

    @Test(expected = JsonException.class)
    public void wrongAttributeType() throws Exception {
        variant.findAttribute(wrongTypeNamedAttributeAccess);
    }

    @Test
    public void productProjection() throws Exception {
        assertThat(productProjection.getMasterVariant().findAttribute(localizedStringNamedAttributeAccess).get()).
                isEqualTo(LocalizedString.of(GERMAN, "val-loc-string-de", ENGLISH, "val-loc-string-en"));

    }

    @Test
    public void checkExistenceWithProductType() throws Exception {
        assertThat(variant.hasAttribute(LOC_STRING_ATTRIBUTE)).isTrue();
        assertThat(variant.hasAttribute(NOT_PRESENT)).isFalse();
    }

    @Test
    public void mapMatchingAttributeWithIfIs() throws Exception {
        final Attribute attr = variant.getAttribute(LOC_STRING_ATTRIBUTE);
        assertThat(map(attr)).isEqualTo("val-loc-string-de");
    }

    @Test
    public void mapMatchingAttributeWithIfGuarded() throws Exception {
        final Attribute attr = variant.getAttribute(STRING_ATTRIBUTE);
        assertThat(map(attr)).isEqualTo("val-string-en");
    }

    @Test
    public void mapMatchingAttributeAbsent() throws Exception {
        final Attribute attr = variant.getAttribute(BOOLEAN_ATTRIBUTE);
        assertThat(map(attr)).isEqualTo("<no mapping found>");
    }


    private String map(final Attribute attr) {
        final MetaProductType metaProductType = MetaProductType.of(asList(productType));
        final Locale locale = Locale.GERMAN;
        final AttributeDefinition attrDefinition = metaProductType.getAttribute(attr.getName());
        return AttributeExtraction.<String>of(attrDefinition, attr)
                .ifIs(ofLocalizedString(), lString -> lString.find(locale).orElse("<no translation found>"))
                .ifGuarded(ofString(), s -> s.length() > 2000 ? Optional.empty() : Optional.of(s))
                .findValue().orElse("<no mapping found>");
    }
}
