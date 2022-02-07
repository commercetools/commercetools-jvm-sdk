{
    "dependencies": [
<#list dependencyMap as e>
        <#assign project = e.getKey()/>
        <#assign licenses = e.getValue()/>
        {
            "moduleName": "${project.groupId}:${project.artifactId}",
            "moduleVersion": "${project.version}",
            "moduleLicenses": [
<#list licenses as license>
                {
                    "moduleLicense": "${license}"
                }<#if license_has_next>,</#if>
</#list>
            ]
        }<#if e_has_next>,</#if>
</#list>
    ]
}
