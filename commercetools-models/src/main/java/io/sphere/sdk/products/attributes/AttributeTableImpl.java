package io.sphere.sdk.products.attributes;

import io.sphere.sdk.models.Base;

import java.util.List;
import java.util.function.Function;

final class AttributeTableImpl extends Base implements AttributeTable {
    private final List<AttributeTableRow> rows;

    AttributeTableImpl(final List<AttributeTableRow> rows) {
        this.rows = rows;
    }

    @Override
    public List<AttributeTableRow> getRows() {
        return rows;
    }

    @Override
    public int getMaxTranslatedValueLength() {
        return maxRowLengthFor(row -> row.getTranslatedValue());
    }

    private int maxRowLengthFor(final Function<AttributeTableRow, String> rowStringFunction) {
        return rows.stream()
                .map(rowStringFunction)
                .filter(value -> value != null)
                .mapToInt(value -> value.length())
                .max()
                .orElse(0);
    }

    @Override
    public int getMaxTranslatedLabelLength() {
        return maxRowLengthFor(row -> row.getTranslatedLabel());
    }
}
