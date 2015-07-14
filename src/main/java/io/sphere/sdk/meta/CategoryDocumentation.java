package io.sphere.sdk.meta;


/**
 <h3 id="category-creation">Create a tree of categories</h3>
 Lets suppose we want to create this category tree (the number before the name is the externalId):
 <pre><code>{@doc.gen include file sphere-convenience/src/it/resources/category-tree-1.txt}</code></pre>

 We define the category data as CSV, we start with the top level so the parent category is before its child categories:

 <pre><code>{@doc.gen include file sphere-convenience/src/it/resources/category-import-1.csv}</code></pre>

Then we can write some script to parse the csv and create them in sphere:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#setUpData()}

 <h3 id="read-all-categories">Fetch all categories</h3>
<h4>Fetch all</h4>
 If categories don't change often it is a good idea to just cache them.
 You can use {@link io.sphere.sdk.queries.ExperimentalReactiveStreamUtils} to do this:

 {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#fetchAll()}
 <h4>Fetch all with predicate</h4>
 Sometimes you want to fetch just the root categories to delete them all:
 {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#fetchRoots()}

 <!-- tip, fetch of criteria, like all root categories, or just any child of one category -->


 <h3 id="category-tree-creation">Work with a cached tree of categories</h3>
 <h3 id="category-tree">Use a tree of categories</h3>

<!-- get by slug -->



 <h3 id="category-deletion">Delete categories and trees</h3>
 <!-- deleting category with children deletes the children -->
 <h3 id="category-tree-changes">Change the category tree</h3>






 */
public class CategoryDocumentation {
    private CategoryDocumentation() {
    }
}
