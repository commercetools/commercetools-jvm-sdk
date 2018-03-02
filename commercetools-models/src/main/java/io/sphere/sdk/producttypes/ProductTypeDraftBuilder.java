package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeDefinitionDraft;
import io.sphere.sdk.products.attributes.AttributeDefinitionDraftBuilder;
import io.sphere.sdk.utils.SphereInternalUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Builder for {@link ProductTypeDraft}.
 */
public final class ProductTypeDraftBuilder extends Base implements Builder<ProductTypeDraftDsl> {
  private List<AttributeDefinitionDraft> attributes;

  private String description;

  @Nullable
  private String key;

  private String name;

  ProductTypeDraftBuilder() {
  }

  ProductTypeDraftBuilder(final List<AttributeDefinitionDraft> attributes, final String description,
      @Nullable final String key, final String name) {
    this.attributes = attributes;
    this.description = description;
    this.key = key;
    this.name = name;
  }

  /**
   * Sets the {@code attributes} property of this builder.
   *
   * @param attributes the value for {@link ProductTypeDraft#getAttributes()}
   * @return this builder
   */
  public ProductTypeDraftBuilder attributes(final List<AttributeDefinitionDraft> attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * Sets the {@code description} property of this builder.
   *
   * @param description the value for {@link ProductTypeDraft#getDescription()}
   * @return this builder
   */
  public ProductTypeDraftBuilder description(final String description) {
    this.description = description;
    return this;
  }

  /**
   * Sets the {@code key} property of this builder.
   *
   * @param key the value for {@link ProductTypeDraft#getKey()}
   * @return this builder
   */
  public ProductTypeDraftBuilder key(@Nullable final String key) {
    this.key = key;
    return this;
  }

  /**
   * Sets the {@code name} property of this builder.
   *
   * @param name the value for {@link ProductTypeDraft#getName()}
   * @return this builder
   */
  public ProductTypeDraftBuilder name(final String name) {
    this.name = name;
    return this;
  }

  public List<AttributeDefinitionDraft> getAttributes() {
    return attributes;
  }

  public String getDescription() {
    return description;
  }

  @Nullable
  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  /**
   * Concatenate {@code attributes} parameter to the {@code attributes} list property of this builder.
   *
   * @param attributes the value for {@link ProductTypeDraft#getAttributes()}
   * @return this builder
   */
  public ProductTypeDraftBuilder plusAttributes(final List<AttributeDefinition> attributes) {
    final List<AttributeDefinitionDraft> attributeDefinitionDraftList = attributes.stream()
            .map(attributeDefinition -> AttributeDefinitionDraftBuilder.of(attributeDefinition).build())
            .collect(Collectors.toList());
    this.attributes =  SphereInternalUtils.listOf(Optional.ofNullable(this.attributes).orElseGet(ArrayList::new), attributeDefinitionDraftList);
    return this;
  }

  /**
   * Adds {@code attributes} parameter to the {@code attributes} list property of this builder.
   *
   * @param attributes the value of the element to add to {@link ProductTypeDraft#getAttributes()}
   * @return this builder
   */
  public ProductTypeDraftBuilder plusAttributes(final AttributeDefinitionDraft attributes) {
    this.attributes =  SphereInternalUtils.listOf(Optional.ofNullable(this.attributes).orElseGet(ArrayList::new), Collections.singletonList(attributes));
    return this;
  }

  /**
   * Adds {@code attributes} parameter to the {@code attributes} list property of this builder.
   *
   * @param attribute the value of the element to add to {@link ProductTypeDraft#getAttributes()}
   * @return this builder
   * @deprecated user {@link ProductTypeDraftBuilder#plusAttributes(AttributeDefinitionDraft)} instead
   */
  public ProductTypeDraftBuilder plusAttributes(final AttributeDefinition attribute) {
    return plusAttributes(AttributeDefinitionDraftBuilder.of(attribute).build());
  }

  /**
   * Creates a new instance of {@code ProductTypeDraftDsl} with the values of this builder.
   *
   * @return the instance
   */
  public ProductTypeDraftDsl build() {
    return new ProductTypeDraftDsl(attributes, description, key, name);
  }

  /**
   * Creates a new object initialized with the given values.
   *
   * @param key initial value for the {@link ProductTypeDraft#getKey()} property
   * @param name initial value for the {@link ProductTypeDraft#getName()} property
   * @param description initial value for the {@link ProductTypeDraft#getDescription()} property
   * @param attributes initial value for the {@link ProductTypeDraft#getAttributes()} property
   * @return new object initialized with the given values
   */
  public static ProductTypeDraftBuilder of(@Nullable final String key, final String name,
      final String description, final List<AttributeDefinitionDraft> attributes) {
    return new ProductTypeDraftBuilder(attributes, description, key, name);
  }

  /**
   * Creates a new object initialized with the fields of the template parameter.
   *
   * @param template the template
   * @return a new object initialized from the template
   */
  public static ProductTypeDraftBuilder of(final ProductTypeDraft template) {
    return new ProductTypeDraftBuilder(template.getAttributes(), template.getDescription(), template.getKey(), template.getName());
  }

  private static List<AttributeDefinitionDraft> copyAttributes(final List<AttributeDefinition> templates) {
    return templates == null ? null : templates.stream().map(template -> AttributeDefinitionDraftBuilder.of(template).build()).collect(Collectors.toList());
  }

  /**
   * Creates a new object initialized with the fields of the template parameter.
   *
   * @param template the template
   * @return a new object initialized from the template
   */
  public static ProductTypeDraftBuilder of(final ProductType template) {
    return new ProductTypeDraftBuilder(copyAttributes(template.getAttributes()), template.getDescription(), template.getKey(), template.getName());
  }
}
