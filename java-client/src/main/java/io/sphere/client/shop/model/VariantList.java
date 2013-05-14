package io.sphere.client.shop.model;

import com.google.common.base.Optional;
import io.sphere.internal.util.ListUtil;

import javax.annotation.Nonnull;
import java.util.*;

/** List of variants of a {@link Product} that supports filtering by various criteria. */
public class VariantList implements Iterable<Variant> {
    private final List<Variant> variants;

    public VariantList(@Nonnull List<Variant> variants) {
        if (variants == null) throw new NullPointerException("variants");
        this.variants = variants;
    }

    @Override public Iterator<Variant> iterator() {
        return variants.iterator();
    }

    /** Returns the number of items in this list. */
    public int size() {
        return variants.size();
    }

    /** Returns {@code true} if this list contains no items. */
    public boolean isEmpty() {
        return variants.isEmpty();
    }

    /** Returns the first element in this list, or {@link Optional#absent()} if the list is empty. */
    public Optional<Variant> first() {
        return (variants.size() == 0) ? Optional.<Variant>absent() : Optional.of(variants.get(0));
    }

    /** Returns the items as a {@link List}. */
    public List<Variant> asList() {
        return new ArrayList<Variant>(variants);
    }

    /** Gets distinct values of given attribute across all variants of this product. */
    public List<Attribute> getAvailableAttributes(String attributeName) {
        List<Attribute> attributes = new ArrayList<Attribute>();
        Set<Object> seen = new HashSet<Object>();
        for(Variant v: variants) {
            for (Attribute a: v.getAttributes()) {
                if (a.getName().equals(attributeName) && !seen.contains(a.getValue())) {
                    attributes.add(a);
                    seen.add(a.getValue());
                }
            }
        }
        return attributes;
    }

    /** Returns the variant with given id, or {@link Optional#absent()} if no such variant exists. */
    public Optional<Variant> byId(String id) {
        for (Variant v: variants) {
            if (v.getId().equals(id)) return Optional.of(v);
        }
        return Optional.absent();
    }

    /** Returns the first variant with given SKU, or {@link Optional#absent()} if no such variant exists. */
    public Optional<Variant> bySKU(String sku) {
        for (Variant v: variants) {
            if (v.getSKU().equals(sku)) return Optional.of(v);
        }
        return Optional.absent();
    }

    /** Finds variants that have all given attribute values.
     *
     * <p>Example:
     *
     * <p>Implement a variant switcher for colors of current product:
     * <p><code>
     * for (Attribute color: product.getVariants().getAvailableAttributes("color")) {
     *     Variant variant = p.getVariants().byAttributes(color).first().get(); // use get() only if you are sure the variant exists
     * }
     * </code>
     *
     * <p>Implement a variant switcher that changes color, maintaining selected size:
     * <p><code>
     * VariantList greenVariants = p.getVariants().byAttributes(new Attribute("color", "green"));
     * VariantList greenInCurrentSize = greenVariants.byAttributes(currentVariant.getAttribute("size"));
     * </code>
     *
     * @param desiredAttribute Attribute that the returned variants must have.
     * @param desiredAttributes Additional attributes that the returned variants must have.
     * @return Variants that have all given attribute values. */
    public VariantList byAttributes(Attribute desiredAttribute, Attribute... desiredAttributes) {
        return byAttributes(ListUtil.list(desiredAttribute, desiredAttributes));
    }

    /** Finds variants that have all given attribute values.
     *
     * <p>Example:
     *
     * <p>Implement a variant switcher for colors of current product:
     * <p><code>
     * for (Attribute color: product.getVariants().getAvailableAttributes("color")) {
     *     Variant variant = p.getVariants().byAttributes(color).first().get(); // use get() only if you are sure the variant exists
     * }
     * </code>
     *
     * <p>Implement a variant switcher that changes color, maintaining selected size:
     * <p><code>
     * VariantList greenVariants = p.getVariants().byAttributes(new Attribute("color", "green"));
     * VariantList greenInCurrentSize = greenVariants.byAttributes(currentVariant.getAttribute("size"));
     * </code>
     *
     * @param desiredAttributes Attributes that the returned variants must have.
     * @return Variants that have all given attribute values. */
    public VariantList byAttributes(@Nonnull Iterable<Attribute> desiredAttributes) {
        if (desiredAttributes == null) throw new NullPointerException("desiredAttributes");
        Map<String, Attribute> desiredAttributesMap = toMap(desiredAttributes);
        ArrayList<Variant> filtered = new ArrayList<Variant>();
        for (Variant v: variants) {
            int matchCount = 0;
            for (Attribute a: v.getAttributes()) {
                Attribute desiredAttribute = desiredAttributesMap.get(a.getName());
                if (desiredAttribute != null && (desiredAttribute.getValue().equals(a.getValue()))) {
                   matchCount++;
                }
            }
            if (matchCount == desiredAttributesMap.size()) {
                // has all desiredAttributes
                filtered.add(v);
            }
        }
        return new VariantList(filtered);
    }

    // ------------------------------------
    // Helpers
    // ------------------------------------

    /** Copies the attributes of given variant and overrides given attributes (based on name). */
    private Map<String, Attribute> toMap(Iterable<Attribute> attributes) {
        Map<String, Attribute> map = new HashMap<String, Attribute>();
        for (Attribute a: attributes) {
            if (a != null) {
                map.put(a.getName(), a);
            }
        }
        return map;
    }
}
