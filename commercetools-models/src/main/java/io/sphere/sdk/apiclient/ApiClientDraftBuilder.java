package io.sphere.sdk.apiclient;

import io.sphere.sdk.client.SphereScope;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ApiClientDraftBuilder extends ApiClientDraftBuilderBase {

    ApiClientDraftBuilder(final @Nullable Integer deleteDaysAfterCreation, final String name, final String scope) {
        super(deleteDaysAfterCreation, name, scope);
    }

    public static ApiClientDraftBuilder of(final String name, String projectKey, final SphereScope scope0, final SphereScope... scopes) {
        final List<SphereScope> scopeList = new ArrayList<>();
        scopeList.add(scope0);
        Optional.ofNullable(scopes).map(sphereScopes -> scopeList.addAll(Arrays.asList(scopes)));
        return of(name,projectKey,scopeList);
    }

    public static ApiClientDraftBuilder of(final String name,String projectKey,final List<SphereScope> scopes) {
        final String scope = scopes.stream().map(SphereScope::toScopeString).map(s -> s+":"+projectKey).collect(Collectors.joining(" "));
        return new ApiClientDraftBuilder(null,name, scope);
    }

    public static ApiClientDraftBuilder of(final String name, final String scope) {
        return new ApiClientDraftBuilder(null,name, scope);
    }
}
