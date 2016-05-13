package io.sphere.sdk.meta;


import io.sphere.sdk.queries.QueryExecutionUtils;

/**
 <h3 id="category-creation">Create a tree of categories</h3>
 Lets suppose we want to create this category tree (the number before the name is the externalId):
 <pre><code>{@include.file commercetools-convenience/src/test/resources/category-tree-1.txt}</code></pre>

 To define the category tree in a CSV format we start with the top level categories so that each parent category appears before its child categories:

 <pre><code>{@include.file commercetools-convenience/src/test/resources/category-import-1.csv}</code></pre>

Then we can write some script to parse the csv and create them in sphere:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#setUpData()}

 <h3 id="read-categories">Fetch all categories</h3>
<h4>Fetch all</h4>
 If categories don't change often it is a good idea to just cache them.
 You can use {@link QueryExecutionUtils} to do this:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#fetchAll()}
 <h4>Fetch all with predicate</h4>
 Sometimes you want to fetch just the root categories to delete them all:
 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#fetchRoots()}


 <h3 id="category-tree">Work with a cached tree of categories</h3>

 In <a href="#read-categories">Fetch all categories</a> you read how to obtain all categories or categories matching a predicate.
 The list of all categories is required to form a category tree:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#createCategoryTree()}

 With a {@link io.sphere.sdk.categories.CategoryTree} you can find categories by slug, by ID and search categories by their parent.

 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#simpleCategoryTreeOperations()}

 <h4>Render a whole tree</h4>
 {@include.example io.sphere.sdk.meta.CategoryTreeTextRepresentation}

 <h4>Render a breadcrumb</h4>

 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#createAViewForACategoryBreadCrumb()}

 <h4>Render a partial tree</h4>
 {@include.example io.sphere.sdk.meta.RenderAPartialTree}



 <h3 id="category-deletion">Delete categories and trees</h3>

 Removing a category also means to delete all child categories of this category:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#categoryDeletionIsRecursive()}

 <h3 id="category-tree-changes">Change the category tree</h3>

 It is possible to reorganize category trees by changing the reference to the parent category.
 But it is not possible to transform a category with a parent to a root category. If you need this, you need to create a new category and move the subcategories there.

 <p>In the following example we move the mens-clothing category to the top category:</p>

 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#moveCategoryTree()}


 <h3 id="find-product-by-category">Find products in a category tree</h3>

 In order to fetch products that belong to a category or to a category tree you specify the category IDs in the query predicate:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationIntegrationTest#demoForFindProducts()}

 <p>A detailed explanation how to filter and facet for products in categories can be found in {@link io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModel}
 and {@link io.sphere.sdk.products.search.ProductCategoriesIdTermFacetSearchModel}.</p>


 */
public final class CategoryDocumentation {
    private CategoryDocumentation() {
    }
}
