package util;

import java.util.ArrayList;
import java.util.List;

import controllers.CategoryController;

import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.shop.model.Category;

public class CategorySDK {

	// TODO Use SDK instead
	public static Category getCategoryBySlug(String categorySlug,
                                             List<Category> allCategories) {
		for (Category c : allCategories) {
			if (c.getSlug() != null && c.getSlug().equals(categorySlug)) {
				return c;
			}
		}
		return null;
	}

    public static List<Category> getSubtree(Category current) {
        return getSubtree(current, new ArrayList<Category>());
    }

    public static List<Category> getSubtree(Category current, List<Category> grandchildren) {
        for (Category c : current.getChildren()) {
            grandchildren.add(c);
            grandchildren.addAll(getSubtree(c));
        }
        return grandchildren;
    }

	// TODO Use SDK instead
	public static Category getRoot(List<Category> allCategories) {
		for (Category c : allCategories) {
			if (isRoot(c)) {
				return c;
			}
		}
		return null;
	}
	
	public static Category getRoot(Category current) {
		if (isRoot(current)) {
			return current;
		}
		return getRoot(CategoryController.getAllCategories());
	}

	public static boolean isRoot(Category category) {
		return category.getName().equals("All categories");
	}

	public static void buildChildren(List<Category> allCategories) {
		buildChildren(getRoot(allCategories), allCategories);
	}

	public static void buildChildren(Category root, List<Category> allCategories) {
		for (Category c : allCategories) {
			if (!c.getParent().isEmpty()) {
				if (c.getParent().getId().equals(root.getId())) {
					root.getChildren().add(c);
					buildChildren(c, allCategories);
				}
			}
		}
	}

	public static String getPath(Category current) {
		String path = "";
		for (Reference<Category> rc : current.getAncestors()) {
			if (!isRoot(rc.get())) {
				path += "/" + rc.get().getSlug();
			}
		}
		path += "/" + current.getSlug();
		return path.substring(1);
	}
	
	public static String getPath(Category current, List<Reference<Category>> tree) {
		String path = "";
		for (Reference<Category> rc : tree) {
			if (rc.get().getId() == current.getId()) {
				break;
			}
			if (!isRoot(rc.get())) {
				path += "/" + rc.get().getSlug();
			}
		}
		path += "/" + current.getSlug();
		return path.substring(1);
	}

}
