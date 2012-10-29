package util;


import de.commercetools.sphere.client.FilterExpression;
import de.commercetools.sphere.client.FilterExpressions;
import de.commercetools.sphere.client.SearchRequestBuilder;
import de.commercetools.sphere.client.shop.model.*;
import sphere.Sphere;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductFilter {

	public Category category;
	public Integer minPrice;
	public Integer maxPrice;

	// TODO Don't care now about reasons, better show them with JS
	public boolean hasErrors() {
		return hasErrorsPrice() || hasErrorsCategory();
	}

	public boolean hasErrorsCategory() {
        // TODO Any suggestion?
		return false;
	}

	public boolean hasErrorsPrice() {
		return hasErrorsMinPrice() || hasErrorsMaxPrice() || hasErrorsMoneyRange();
	}

	private boolean hasErrorsMinPrice() {
		if (minPrice != null) {
			if (minPrice < 0) {
				return true;
			}
		}
		return false;
	}

	private boolean hasErrorsMaxPrice() {
		if (maxPrice != null) {
			if (maxPrice < 0) {
				return true;
			}
		}
		return false;
	}

	private boolean hasErrorsMoneyRange() {
		if (maxPrice != null && minPrice != null) {
			if (minPrice > maxPrice) {
				return true;
			}
		}
		return false;
	}

	public FilterExpression getPriceFilter() {
		if ((minPrice == null && maxPrice == null) || hasErrorsPrice()) {
			return null;
		}
		return new FilterExpressions.MoneyAttribute.Range("variants.price",
				minPrice == null ? null : new BigDecimal(minPrice.doubleValue()),
				maxPrice == null ? null : new BigDecimal(maxPrice.doubleValue()));
	}

	public FilterExpression getCategoryFilter() {
		if (category == null || hasErrorsCategory()) {
			return null;
		}
        List<String> categoryIds = new ArrayList<String>();
        categoryIds.add(category.getId());
        for (Category c : CategorySDK.getSubtree(category)) {
            categoryIds.add(c.getId());
        }
		return new FilterExpressions.CategoryAnyOf(categoryIds);
	}

	public List<Product> getFilteredProducts() {
		SearchRequestBuilder<Product> productSearch;
		Collection<FilterExpression> filters = new ArrayList<FilterExpression>();
		filters.add(getPriceFilter());
		filters.add(getCategoryFilter());
		filters.removeAll(Collections.singletonList(null));
		if (filters.isEmpty()) {
			productSearch = Sphere.getSphereClient().products.all();
		} else {
			productSearch = Sphere.getSphereClient().products.filter(filters);
		}
		// TODO Add more filtering options
		return productSearch.expand("vendor").fetch().getResults();
	}

}
