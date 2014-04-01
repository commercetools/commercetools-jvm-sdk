package io.sphere.internal;

import java.util.ArrayList;
import java.util.List;

import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.LocalizedString;
import io.sphere.client.model.products.*;
import io.sphere.client.shop.ApiMode;
import io.sphere.internal.util.Log;
import io.sphere.client.model.Reference;
import io.sphere.client.shop.CategoryTree;
import io.sphere.client.shop.model.*;


/** Converts products from the raw backend format into {@linkplain Product Products}.
 *  Products have references to categories resolved, as opposed to a
 *  {@linkplain io.sphere.client.model.products.BackendProductProjection BackendProducts} which hold raw {@linkplain Reference References}. */
public class ProductConversion {
    public static List<Product> fromBackendProductProjections(List<BackendProductProjection> rawProducts, CategoryTree categoryTree) {
        if (rawProducts == null) {
            return new ArrayList<Product>();
        }
        List<Product> result = new ArrayList<Product>(rawProducts.size());
        for (BackendProductProjection p : rawProducts) {
            result.add(fromBackendProductProjection(p, categoryTree));
        }
        return result;
    }

    public static Product fromBackendProductProjection(BackendProductProjection p, CategoryTree categoryTree) {
        List<Category> categories = getCategories(p.getCategories(), categoryTree, p.getId(), p.getName());

        return new Product(
                p.getIdAndVersion(), p.getName(), p.getDescription(), p.getSlug(),
                p.getMetaTitle(), p.getMetaDescription(), p.getMetaKeywords(), p.getMasterVariant(),
                p.getVariants(), categories, p.getCatalogs(), p.getCatalog(), p.getReviewRating(), p.getTaxCategory());
    }

    public static Product fromBackendProduct(BackendProduct p, CategoryTree categoryTree, ApiMode apiMode) {
        ProductData data;
        if (apiMode == ApiMode.Staged) data = p.getMasterData().getStaged();
        else data = p.getMasterData().getCurrent();
        
        List<Category> categories = getCategories(data.getCategories(), categoryTree, p.getId(), data.getName());
        Reference<Catalog> catalog = EmptyReference.create("catalog");
        
        return new Product(
                p.getIdAndVersion(), data.getName(), data.getDescription(), data.getSlug(),
                data.getMetaTitle(), data.getMetaDescription(), data.getMetaKeywords(), data.getMasterVariant(),
                data.getVariants(), categories, p.getCatalogs(), catalog, p.getReviewRating(), p.getTaxCategory());
    }  
    
    private static List<Category> getCategories(
            List<Reference<BackendCategory>> refs, 
            CategoryTree categoryTree, 
            String productId,
            LocalizedString productName) {
        
        List<Category> categories = new ArrayList<Category>(refs.size());
        for (Reference<BackendCategory> categoryReference : refs) {
            Category resolved = categoryTree.getById(categoryReference.getId());
            if (resolved != null) {
                categories.add(resolved);
            } else {
                Log.warn(String.format("Product %s (%s) has an unknown category: %s", productId, productName, categoryReference.getId()));
            }
        }
        return categories;
    }
}
