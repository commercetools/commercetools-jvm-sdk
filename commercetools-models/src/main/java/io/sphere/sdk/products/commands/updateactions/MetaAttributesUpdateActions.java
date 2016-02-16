package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.products.Product;

import java.util.List;

import static java.util.Arrays.asList;

/**
 *  <p>Create update actions to set the SEO attributes title, description and keywords altogether:</p>
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setMetaAttributes()}
 *
 * {@doc.gen intro}
 */
public final class MetaAttributesUpdateActions extends Base {
    private MetaAttributesUpdateActions() {
    }

    public static List<UpdateAction<Product>> of(final MetaAttributes metaAttributes) {
        return asList(
                SetMetaTitle.of(metaAttributes.getMetaTitle()),
                SetMetaDescription.of(metaAttributes.getMetaDescription()),
                SetMetaKeywords.of(metaAttributes.getMetaKeywords())
        );
    }
}
