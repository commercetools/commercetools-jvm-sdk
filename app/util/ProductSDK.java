package util;

import java.util.ArrayList;
import java.util.List;

import controllers.ProductController;

import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.shop.model.Category;
import de.commercetools.sphere.client.shop.model.Product;

public class ProductSDK {
	
	// TODO Use SDK instead
	public static List<Product> getProductsByCategory(Category category, List<Product> allProducts) {
		return getProductByCategory(category, allProducts, new ArrayList<Product>());
	}

	// TODO Use search instead when expand is available
	public static List<Product> getProductByCategory(Category category, List<Product> allProducts, List<Product> selectedProducts) {
		for (Category c : category.getChildren()) {
			selectedProducts = getProductByCategory(c, allProducts, selectedProducts);
		}
		// For each product we check if it's contained in our category
		for (Product p : allProducts) {
			for (Reference<Category> rc : p.getCategories()) {
				if (rc.get().getId().equals(category.getId())) {
					selectedProducts.add(p);
				}
			}
		}
		return selectedProducts;
	}

    public static Product getProductBySlug(String productSlug, List<Product> allProducts) {
        for (Product p : allProducts) {
            if (p.getSlug() != null && p.getSlug().equals(productSlug)) {
                return p;
            }
        }
        return null;
    }

    public static String getVendorName(Product product) {
        if(product.getVendor().isEmpty()) {
            return "None";
        }
        return product.getVendor().get().getName();
    }

	public static String getMainImageURL(Product product) {
		if (product.getImageURLs().isEmpty()) {
			// TODO Should be no photo picture
			return "http://www.placehold.it/300x200";
		}
		return product.getImageURLs().get(0);
	}

	public static Category getMainCategory(Product product) {
		if (product.getCategories().size() > 0) {
			// TODO Any better criteria to choose default category?
			return product.getCategories().get(0).get();
		}
		return null;
	}

}
