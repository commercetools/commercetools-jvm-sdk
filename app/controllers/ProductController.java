package controllers;

import java.util.List;

import de.commercetools.sphere.client.shop.model.*;
import play.data.Form;
import play.mvc.*;
import sphere.ShopController;
import util.CategorySDK;
import util.ProductFilter;
import util.ProductSDK;

public class ProductController extends ShopController {

	private static final Form<ProductFilter> filterForm = form(ProductFilter.class);

	public static Result all() {
		List<Category> allCategories = getAllCategories();
		List<Product> allProducts = getAllProducts();
		Category root = CategorySDK.getRoot(allCategories);
		return ok(views.html.allproductsview.render(root, allProducts));
	} 
	
	public static Result list(String categorySlug) {
		List<Category> allCategories = getAllCategories();
		Category category = CategorySDK.getCategoryBySlug(categorySlug, allCategories);
		if (category == null) {
			return notFound("Invalid category " + categorySlug);
		}
		Form<ProductFilter> boundFilterForm = filterForm.bindFromRequest();
		if (boundFilterForm.hasErrors() || boundFilterForm.get().hasErrors()) {
			flash("error", "Please correct the form below");
			List<Product> allProducts = getAllProducts();
			List<Product> products = ProductSDK.getProductsByCategory(category, allProducts);
			return badRequest(views.html.filterview.render(category, category.getChildren(), products, filterForm));
		}
		boundFilterForm.get().category = category;
		List<Product> products = boundFilterForm.get().getFilteredProducts();
		return ok(views.html.filterview.render(category, category.getChildren(), products, filterForm));
	}
		  
	public static Result filter(String categorySlug, String subcategorySlug) {
		List<Category> allCategories = getAllCategories();
		Category subcategory = CategorySDK.getCategoryBySlug(subcategorySlug, allCategories);
		if (subcategory == null) {
			return notFound("Invalid subcategory " + subcategorySlug);
		}
		Category category = CategorySDK.getCategoryBySlug(categorySlug, allCategories);
		if (category == null) {
			return notFound("Invalid category " + categorySlug);
		}		
		List<Category> subcategories = category.getChildren();
		Form<ProductFilter> boundFilterForm = filterForm.bindFromRequest();
		if (boundFilterForm.hasErrors() || boundFilterForm.get().hasErrors()) {
			flash("error", "Please correct the form below");
			List<Product> allProducts = getAllProducts();
			List<Product> products = ProductSDK.getProductsByCategory(subcategory, allProducts);
			return badRequest(views.html.filterview.render(subcategory, subcategories, products, filterForm));
		}
		boundFilterForm.get().category = subcategory;
		List<Product> products = boundFilterForm.get().getFilteredProducts();
		return ok(views.html.filterview.render(subcategory, subcategories, products, filterForm));
	}

	public static Result show(String productSlug, String categorySlug) {
        List<Product> allProducts = getAllProducts();
		List<Category> allCategories = getAllCategories();
        Product product = getProduct(ProductSDK.getProductBySlug(productSlug, allProducts).getId());
        if (product == null) {
            return notFound("Invalid product " + productSlug);
        }
		Category category;
		if (!categorySlug.isEmpty()) {
			// TODO Check if it's really one of the ancestor categories of the product
			category = CategorySDK.getCategoryBySlug(categorySlug, allCategories);
		} else {
			category = ProductSDK.getMainCategory(product);
		}
		if (category == null) {
			return notFound("Invalid product category");
		}
		return ok(views.html.productview.render(category, product));
	}
  
	// TODO Interesting shortcut
	private static List<Category> getAllCategories() {
		return CategoryController.getAllCategories();
    }

	private static List<Product> getAllProducts() {
		return sphere.products.all().expand("categories[*].ancestors[*]").expand("vendor").fetch().getResults();
	}

    private static Product getProduct(String id) {
        return sphere.products.byId(id).expand("categories[*].ancestors[*]").expand("vendor").fetch();
    }

}
