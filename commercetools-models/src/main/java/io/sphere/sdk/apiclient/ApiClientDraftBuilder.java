package io.sphere.sdk.apiclient;

import io.sphere.sdk.client.SphereScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ApiClientDraftBuilder extends ApiClientDraftBuilderBase {

    ApiClientDraftBuilder(String name, String scope) {
        super(name, scope);
    }

    public static ApiClientDraftBuilder of(final String name,String projectKey,final SphereScope scope0, final SphereScope... scopes) {
        final List<SphereScope> scopeList = new ArrayList<>();
        scopeList.add(scope0);
        Optional.ofNullable(scopes).map(sphereScopes -> scopeList.addAll(Arrays.asList(scopes)));
        return of(name,projectKey,scopeList);
    }

    public static ApiClientDraftBuilder of(final String name,String projectKey,final List<SphereScope> scopes) {
        final String scope = scopes.stream().map(SphereScope::toScopeString).map(s -> s+":"+projectKey).collect(Collectors.joining(" "));
        return new ApiClientDraftBuilder(name, scope);
    }

    public static ApiClientDraftBuilder of(final String name, final String scope) {
        return new ApiClientDraftBuilder(name, scope);
    }
}
