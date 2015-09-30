package io.sphere.sdk.types;

import io.sphere.sdk.categories.Category;

import javax.annotation.Nullable;

/**
 Interface to mark objects which can have custom fields, it has nothing to do with CustomObjects.

  <p>To add fields to classes implementing {@link Custom} a {@link Type} needs to be created and assigned to the object.</p>

 <p>In the example scenario we create a type for categories.</p>

 The type will contain:
 <ul>
    <li>an enum field {@code state} to indicate the state of of the category like "published" and "draft"</li>
    <li>a String field {@code imageUrl} to show an icon for the category</li>
    <li>a set field with category references {@code relatedCategories} to suggest other categories with accessoires for example</li>
 </ul>

 If you work with types it is a good idea to import {@code io.sphere.sdk.types.*;} so you don't get confused with
 classes from product attributes which have sometimes the same class name but are in another package.

 <h3 id="create-type">Create a type</h3>

 <p>A type can be assigned to different kinds of objects, e.g., a type can be used for categories, orders and carts.
 In the type creation the property {@link TypeDraft#getResourceTypeIds()} contains a set of resourceTypeIds which can be
 find on the class of the object like in {@link Category#resourceTypeId()} for categories. Be careful to not confuse this with
 {@link Category#referenceTypeId()} which contains the id for {@link io.sphere.sdk.models.Reference}s. Cart and order have the same
 resourceTypeId so the type can be automatically be used for {@link io.sphere.sdk.orders.Order} and {@link io.sphere.sdk.carts.Cart} if it is for one of them enabled.</p>

 <p>Execution example:</p>
 {@include.example io.sphere.sdk.types.CreateTypeDemo}

 <h3 id="create-object-with-type">Assign a type to an object at the creation of the object</h3>

 {@include.example io.sphere.sdk.types.CustomTest#createCategoryWithType()}

 <h3 id="assign-existing-object-with-type">Assign a type to an object in an update action</h3>
 <h3 id="update-type">Update a type</h3>
 <h3 id="delete-type">Delete a type</h3>




 */
public interface Custom {
    @Nullable
    CustomFields getCustom();
}
