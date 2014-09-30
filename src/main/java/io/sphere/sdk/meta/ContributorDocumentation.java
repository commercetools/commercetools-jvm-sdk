package io.sphere.sdk.meta;

/**
 <h3 id=construction>Construction</h3>
 <ul>
    <li>
        A good class should have at least one explicit constructor.
    </li>
    <li>
        If multiple constructors are present, one constructor should have the role as primary constructor, so every other constructor has to directly or indirectly call the primary constructor.
    </li>
    <li>
        Prefer to create not public constructors.
    </li>
    <li>
        The task of a constructor is to create a usable and consistent object but not firing actions. Prefer public static factory methods to constructors so internals are hidden and it is possible to make the class an interface later.
    </li>
    <li>
        Static factory methods should be called {@code of}, this is the Java 8 style, like {@link java.util.Optional#of(Object)}. It is okay to create additional aliases, like {@code empty()} for {@code of()}. Very specific factory methods can be named like this {@code Xyz.ofSlug(final String slug)} or
        {@code Xyz.ofName(final String name)} this is more clear without remembering the correct parameter type like for {@code Xyz.of(final String guessWhat)}.
    </li>
    <li>
        If several constructors are present use the annotation {@link com.fasterxml.jackson.annotation.JsonCreator} to mark the right one for the JSON deserialization.
        <ul>
            <li>
                If you don't do it you get an error message like
                {@code Caused by: com.fasterxml.jackson.databind.JsonMappingException: Conflicting property-based creators: already had [constructor for YOUR_CLASS, annotations: [null]], encountered [constructor for YOUR_CLASS, annotations: [null]]}
            </li>
            <li>
                Another problem cause for the previous error can be a static factory method which jackson by mistake tries to use for mapping, so annotate it with {@link com.fasterxml.jackson.annotation.JsonIgnore}.
            </li>
            <li>
                The error message {@code java.lang.RuntimeException: com.fasterxml.jackson.databind.JsonMappingException: Could not find creator property with name 'YOUR_PROPERTY_NAME' } is also a sign to annotate a factory method with {@link com.fasterxml.jackson.annotation.JsonIgnore}.
            </li>
        </ul>
    </li>
    <li>
        In classic Jackson JSON projects you have to annotate the arguments for a constructor:
        <pre>@JsonCreator
public Xyz(@JsonProperty("id") String id)</pre> but with Java 8 it can be just  <pre>@JsonCreator
public Xyz(final String id)</pre>
    </li>
 </ul>

 <h3 id=classes>Classes</h3>
 <ul>
    <li>Do not extend Object directly, if you can't find a base class use {@link io.sphere.sdk.models.Base} which implements equals, hashCode and toString with reflection.</li>
 </ul>
 <h3 id=abstract-classes>Polymorphism/Abstract Classes/Interfaces</h3>
 <ul>
 <li>If you have to deserialize abstract classes or interfaces you need to specify how Jackson should deserialize. Look into the source code of {@link io.sphere.sdk.productdiscounts.ProductDiscountValue} to find out how this can be done.</li>
 <li>If you use {@link com.fasterxml.jackson.annotation.JsonTypeInfo} with a property and have already declared a property with the same name, it will may be written twice with different values, see also <a href="http://stackoverflow.com/questions/18237222/duplicate-json-field-with-jackson">Stackoverflow</a>.</li>
 </ul>
 <h3 id=optional-values>Optional values</h3>

 <ul>
    <li>In contrast to classic Jackson projects you can use {@code Optional<MyType>} directly as member/getter/constructor parameter and it will be mapped.</li>
    <li>Sometimes lists/sets are optional in the API, so if you may send an empty JSON array like in
<pre>{
  "key" : "assignPricesToMasterVariantAccordingToAChannel",
  "roles" : [ ],
  "name" : null,
  "description" : null
}
</pre> it returns the following error message: <pre>{
  "statusCode" : 400,
  "message" : "'roles' should not be empty.",
  "errors" : [ {
    "code" : "InvalidOperation",
    "message" : "'roles' should not be empty."
  } ]
}
 </pre><p>To solve this problem you have to not serialize the field at all like in</p> <pre>{
  "key" : "assignPricesToMasterVariantAccordingToAChannel",
  "name" : null,
  "description" : null
} </pre><p>This can be achieved by adding annotation {@link com.fasterxml.jackson.annotation.JsonInclude} like this: {@code @JsonInclude(JsonInclude.Include.NON_EMPTY) }</p></li>
 </ul>





 */
public final class ContributorDocumentation {
    private ContributorDocumentation() {
    }
}
