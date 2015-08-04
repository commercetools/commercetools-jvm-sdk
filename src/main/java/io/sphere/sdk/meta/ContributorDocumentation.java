package io.sphere.sdk.meta;

import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.ResourceImpl;

/**
 <h3 id=start>Developing the SDK</h3>


 <ul>
    <li>please follow the <a href="https://github.com/sphereio/sphere-sunrise/blob/master/CONTRIBUTING.md">Contribution Guidelines</a></li>
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
 <li>If you use {@link com.fasterxml.jackson.annotation.JsonTypeInfo} with a property and you have already declared a property with the same name, it will may be written twice with different values, see also <a href="http://stackoverflow.com/questions/18237222/duplicate-json-field-with-jackson">Stackoverflow</a>.</li>
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
 </pre><p>To solve this problem you have to prevent the field from serialization like in</p> <pre>{
  "key" : "assignPricesToMasterVariantAccordingToAChannel",
  "name" : null,
  "description" : null
} </pre><p>This can be achieved by adding the annotation {@link com.fasterxml.jackson.annotation.JsonInclude} like this: {@code @JsonInclude(JsonInclude.Include.NON_EMPTY) }</p></li>
 </ul>


<h3 id=aggregates>Creating aggregates</h3>

 <h4 id=model-package>Package {@code io.sphere.sdk.<RESOURCE_AS_LOWERCASE_IN_PLURAL>}</h4>
<ul>
 <li>create an interface <em>RESOURCE</em> which extends {@link Resource} with <em>RESOURCE</em> as type parameter</li>
 <li>create a class <em>RESOURCEImpl</em> which extends {@link ResourceImpl} with <em>RESOURCE</em> as type argument and it should implement <em>RESOURCE</em></li>

 <li>in <em>RESOURCEImpl</em> replace "public class" with "class" to set the visibility
 of this class to package scope</li>

 <li>in <em>RESOURCEImpl</em> add private final fields for all members of the resource (except the members defined in {@link ResourceImpl}
 like {@link ResourceImpl#id}, {@link ResourceImpl#version}, {@link ResourceImpl#createdAt} and {@link ResourceImpl#lastModifiedAt}</li>
 <li>in <em>RESOURCEImpl</em> create a constructor with your IDE, this will be called the primary constructor</li>
 <li>in <em>RESOURCEImpl</em> create getters for all the fields you have created with your IDE</li>
 <li>in <em>RESOURCEImpl</em> use 'extract interface' of your IDE to get the signatures of the getters,
 cut and paste them into <em>RESOURCE</em> and remove the extracted interface, as a result the getter will also have the {@link java.lang.Override} annotation</li>
 <li>in <em>RESOURCE</em> annotate the interface <em>RESOURCE</em> with {@code @JsonDeserialize(as=RESOURCEImpl.class)}</li>
 <li>in <em>RESOURCE</em> create a static method which returns the type id as in {@link io.sphere.sdk.categories.Category#typeId()}</li>
 <li>in <em>RESOURCE</em> create a static method which returns a {@link com.fasterxml.jackson.core.type.TypeReference} as in {@link io.sphere.sdk.categories.Category#typeReference()}</li>
 <li>in <em>RESOURCE</em> implement {@link io.sphere.sdk.models.Referenceable#toReference()} as in io.sphere.sdk.categories.Category#toReference()</li>

<li>create a class <em>RESOURCEBuilder</em> which extends {@code DefaultModelFluentBuilder<RESOURCEBuilder, RESOURCE>}</li>
<li>in <em>RESOURCEBuilder</em> copy the attributes of  <em>RESOURCEImpl</em> and make them non final</li>
<li>in <em>RESOURCEBuilder</em> make a private constructor with all really necessary values</li>
 <li>in <em>RESOURCEBuilder</em> set default values where you can, for {@link java.util.Optional} empty and for list or set their empty versions.</li>
 <li>in <em>RESOURCEBuilder</em> create public static "of" methods which constructs the builder in a consistent way</li>
<li>create a <em>RESOURCEImplTest</em> which extends {@code DefaultModelSubclassTest<RESOURCE>}, you need to implement some methods.</li>


 </ul>


 */
public final class ContributorDocumentation {
    private ContributorDocumentation() {
    }
}
