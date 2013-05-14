package io.sphere.internal;

import io.sphere.internal.util.Log;
import io.sphere.client.model.Reference;
import io.sphere.client.model.products.BackendCategory;
import io.sphere.client.model.products.BackendProduct;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.model.*;

import java.util.ArrayList;
import java.util.List;

/** Converts products from the raw backend format into {@linkplain Product Products}.
 *  Products have references to categories resolved, as opposed to a
 *  {@linkplain BackendProduct BackendProducts} which hold raw {@linkplain Reference References}. */
public class ProductConversion {
    public static List<Product> fromBackendProducts(List<BackendProduct> rawProducts, CategoryTree categoryTree) {
        if (rawProducts == null) {
            return new ArrayList<Product>();
        }
        List<Product> result = new ArrayList<Product>(rawProducts.size());
        for (BackendProduct p : rawProducts) {
            result.add(fromBackendProduct(p, categoryTree));
        }
        return result;
    }

    public static Product fromBackendProduct(BackendProduct p, CategoryTree categoryTree) {
        List<Category> categories = new ArrayList<Category>(p.getCategories().size());
        for (Reference<BackendCategory> categoryReference : p.getCategories()) {
            Category resolved = categoryTree.getById(categoryReference.getId());
            if (resolved != null) {
                categories.add(resolved);
            } else {
                Log.warn(String.format("Product %s (%s) has an unknown category: %s", p.getId(), p.getName(), categoryReference.getId()));
            }
        }
        return new Product(
                p.getIdAndVersion(), p.getName(), p.getDescription(), p.getSlug(),
                p.getMetaTitle(), p.getMetaDescription(), p.getMetaKeywords(), p.getMasterVariant(),
                p.getVariants(), categories, p.getCatalogs(), p.getCatalog(), p.getReviewRating());
    }
}
