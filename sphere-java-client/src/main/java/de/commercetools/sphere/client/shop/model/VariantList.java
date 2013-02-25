package de.commercetools.sphere.client.shop.model;

import javax.annotation.Nonnull;
import java.util.*;

/** List of variants of a {@link Product}, with helper methods for filtering. */
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

    /** Returns the first element in this list, or null if the list is empty. */
    public Variant firstOrNull() {
        return variants.size() > 0 ? variants.get(0) : null;
    }

    /** Returns the first element in this list, throws an {@link IllegalStateException} if the list is empty. */
    public Variant first() {
        if (variants.size() == 0) throw new IllegalStateException("The variant list is empty.");
        return variants.get(0);
    }

    /** Returns the items as a plain list. */
    public ArrayList<Variant> asList() {
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

    /** Returns the variant with given SKU, or null if no such variant exists. */
    public Variant findBySKU(String sku) {
        for (Variant v: variants) {
            if (v.getSKU().equals(sku)) return v;
        }
        return null;
    }

    /** Finds first variant that satisfies all given attribute values.
     *
     *  @param desiredAttribute Attribute that the returned variant must have.
     *
     *  Example:
     *
     *  If you want to implement a variant switcher that changes color but maintains selected size:
     *  <code>
     *      Variant greenVariant = p.getVariant(new Attribute("color", "green"), currentVariant.getAttribute("size"));
     *  </code>
     *
     *  If you want to implement a variant switcher for colors of current product:
     *  <code>
     *      for (Attribute color: product.getAvailableVariantAttributes("color")) {
     *          Variant variant = p.getVariant(color);  // returns first variant for color
     *      }
     *  </code>
     *
     *  @return The variant or null if no such variant exists.
     *  */
    public VariantList findByAttributes(Attribute desiredAttribute, Attribute... desiredAttributes) {
        return findByAttributes(de.commercetools.internal.util.ListUtil.list(desiredAttribute, desiredAttributes));
    }

    /** Finds first variant that satisfies all given attribute values.
     *
     *  @param desiredAttributes Attributes that the returned variant must have.
     *
     *  Example:
     *
     *  If you want to implement a variant switcher that changes color but maintains selected size:
     *  <code>
     *      Variant greenVariant = p.getVariant(new Attribute("color", "green"), currentVariant.getAttribute("size"));
     *  </code>
     *
     *  If you want to implement a variant switcher for colors of current product:
     *  <code>
     *      for (Attribute color: product.getAvailableVariantAttributes("color")) {
     *          Variant variant = p.getVariant(color);  // returns first variant for color
     *      }
     *  </code>
     *
     *  @return The variant or null if no such variant exists.
     *  */
    public VariantList findByAttributes(@Nonnull Iterable<Attribute> desiredAttributes) {
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

     // --------------------------------------------------------
    // Helpers
    // --------------------------------------------------------

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
