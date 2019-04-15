package io.sphere.sdk.stores;

import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@ResourceValue
public interface Store {
    
    String getId();
    
    String getVersion();
    
    String getKey();
    
    @Nullable
    LocalizedString getName();
    
    ZonedDateTime getCreatedAt();
    
    ZonedDateTime getLastModifiedAt();
    
}
