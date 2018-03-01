package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.attributes.AttributeDefinitionDraft;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Dsl class for {@link ProductTypeDraft}.
 */
public final class ProductTypeDraftDsl extends Base implements ProductTypeDraft {
  private List<AttributeDefinitionDraft> attributes;

  private String description;

  @Nullable
  private String key;

  private String name;

  @JsonCreator
  ProductTypeDraftDsl(final List<AttributeDefinitionDraft> attributes, final String description,
      @Nullable final String key, final String name) {
    this.attributes = attributes;
    this.description = description;
    this.key = key;
    this.name = name;
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
   * Creates a new builder with the values of this object.
   *
   * @return new builder
   */
  public ProductTypeDraftBuilder newBuilder() {
    return new ProductTypeDraftBuilder(attributes, description, key, name);
  }

  public ProductTypeDraftDsl withAttributes(final List<AttributeDefinitionDraft> attributes) {
    return newBuilder().attributes(attributes).build();
  }

  public ProductTypeDraftDsl withDescription(final String description) {
    return newBuilder().description(description).build();
  }

  public ProductTypeDraftDsl withKey(@Nullable final String key) {
    return newBuilder().key(key).build();
  }

  public ProductTypeDraftDsl withName(final String name) {
    return newBuilder().name(name).build();
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
  public static ProductTypeDraftDsl of(@Nullable final String key, final String name,
      final String description, final List<AttributeDefinitionDraft> attributes) {
    return new ProductTypeDraftDsl(attributes, description, key, name);
  }

  /**
   * Creates a new object initialized with the fields of the template parameter.
   *
   * @param template the template
   * @return a new object initialized from the template
   */
  public static ProductTypeDraftDsl of(final ProductTypeDraft template) {
    return new ProductTypeDraftDsl(template.getAttributes(), template.getDescription(), template.getKey(), template.getName());
  }
}
