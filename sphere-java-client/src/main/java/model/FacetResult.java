package de.commercetools.sphere.client.model;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/** Information about a facet, as part of {@link SearchQueryResult}. */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TermsFacetResult.class, name = "terms"),
        @JsonSubTypes.Type(value = RangeFacetResult.class, name = "range")
})
public interface FacetResult {
}
