package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class AttributeAccessTest {
    @Test
    public void setReference() throws Exception {
        final AttributeAccess<Set<Reference<Product>>> access = AttributeAccess.ofProductReferenceSet();
        final AttributeDefinition attributeDefinition = AttributeDefinitionBuilder.of("productreference", LocalizedStrings.ofEnglishLocale("foo"), SetType.of(ReferenceType.ofProduct())).build();
        assertThat(access.canHandle(attributeDefinition)).isTrue();
    }
}