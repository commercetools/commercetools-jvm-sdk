# Localization

This is an API draft for multi-language support for Product attributes.
The only attributes that are subject to localization are Text and Enum attributes.

### Text attributes

Proposal (straightforward):

````java
p.getTitle()          -> p.getTitle("de")
p.getDescription()    -> p.getDescription("de")
p.getSlug()           -> p.getSlug("de")

p.getString("myAttr") -> p.getString("myAttr", "de")
````

Providing overloads without the language parameter (`p.getTitle()`) might make sense for cases when a project uses just
one language.

### Enum attributes

Current state:

````java
p.getString("color")  // enums are represented as strings
````

Proposal:

````java
p.getEnum("color").getKey()       // returns "color"
p.getEnum("color").getLabel("de")
````

For ideas on how to implement `getEnum`, see `Attribute.getMoney`.

### Querying

````java
sphere.products().bySlug(slug, "de")
````

### Searching

````java
sphere.products().filter(new FilterExpressions.Fulltext(text, "de"))
````