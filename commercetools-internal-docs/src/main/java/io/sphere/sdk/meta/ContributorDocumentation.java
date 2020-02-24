package io.sphere.sdk.meta;

/**
 <h3 id=start>Developing the SDK</h3>


 <ul>
    <li>please follow the <a href="https://github.com/commercetools/commercetools-sunrise-java/blob/master/CONTRIBUTING.md">Contribution Guidelines</a></li>
    <li>`sbt clean genDoc` creates Javadocs for all modules, it is available in target/javaunidoc/index.html</li>
    <li>opening the project with IntelliJ IDEA Ultimate might not work due to a bug, but using <a href="https://github.com/mpeltonen/sbt-idea">this solution</a> works</li>
 </ul>

 <h3 id=construction>Construction</h3>
 <ul>
    <li>
        A good class should have at least one explicit constructor.
    </li>
    <li>
        If multiple constructors are present, one constructor should have the role as primary constructor, so every other constructor has to directly or indirectly call the primary constructor.
    </li>
    <li>
        Prefer to create non-public constructors.
    </li>
    <li>
        The purpose of a constructor is to create a usable and consistent object, but not to fire actions. Prefer public static factory methods to constructors so that internals are hidden and to make it possible to turn the class into an interface later.
    </li>
    <li>
        Static factory methods should be named {@code of}, to be compliant to the Java 8 style, for instance {@link java.util.Optional#of(Object)}. It is okay to create additional aliases, like {@code empty()} for {@code of()}. Very specific factory methods can be named like this: {@code Xyz.ofSlug(final String slug)} or
        this: {@code Xyz.ofName(final String name)}. This naming convention makes clear what parameter type is required for the method compared to the more general method name like: {@code Xyz.of(final String guessWhat)}.
    </li>
    <li>
        If several constructors are present use the annotation {@link com.fasterxml.jackson.annotation.JsonCreator} to mark the right one for the JSON deserialization.
        <ul>
            <li>
                If you don't do it you'll get an error message like
                {@code Caused by: com.fasterxml.jackson.databind.JsonMappingException: Conflicting property-based creators: already had [constructor for YOUR_CLASS, annotations: [null]], encountered [constructor for YOUR_CLASS, annotations: [null]]}
            </li>
            <li>
                Another reason for the previous error can be a static factory method which jackson tries to use for mapping by mistake, so you better annotate it with {@link com.fasterxml.jackson.annotation.JsonIgnore}.
            </li>
            <li>
                Also the error message {@code java.lang.RuntimeException: com.fasterxml.jackson.databind.JsonMappingException: Could not find creator property with name 'YOUR_PROPERTY_NAME' } indicates that you should annotate a factory method with {@link com.fasterxml.jackson.annotation.JsonIgnore}.
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
    <li>Do not extend Object directly; if you can't find a base class simply use {@link io.sphere.sdk.models.Base} which implements equals, hashCode and toString with reflection.</li>
 </ul>
 <h3 id=abstract-classes>Polymorphism/Abstract Classes/Interfaces</h3>
 <ul>
 <li>If you have to deserialize abstract classes or interfaces you need to specify the way how Jackson does the deserialization. Have a look into the source code of {@link io.sphere.sdk.productdiscounts.ProductDiscountValue} to find out how this can be done.</li>
 <li>If you use {@link com.fasterxml.jackson.annotation.JsonTypeInfo} with a property and you have already declared a property with the same name, it will may be written twice with different values, see also <a href="https://stackoverflow.com/questions/18237222/duplicate-json-field-with-jackson">Stackoverflow</a>.</li>
 </ul>



 */
public final class ContributorDocumentation {
    private ContributorDocumentation() {
    }
}
