package controllers;

import java.util.List;

import de.commercetools.sphere.client.shop.model.Category;
import play.mvc.*;
import sphere.ShopController;
import util.CategorySDK;

public class CategoryController extends ShopController {

	public static Result select(String categories) {
		// TODO Maybe check if main category is parent of the selected category
		String[] categorySlugs = categories.split("/");
		switch (categorySlugs.length) {
			case 1:
				return CategoryController.show(categorySlugs[0]);
			case 2:
				return ProductController.list(categorySlugs[1]);
			case 3:
				return ProductController.filter(categorySlugs[1], categorySlugs[2]);
		}
		return redirect(routes.CategoryController.list());
	}

	public static Result all() {
		List<Category> allCategories = getAllCategories();
		Category root = CategorySDK.getRoot(allCategories);
		return ok(views.html.sitemapview.render(root));
	}

	public static Result list() {
		List<Category> allCategories = getAllCategories();
		List<Category> children = CategorySDK.getRoot(allCategories).getChildren();
		Category root = CategorySDK.getRoot(allCategories);
		return ok(views.html.categoriesview.render(root, children));
	}
	
	public static Result show(String categorySlug) {
		List<Category> allCategories = getAllCategories();
		Category category = CategorySDK.getCategoryBySlug(categorySlug, allCategories);
		if (category == null) {
			return notFound("Invalid main category " + categorySlug);
		}		
		return ok(views.html.categoryview.render(category, category.getChildren()));
	}  
	
	// TODO Interesting shortcut
	public static List<Category> getAllCategories() {
		List<Category> allCategories = sphere.categories.all().expand("ancestors[*]").pageSize(100).fetch().getResults();
		CategorySDK.buildChildren(allCategories);
		return allCategories;
	}
}
