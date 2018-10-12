package io.sphere.sdk.projects;

import io.sphere.sdk.models.Base;

final class MessagesConfigurationDraftDsl extends Base implements MessagesConfigurationDraft {
    private Boolean enabled;
    private Long deleteDaysAfterCreation;

    MessagesConfigurationDraftDsl(final Boolean enabled, final Long deleteDaysAfterCreation) {
        this.enabled = enabled;
        this.deleteDaysAfterCreation = deleteDaysAfterCreation;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }


    @Override
    public Long getDeleteDaysAfterCreation() {
        return deleteDaysAfterCreation;
    }



}
