package io.sphere.sdk.meta;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryTree;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;

import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public final class CategoryTreeTextRepresentation {
    private static final Comparator<Category> EXTERNALID_COMPARATOR = Comparator.comparing(c -> Integer.parseInt(c.getExternalId()));

    private CategoryTreeTextRepresentation() {
    }

    public static void demoForRendering(final CategoryTree categoryTree) {
        final String actual = visualizeTree(categoryTree);
        assertThat(actual).isEqualTo(
                        "0 top\n" +
                        "    1 men\n" +
                        "        3 clothing\n" +
                        "            7 t-shirts\n" +
                        "            8 jeans\n" +
                        "        4 shoes\n" +
                        "            9 sandals\n" +
                        "            10 boots\n" +
                        "    2 women\n" +
                        "        5 clothing\n" +
                        "            11 t-shirts\n" +
                        "            12 jeans\n" +
                        "        6 shoes\n" +
                        "            13 sandals\n" +
                        "            14 boots\n");
    }

    public static String visualizeTree(final CategoryTree categoryTree) {
        final StringBuilder stringBuilder = new StringBuilder();
        categoryTree.getRoots()
                .forEach(category -> appendToBuilder(category, stringBuilder, categoryTree, 0));
        return stringBuilder.toString();
    }


    private static void appendToBuilder(final Category category, final StringBuilder stringBuilder, final CategoryTree categoryTree, final int level) {
        final String name = category.getName().get(ENGLISH);
        final String externalId = category.getExternalId();
        final String offset = StringUtils.repeat(' ', level * 4);
        stringBuilder.append(offset).append(externalId).append(" ").append(name).append("\n");
        final List<Category> children = categoryTree.findChildren(category);
        children.stream()
                .sorted(EXTERNALID_COMPARATOR)
                .forEach(child -> appendToBuilder(child, stringBuilder, categoryTree, level + 1));
    }
}
