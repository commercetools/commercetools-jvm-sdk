package io.sphere.sdk.categories;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTreeTest {
    private static final Comparator<Category> byNameComparator = (Category left, Category right) -> left.getName().get(Locale.ENGLISH).compareTo(right.getName().get(Locale.ENGLISH));
    private final Locale locale = Locale.ENGLISH;
    private final List<String> rootIds = asList("0", "1", "2", "3");
    private final List<String> childIds = asList("a", "b", "c", "d", "e");
    private final List<String> grandchildIds = asList("u", "v", "w", "x");

    @Test(expected = NullPointerException.class)
    public void checkForNonNullArgumentsInFactoryMethod() throws Exception {
        CategoryTree.of(null);
    }

    @Test
    public void createEmptyCategoryHierarchy() throws Exception {
        final CategoryTree tree = CategoryTree.of(Collections.emptyList());
        assertThat(tree.getAllAsFlatList()).hasSize(0);
        assertThat(tree.getRoots()).hasSize(0);
    }

    @Test
    public void createFlatHierarchy() throws Exception {
        final List<String> ids = asList("0", "1", "2", "3");
        final List<Category> categories = ids.stream().map(id -> newOrphanCategory(id)).collect(toList());
        final CategoryTree tree = CategoryTree.of(categories);
        assertThat(extractIdSet(tree.getAllAsFlatList())).isEqualTo(extractIdSet(categories));
        assertThat(extractIdSet(tree.getRoots())).isEqualTo(extractIdSet(categories));
    }

    @Test
    public void createHierarchy() throws Exception {
        final List<Category> allCategories = createCategoryHierarchyAsFlatList();
        final CategoryTree tree = CategoryTree.of(allCategories);
        assertThat(extractIdSet(tree.getAllAsFlatList()))
                .overridingErrorMessage("all categories are present").isEqualTo(extractIdSet(allCategories));
        assertThat(extractIdSet(tree.getRoots()))
                .isEqualTo(new HashSet<>(rootIds));
        final List<Category> nameSortedRoots = byNameSorted(tree);
        final Category category0 = nameSortedRoots.get(0);
        assertThat(category0.getId()).isEqualTo(rootIds.get(0));
        final List<Category> childrenOf0 = tree.findChildren(category0);
        assertThat(childrenOf0).hasSize(childIds.size());
        final List<Category> sortedChildren = childrenOf0.stream().sorted(byNameComparator).collect(toList());
        assertThat(sortedChildren.get(1).getId()).isEqualTo("0b");
        final List<Category> sortedGrandChildren = tree.findChildren(sortedChildren.get(1)).stream().sorted(byNameComparator).collect(toList());
        assertThat(extractIdList(sortedGrandChildren)).isEqualTo(asList("0bu", "0bv", "0bw", "0bx"));
    }

    @Test
    public void searchById() throws Exception {
        final CategoryTree tree = CategoryTree.of(createCategoryHierarchyAsFlatList());
        assertThat(tree.findById("0bu").get().getId()).isEqualTo("0bu");
        assertThat(tree.findById("not-present")).isEqualTo(Optional.empty());
    }

    @Test
    public void searchBySlug() throws Exception {
        final CategoryTree tree = CategoryTree.of(createCategoryHierarchyAsFlatList());
        assertThat(tree.findBySlug(locale, "slug-0bu").get().getId()).isEqualTo("0bu");
        final Locale absentLocale = Locale.GERMAN;
        assertThat(tree.findBySlug(absentLocale, "slug-0bu")).isEqualTo(Optional.empty());
    }

    public List<Category> createCategoryHierarchyAsFlatList() {
        final List<Category> rootCategories = rootIds.stream().map(id -> newOrphanCategory(id)).collect(toList());
        final List<Category> children = createChildren(childIds, rootCategories);
        final List<Category> grandchildren = createChildren(grandchildIds, children);
        final List<Category> allCategories = new LinkedList<>();
        allCategories.addAll(rootCategories);
        allCategories.addAll(children);
        allCategories.addAll(grandchildren);
        assertThat(allCategories).hasSize(rootIds.size() + rootIds.size() * childIds.size() + rootIds.size() * childIds.size() * grandchildIds.size());
        return allCategories;
    }

    private static List<String> extractIdList(final List<Category> categories) {
        return categories.stream().map(c -> c.getId()).collect(toList());
    }

    private static Set<String> extractIdSet(final List<Category> categories) {
        return new HashSet<>(extractIdList(categories));
    }

    private List<Category> createChildren(final List<String> childIds, final List<Category> rootCategories) {
        final List<Category> children = new LinkedList<>();
        rootCategories.stream().forEach(parent ->
            childIds.stream().forEach(childId -> {
                    final Category child = new CategoryWrapper(newOrphanCategory(parent.getId() + childId)) {
                        @Override
                        public Reference<Category> getParent() {
                            return parent.toReference();
                        }
                    };
                    children.add(child);
                }
            )
        );
        return children;
    }

    private static Category newOrphanCategory(final String id) {
        return CategoryBuilder.of(id, en("name " + id), en("slug-" + id)).build();
    }

    private static LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }

    private List<Category> byNameSorted(final CategoryTree tree1) {
        return tree1.getRoots().stream().sorted(byNameComparator).collect(toList());
    }
}
