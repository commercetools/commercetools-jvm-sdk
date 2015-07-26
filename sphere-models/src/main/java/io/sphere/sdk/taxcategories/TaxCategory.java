package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import java.util.List;

/** Tax Categories define how products are to be taxed in different countries.

 <p id=operations>Operations:</p>
<ul>
 <li>Create a tax category with {@link io.sphere.sdk.taxcategories.commands.TaxCategoryCreateCommand}.</li>
 <li>Query a tax category with {@link io.sphere.sdk.taxcategories.queries.TaxCategoryQuery}.</li>
 <li>Delete a tax category with {@link io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteCommand}.</li>
 </ul>


 */
@JsonDeserialize(as=TaxCategoryImpl.class)
public interface TaxCategory extends DefaultModel<TaxCategory> {
    String getName();

    @Nullable
    String getDescription();

    List<TaxRate> getTaxRates();

    static TypeReference<TaxCategory> typeReference(){
        return new TypeReference<TaxCategory>() {
            @Override
            public String toString() {
                return "TypeReference<TaxCategory>";
            }
        };
    }

    @Override
    default Reference<TaxCategory> toReference() {
        return Reference.of(typeId(), getId(), this);
    }

    static String typeId(){
        return "tax-category";
    }
}
