package io.sphere.sdk.projects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.taxcategories.TaxCategoryDraftDsl;

@JsonDeserialize(as = MessagesConfigurationDraftDsl.class)
public interface MessagesConfigurationDraft {

    Boolean isEnabled();

    Long getDeleteDaysAfterCreation();

    static MessagesConfigurationDraft of(final Boolean enabled, final Long deleteDaysAfterCreation) {
        return new MessagesConfigurationDraftDsl(enabled,deleteDaysAfterCreation );
    }

}
