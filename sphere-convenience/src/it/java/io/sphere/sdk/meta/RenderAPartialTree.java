package io.sphere.sdk.meta;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.models.Reference;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public final class RenderAPartialTree {
    private static final Comparator<Category> EXTERNALID_COMPARATOR = Comparator.comparing(c -> Integer.parseInt(c.getExternalId()));

    private RenderAPartialTree() {
    }

    public static void demoForRendering(final CategoryTree categoryTree) {
        final Category currentCategory = categoryTree.findBySlug(ENGLISH, "tshirts-men").get();
        final List<Reference<Category>> ancestorReferences = currentCategory.getAncestors().stream()
                .skip(1)//remove top level category
                .collect(toList());
        final StringBuilder stringBuilder = new StringBuilder();
        appendToBuilder(ancestorReferences.get(0), stringBuilder, categoryTree, 0, currentCategory);
        final String actual = stringBuilder.toString();
        assertThat(actual).isEqualTo(
                        "1 men\n" +
                        "    3 clothing\n" +
                        "        ***7 t-shirts***\n" +
                        "        8 jeans\n" +
                        "    4 shoes\n");
    }


    private static void appendToBuilder(final Identifiable<Category> categoryReference, final StringBuilder stringBuilder, final CategoryTree categoryTree, final int level, final Category selectedCategory) {
        final Category category = categoryTree.findById(categoryReference.getId()).get();
        final String name = category.getName().get(ENGLISH);
        final String externalId = category.getExternalId();
        final String offset = StringUtils.repeat(' ', level * 4);

        stringBuilder.append(offset);
        if (categoryReference.getId().equals(selectedCategory.getId())) {
            stringBuilder.append("***");
        }
        stringBuilder.append(externalId).append(" ").append(name);
        if (categoryReference.getId().equals(selectedCategory.getId())) {
            stringBuilder.append("***");
        }
        stringBuilder.append("\n");

        final Predicate<Category> isAncestor = cat -> selectedCategory.getAncestors().stream().anyMatch(anc -> anc.getId().equals(cat.getId()));
        final Predicate<Category> isOnHigherLevelThanCurrent = cat -> cat.getAncestors().size() < selectedCategory.getAncestors().size();
        final Predicate<Category> isSibling = cat -> cat.getAncestors().equals(selectedCategory.getAncestors());

        final List<Category> children = categoryTree.findChildren(category);
        children.stream()
                .filter(isAncestor.or(isOnHigherLevelThanCurrent).or(isSibling))
                .sorted(EXTERNALID_COMPARATOR)
                .forEach(child -> appendToBuilder(child, stringBuilder, categoryTree, level + 1, selectedCategory));
    }
}
