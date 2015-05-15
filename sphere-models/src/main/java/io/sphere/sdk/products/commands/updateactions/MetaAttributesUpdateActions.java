package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductUpdateScope;

import java.util.List;

import static java.util.Arrays.asList;

/**
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setMetaAttributes()}
 */
public class MetaAttributesUpdateActions extends Base {
    public static List<UpdateAction<Product>> of(final MetaAttributes metaAttributes, final ProductUpdateScope productUpdateScope) {
        return asList(
                SetProductMetaTitle.of(metaAttributes.getMetaTitle(), productUpdateScope),
                SetProductMetaDescription.of(metaAttributes.getMetaDescription(), productUpdateScope),
                SetProductMetaKeywords.of(metaAttributes.getMetaKeywords(), productUpdateScope)
        );
    }
}
