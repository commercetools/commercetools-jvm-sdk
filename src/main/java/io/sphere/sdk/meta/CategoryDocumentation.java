package io.sphere.sdk.meta;


/**
 <h3 id="category-creation">Create a tree of categories</h3>
 Lets suppose we want to create this category tree (the number before the name is the externalId):
 <pre><code>{@doc.gen include file sphere-convenience/src/it/resources/category-tree-1.txt}</code></pre>

 We define the category data as CSV, we start with the top level so the parent category is before its child categories:

 <pre><code>{@doc.gen include file sphere-convenience/src/it/resources/category-import-1.csv}</code></pre>

Then we can write some script to parse the csv and create them in sphere:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#setUpData()}

 <h3 id="read-categories">Fetch all categories</h3>
<h4>Fetch all</h4>
 If categories don't change often it is a good idea to just cache them.
 You can use {@link io.sphere.sdk.queries.ExperimentalReactiveStreamUtils} to do this:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#fetchAll()}
 <h4>Fetch all with predicate</h4>
 Sometimes you want to fetch just the root categories to delete them all:
 {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#fetchRoots()}


 <h3 id="category-tree">Work with a cached tree of categories</h3>

 In <a href="#read-categories">Fetch all categories</a> you read how to obtain all categories or categories matching a predicate.
 The list of all categories is required to form a category tree:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#createCategoryTree()}

 With a {@link io.sphere.sdk.categories.CategoryTree} you can find categories by slug, by ID and search categories by their parent.

 {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#simpleCategoryTreeOperations()}





 <h3 id="category-deletion">Delete categories and trees</h3>
 <!-- deleting category with children deletes the children -->
 <h3 id="category-tree-changes">Change the category tree</h3>






 */
public class CategoryDocumentation {
    private CategoryDocumentation() {
    }
}
