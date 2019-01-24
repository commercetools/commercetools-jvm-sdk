package io.sphere.sdk.products.attributes;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import org.junit.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AttributeFromReferenceTest {

    @Test
    public void createAttributeFromReferenceSet(){
        final Product product = SphereJsonUtils.readObjectFromResource("product1.json", Product.class);

        final Reference<Product> expandedReference =
                Reference.ofResourceTypeIdAndIdAndObj(Product.referenceTypeId(), UUID.randomUUID().toString(), product);

        final HashSet<Reference<Product>> references = new HashSet<>();
        references.add(expandedReference);

        final Attribute attribute = Attribute.of("attrName", AttributeAccess.ofProductReferenceSet(), references);
        assertThat(attribute.getValue(AttributeAccess.ofProductReferenceSet()).stream().allMatch(productReference -> productReference.getObj() != null)).isTrue();
    }

}
