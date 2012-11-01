package de.commercetools.internal;

import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.model.products.BackendCategory;
import de.commercetools.sphere.client.model.products.BackendProduct;
import de.commercetools.sphere.client.shop.CategoryTree;
import de.commercetools.sphere.client.shop.model.*;

import java.util.ArrayList;
import java.util.List;

public class ProductConverter {
    /** Converts products from the raw backend format into {@link de.commercetools.sphere.client.shop.model.Product}s. */
    public static List<Product> convertProducts(List<BackendProduct> rawProducts, CategoryTree categoryTree) {
        if (rawProducts == null) {
            return new ArrayList<Product>();
        }
        List<Product> result = new ArrayList<Product>(rawProducts.size());
        for (BackendProduct p : rawProducts) {
            result.add(convertProduct(p, categoryTree));
        }
        return result;
    }

    /** Converts a product from the raw backend format into a {@link de.commercetools.sphere.client.shop.model.Product}. */
    public static Product convertProduct(BackendProduct p, CategoryTree categoryTree) {
        List<Category> categories = new ArrayList<Category>(p.getCategories().size());
        for (Reference<BackendCategory> categoryReference : p.getCategories()) {
            Category resolved = categoryTree.getById(categoryReference.getId());
            if (resolved != null) {
                categories.add(resolved);
            }
        }
        return new Product(
                p.getId(), p.getVersion(), p.getName(), p.getDescription(), p.getVendor(), p.getSlug(),
                p.getMetaTitle(), p.getMetaDescription(), p.getMetaKeywords(), p.getQuantityAtHand(), p.getMasterVariant(),
                p.getVariants(), categories, p.getCatalogs(), p.getCatalog());
    }
}
