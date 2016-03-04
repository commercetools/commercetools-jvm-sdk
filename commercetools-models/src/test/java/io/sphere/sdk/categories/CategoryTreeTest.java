package io.sphere.sdk.categories;

import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryTreeTest {
    private static final Comparator<Category> byNameComparator = (Category left, Category right) -> left.getName().get(Locale.ENGLISH).compareTo(right.getName().get(Locale.ENGLISH));
    private final Locale locale = Locale.ENGLISH;
    private final List<String> rootIds = asList("0", "1", "2", "3");
    private final List<String> childIds = asList("a", "b", "c", "d", "e");
    private final List<String> grandchildIds = asList("u", "v", "w", "x");
    private static final CategoryTree CATEGORY_TREE = CategoryTree.of(SphereJsonUtils.readObjectFromResource("categoryQueryResult.json", CategoryQuery.resultTypeReference()).getResults());
    private static final Category handBags = CATEGORY_TREE.findById("9a584ee8-a45a-44e8-b9ec-e11439084687").get();
    private static final Category clutches = CATEGORY_TREE.findById("a9c9ebd8-e6ff-41a6-be8e-baa07888c9bd").get();
    private static final Category satchels = CATEGORY_TREE.findById("30d79426-a17a-4e63-867e-ec31a1a33416").get();
    private static final Category shoppers = CATEGORY_TREE.findById("bd83e288-77de-4c3a-a26c-8384af715bbb").get();
    private static final Category wallets = CATEGORY_TREE.findById("d2f9a2da-db3e-4ee8-8192-134ebbc7fe4a").get();
    private static final Category backpacks = CATEGORY_TREE.findById("46249239-8f0f-48a9-b0a0-d29b37fc617f").get();
    private static final Category slingBags = CATEGORY_TREE.findById("8e052705-7810-4528-ba77-00094b87a69a").get();

    @Test(expected = NullPointerException.class)
    public void checkForNonNullArgumentsInFactoryMethod() throws Exception {
        CategoryTree.of(null);
    }

    @Test
    public void demoGetRoots() {
        final CategoryTree tree = createAbcCategoryTree();
        assertThat(tree.getRoots()).extracting(Resource::getId).containsOnly("A", "B", "C");
    }

    @Test
    public void getAllAsFlatList() {
        final CategoryTree tree = createAbcCategoryTree();
        assertThat(tree.getAllAsFlatList())
            .hasSize(39)
            .extracting(cat -> cat.getId())
                //don't rely on the order!
            .containsOnly("A", "B", "C",
                    "A-1", "A-2", "A-3",
                    "B-1", "B-2", "B-3",
                    "C-1", "C-2", "C-3",
                    "A-1-1", "A-1-2", "A-1-3",
                    "A-2-1", "A-2-2", "A-2-3",
                    "A-3-1", "A-3-2", "A-3-3",
                    "B-1-1", "B-1-2", "B-1-3",
                    "B-2-1", "B-2-2", "B-2-3",
                    "B-3-1", "B-3-2", "B-3-3",
                    "C-1-1", "C-1-2", "C-1-3",
                    "C-2-1", "C-2-2", "C-2-3",
                    "C-3-1", "C-3-2", "C-3-3");
    }

    @Test
    public void getSiblings() {
        final CategoryTree tree = createAbcCategoryTree();
        final Category b2 = tree.findById("B-2").get();
        final Category b1 = tree.findById("B-1").get();
        final Category c2 = tree.findById("C-2").get();
        final Category c21 = tree.findById("C-2-1").get();
        final Category c22 = tree.findById("C-2-2").get();
        final Category c23 = tree.findById("C-2-3").get();
        final Category b = tree.findById("B").get();
        final Category a = tree.findById("A").get();
        assertThat(tree.findSiblings(singletonList(b2)))
                .extracting(c -> c.getId())
                .as("one category will provide all its siblings (no recursion) without including itself")
                .containsOnly("B-1", "B-3");
        assertThat(tree.findSiblings(asList(b1, b2))).extracting(c -> c.getId())
                .as("providing multiple categories which are siblings exclude themselves from the result list")
                .containsOnly("B-3");
        assertThat(tree.findSiblings(asList(b2, c2))).extracting(c -> c.getId())
                .as("using non-sibling categories as arguments results in getting all their siblings in one list")
                .containsOnly("B-1", "B-3", "C-1", "C-3");
        assertThat(tree.findSiblings(asList(b, b1))).extracting(c -> c.getId())
                .as("even on different levels it will provide the siblings and will filter out all input categories")
                .containsOnly("A", "C", "B-2", "B-3");
        assertThat(tree.findSiblings(asList(a, b))).extracting(c -> c.getId()).containsOnly("C");
        assertThat(tree.findSiblings(asList(c21, c22, c23))).extracting(c -> c.getId())
                .as("if there are no siblings available")
                .isEmpty();
    }

    @Test
    public void getSubtreeFromRootCategory() {
        final CategoryTree tree = createAbcCategoryTree();
        final Category category = tree.findById("B").get();

        final CategoryTree subtree = tree.getSubtree(singletonList(category));

        assertThat(subtree.getAllAsFlatList()).hasSize(13);
        assertThat(subtree.findById(category.getId())).isPresent();
        assertThat(subtree.getRoots()).hasSize(1);
        assertThat(subtree.getSubtreeRoots()).isEqualTo(subtree.getRoots());
        assertThat(subtree.findById("B-1")).isPresent();
        assertThat(subtree.findById("C-1")).isEmpty();
    }

    @Test
    public void getSubtreeForRecursiveProblem() {
        final CategoryTree tree = createAbcCategoryTree();
        final Category a = tree.findById("A").get();
        final Category b = tree.findById("B").get();
        final Category b1 = tree.findById("B-1").get();
        assertThatThrownBy(() -> tree.getSubtree(asList(a, b, b1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("category of ID [B-1] cannot be subtree root and descendant of [B]");
    }

    @Test
    public void getSubtree() {
        final CategoryTree tree = createAbcCategoryTree();
        final Category a = tree.findById("A").get();
        final Category b1 = tree.findById("B-1").get();
        final Category c22 = tree.findById("C-2-2").get();

        final CategoryTree subtree = tree.getSubtree(asList(a, b1, c22));
        assertThat(subtree.getRoots()).extracting(c -> c.getId())
                .as("roots are still categories which have no parent and are included in this tree")
                .containsOnly("A")
                .doesNotContain("B-1", "C-2-2", "B", "C");
        assertThat(subtree.getSubtreeRoots()).extracting(c -> c.getId())
                .as("subtree roots are the categories at the top," +
                        "no matter if they have a parent reference")
                .containsOnly("A", "B-1", "C-2-2")
                .doesNotContain("B", "C", "B-2");

        assertThat(subtree.findById("A")).isPresent();
        assertThat(subtree.findById("B-1")).isPresent();
        assertThat(subtree.findById("C-2-2")).isPresent();
        assertThat(subtree.findById("B-1-2")).isPresent();
        assertThat(subtree.findById("C")).isEmpty();
        assertThat(subtree.findById("B-2")).isEmpty();

        assertThat(subtree.findByExternalId("external-id-C-2-2")).isPresent();
        assertThat(subtree.findByExternalId("external-id-B-2")).isEmpty();

        assertThat(subtree.findBySlug(Locale.ENGLISH, "slug-C-2-2")).isPresent();
        assertThat(subtree.findBySlug(Locale.ENGLISH, "slug-B-2")).isEmpty();

        assertThat(subtree.getAllAsFlatList()).hasSize(18);

        assertThat(subtree.findChildren(b1)).hasSize(3);
        assertThat(subtree.findChildren(tree.findById("C").get())).hasSize(0);
        assertThat(subtree.findChildren(tree.findById("C-2").get()))
                .as("C-2 is not included in the tree but its direct child C-2-2")
                .hasSize(1);

        assertThat(subtree.findSiblings(singletonList(a))).hasSize(0);
        assertThat(subtree.findSiblings(singletonList(c22))).hasSize(0);
        assertThat(subtree.findSiblings(singletonList(tree.findById("B-1-1").get()))).hasSize(2);

        final CategoryTree b1Subtree = subtree.getSubtree(singletonList(b1));
        assertThat(b1Subtree.getAllAsFlatList()).hasSize(4);
        assertThat(b1Subtree.getRoots()).hasSize(0);
        assertThat(b1Subtree.getSubtreeRoots()).hasSize(1);
    }

    @Test
    public void getSubtreeRoots() {
        final CategoryTree tree = createAbcCategoryTree();
        final Category a = tree.findById("A").get();
        final Category b1 = tree.findById("B-1").get();
        final Category c22 = tree.findById("C-2-2").get();

        final CategoryTree subtree = tree.getSubtree(asList(a, b1, c22));
        assertThat(subtree.getRoots()).extracting(c -> c.getId())
                .as("roots are still categories which have no parent and are included in this tree")
                .containsOnly("A")
                .doesNotContain("B-1", "C-2-2", "B", "C");
        assertThat(subtree.getSubtreeRoots()).extracting(c -> c.getId())
                .as("subtree roots are the categories at the top," +
                        "no matter if they have a parent reference")
                .containsOnly("A", "B-1", "C-2-2")
                .doesNotContain("B", "C", "B-2");
    }

    @Test
    public void findChildren() {
        final CategoryTree tree = createAbcCategoryTree();
        final Category category = tree.findById("B").get();
        final List<Category> children = tree.findChildren(category);
        assertThat(children).extracting(Resource::getId)
                .as("only direct children are present")
                .containsOnly("B-1", "B-2", "B-3");
    }

    @Test
    public void findChildrenLeaf() {
        final CategoryTree tree = createAbcCategoryTree();
        final Category category = tree.findById("B-1-1").get();
        final List<Category> children = tree.findChildren(category);
        assertThat(children).as("leaf nodes produce empty lists").isEmpty();
    }

    @Test
    public void findByExternalId() {
        final CategoryTree tree = createAbcCategoryTree();
        final Category category = tree.findById("B-2-3").get();
        final String externalId = "external-id-B-2-3";
        assertThat(category.getExternalId()).isEqualTo(externalId);

        final Optional<Category> optional = tree.findByExternalId(externalId);
        assertThat(optional).isPresent().contains(category);
    }

    @Test
    public void demoFindByIdFound() {
        final CategoryTree tree = createAbcCategoryTree();
        final Optional<Category> optional = tree.findById("B-2-3");
        assertThat(optional).isPresent();
        assertThat(optional.get().getId()).isEqualTo("B-2-3");
    }

    @Test
    public void demoFindByIdNotFound() {
        final CategoryTree tree = createAbcCategoryTree();
        final Optional<Category> optional = tree.findById("id-which-does-not-exist");
        assertThat(optional).isEmpty();

        assertThatThrownBy(() -> optional.get()).isInstanceOf(NoSuchElementException.class);
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
    public void getRootAncestor() throws Exception {
        final CategoryTree tree = createAbcCategoryTree();
        final Category a = tree.findById("A").get();
        final Category a1 = tree.findById("A-1").get();
        final Category a23 = tree.findById("A-2-3").get();
        assertThat(tree.getRootAncestor(a)).isEqualTo(a);
        assertThat(tree.getRootAncestor(a1)).isEqualTo(a);
        assertThat(tree.getRootAncestor(a23)).isEqualTo(a);
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

    @Test
    public void siblingsOfShoulderBags() throws Exception {
        test(singletonList(satchels.toReference()),
                siblings -> assertThat(siblings).containsExactly(clutches, shoppers, handBags, wallets, backpacks, slingBags));
    }

    @Test
    public void siblingsOfHandBags() throws Exception {
        test(singletonList(handBags.toReference()),
                siblings -> assertThat(siblings).containsExactly(clutches, satchels, shoppers, wallets, backpacks, slingBags));
    }

    @Test
    public void siblingsOfCombined() throws Exception {
        test(asList(satchels.toReference(), handBags.toReference()),
                siblings -> assertThat(siblings).containsExactly(clutches, shoppers, wallets, backpacks, slingBags));
    }

    private void test(final List<Reference<Category>> categoryRefs, final Consumer<List<Category>> test) {
        final List<Category> categories = categoryRefs.stream()
                .map(c -> CATEGORY_TREE.findById(c.getId()).get())
                .collect(toList());
        final List<Category> siblings = CATEGORY_TREE.findSiblings(categories);
        test.accept(siblings);
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

    private static CategoryTree createAbcCategoryTree() {
        final List<Category> rootCategories = Stream.of("A", "B", "C")
                .map(id -> CategoryBuilder.of(id, en("name " + id), en("slug-" + id)).externalId("external-id-" + id).build())
                .collect(toList());
        final List<Category> secondLevelCategories = rootCategories.stream()
                .flatMap(parent -> IntStream.range(1, 4)
                        .mapToObj(i -> {
                            final String id = parent.getId() + "-" + i;
                            return CategoryBuilder.of(id, en("name " + id), en("slug-" + id)).parent(parent).ancestors(singletonList(parent.toReference())).externalId("external-id-" + id).build();
                        }))
                .collect(Collectors.toList());
        final List<Category> thirdLevelCategories = secondLevelCategories.stream()
                .flatMap(parent -> IntStream.range(1, 4)
                        .mapToObj(i -> {
                            final List<Reference<Category>> ancestors = new LinkedList<Reference<Category>>();
                            ancestors.addAll(parent.getAncestors());
                            ancestors.add(parent.toReference());
                            final String id = parent.getId() + "-" + i;
                            return CategoryBuilder.of(id, en("name " + id), en("slug-" + id)).parent(parent).ancestors(ancestors).externalId("external-id-" + id).build();
                        }))
                .collect(Collectors.toList());
        final List<Category> all = new LinkedList<>();
        all.addAll(rootCategories);
        all.addAll(secondLevelCategories);
        all.addAll(thirdLevelCategories);
        assertThat(all).hasSize(39);
        return CategoryTree.of(all);
    }
}
