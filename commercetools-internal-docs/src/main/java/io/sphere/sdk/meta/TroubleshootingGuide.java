package io.sphere.sdk.meta;

/**
 <h3 id="mixed-dependencies">Mixed dependencies</h3>
 <h4>Indications</h4>
 {@code java.lang.NoClassDefFoundError: <a commercetools related class>}
 <h4>Possible solutions</h4>
 The JVM SDK depends on multiple modules like the Java client and the models, make sure that they have the same version number in your build file.

 Example for wrong pom:

 <pre>{@code <dependency>
    <groupId>io.sphere.sdk.jvm</groupId>
    <artifactId>sphere-models</artifactId>
    <version>1.0.0-M16</version>
</dependency>
<dependency>
    <groupId>io.sphere.sdk.jvm</groupId>
    <artifactId>sphere-java-client</artifactId>
    <!-- here it is not 1.0.0-M16 -->
    <version>1.0.0-M15</version>
</dependency>}</pre>

 Example for fixed pom:

 <pre>{@code <dependency>
    <groupId>io.sphere.sdk.jvm</groupId>
    <artifactId>sphere-models</artifactId>
    <version>1.0.0-M16</version>
</dependency>
<dependency>
    <groupId>io.sphere.sdk.jvm</groupId>
    <artifactId>sphere-java-client</artifactId>
    <version>1.0.0-M16</version>
</dependency>}</pre>


  <h3 id="jackson-dependency">JSON Jackson Initialization Problems</h3>
  <h4>Indications</h4>
 {@code Could not initialize class <a commercetools related class>}

 <h4>Possible solutions</h4>
 It could be the case that you use a build tool like Maven which has other code in it depending on an older Jackson version.
 The <a href="https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Transitive_Dependencies">Maven way of getting transitive dependencies</a> may cause that you
 use an outdated Jackson version and you need to explicitly pull it up like with:

 <pre>{@code <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.6.0</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.6.0</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.6.0</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.module</groupId>
    <artifactId>jackson-module-parameter-names</artifactId>
    <version>2.6.0</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.6.0</version>
</dependency>}</pre>

<h3 id=incompatible-ahc>Incompatible Ning Async Http Client Versions</h3>
 <h4>Indications</h4>
 {@code java.lang.NoSuchMethodError: com.ning.http.client.<a class>}



 <h4>Possible solutions</h4>
 Switch to another adapter for this library, remove "sphere-java-client" and replace it with "sphere-java-client-ahc-1_8", refer to the README.md in the SDK GitHub repo.

<a href="https://github.com/AsyncHttpClient/async-http-client/blob/master/MIGRATION.md" target="_blank">A list of breaking changes from Ning Async HTTP Client 1.8 to 1.9.</a>


 <h3 id=ahc-log-flooding>Ning Async Http Client is flooding the logs</h3>

 See {@link io.sphere.sdk.utils.SphereInternalLogger}.

 <h3 id=reuqired-data-to-reproduce>How to provide a reproduceable support question</h3>
 If you get an exception, give us the stacktrace with data you want to share.

 <p>A typical exception stack trace looks like this:</p>
 <pre>{@code
 [error] project: sphere-project-key-42
 [error] endpoint: POST /tax-categories/b5a012ae-0824-4935-b935-943e4253b5c7
 [error] Java: 1.8.0_45
 [error] cwd: /Users/myname/dev/projectname
 [error] date: Fri Aug 14 13:36:46 CEST 2015
 [error] sphere request: DeprecationExceptionSphereClientDecorator.DeprecationHeaderSphereRequest[sphereRequest=TaxCategoryUpdateCommandImpl[versioned=TaxCategoryImpl[name=random-slug--1248264105,description=<null>,taxRates=[TaxRateImpl[id=DkZrWYZZ,name=de19,amount=0.19,includedInPrice=true,country=DE,state=<null>]],id=b5a012ae-0824-4935-b935-943e4253b5c7,version=1,createdAt=2015-08-14T11:36:45.833Z,lastModifiedAt=2015-08-14T11:36:45.833Z],getUpdateActions=[AddTaxRate[taxRate=TaxRateImpl[id=<null>,name=de7,amount=0.07,includedInPrice=true,country=DE,state=<null>],action=addTaxRate]],typeReference=TypeReference<TaxCategory>,baseEndpointWithoutId=/tax-categories,creationFunction=io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommandImpl$$Lambda$125/335238144@477de51b,expansionModel=TaxCategoryExpansionModel[parentPath=<null>,path=<null>],expansionPaths=[]]]
 [error] http request: HttpRequestIntent[httpMethod=POST,path=/tax-categories/b5a012ae-0824-4935-b935-943e4253b5c7,headers={},body=StringHttpRequestBody[body={"version":1,"actions":[{"action":"addTaxRate","taxRate":{"id":null,"name":"de7","amount":0.07,"includedInPrice":true,"country":"DE","state":null}}]}]]
 [error] http response: HttpResponseImpl[statusCode=400,headers={Transfer-Encoding=[chunked], Server=[nginx], Access-Control-Allow-Origin=[*], X-Served-By=[app08.sphere.prod.commercetools.de], X-Correlation-ID=[nginx-cfd1585f-971e-4b43-9fce-b72bba93d8e0], Access-Control-Allow-Methods=[GET, POST, DELETE, OPTIONS], Connection=[close], X-Served-Config=[sphere-projects-ws-1.0], Date=[Fri, 14 Aug 2015 11:36:45 GMT], Access-Control-Allow-Headers=[Accept, Authorization, Content-Type, Origin], Content-Type=[application/json; charset=utf-8]},responseBody={123,34,115,116,97,116,117,115,67,111,100,101,34,58,52,48,48,44,34,109,101,115,115,97,103,101,34,58,34,68,117,112,108,105,99,97,116,101,32,116,97,120,32,114,97,116,101,32,102,111,114,32,68,69,46,34,44,34,101,114,114,111,114,115,34,58,91,123,34,99,111,100,101,34,58,34,73,110,118,97,108,105,100,79,112,101,114,97,116,105,111,110,34,44,34,109,101,115,115,97,103,101,34,58,34,68,117,112,108,105,99,97,116,101,32,116,97,120,32,114,97,116,101,32,102,111,114,32,68,69,46,34,125,93,125},associatedRequest=<null>,bodyAsStringForDebugging={"statusCode":400,"message":"Duplicate tax rate for DE.","errors":[{"code":"InvalidOperation","message":"Duplicate tax rate for DE."}]}]
 [error] additional notes: []
 [error] Javadoc: https://commercetools.github.io/commercetools-jvm-sdk/apidocs/io/sphere/sdk/client/ErrorResponseException.html
 [error] , took 0.229 sec
 [error]     at io.sphere.sdk.client.ExceptionFactory.lambda$of$31(ExceptionFactory.java:52)
 [error]     at io.sphere.sdk.client.ExceptionFactory$$Lambda$143/1461123310.apply(Unknown Source)
 [error]     at io.sphere.sdk.client.ExceptionFactory.lambda$whenStatus$21(ExceptionFactory.java:31)
 [error]     at io.sphere.sdk.client.ExceptionFactory$$Lambda$137/925081809.apply(Unknown Source)
 [error]     at io.sphere.sdk.client.ExceptionFactory.createException(ExceptionFactory.java:71)
 [error]     at io.sphere.sdk.client.SphereClientImpl.createExceptionFor(SphereClientImpl.java:103)
 [error]     at io.sphere.sdk.client.SphereClientImpl.parse(SphereClientImpl.java:88)
 [error]     at io.sphere.sdk.client.SphereClientImpl.processHttpResponse(SphereClientImpl.java:82)
 [error]     at io.sphere.sdk.client.SphereClientImpl.lambda$execute$49(SphereClientImpl.java:57)
 [error]     at io.sphere.sdk.client.SphereClientImpl$$Lambda$105/2008696824.apply(Unknown Source)
 [error]     at java.util.concurrent.CompletableFuture.uniApply(CompletableFuture.java:602)
 [error]     at java.util.concurrent.CompletableFuture$UniApply.tryFire(CompletableFuture.java:577)
 [error]     at java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:474)
 [error]     at java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:1954)
 [error]     at io.sphere.sdk.http.DefaultAsyncHttpClientAdapterImpl.lambda$wrap$5(DefaultAsyncHttpClientAdapterImpl.java:93)
 [error]     at io.sphere.sdk.http.DefaultAsyncHttpClientAdapterImpl$$Lambda$75/644950868.run(Unknown Source)
 [error]     at java.util.concurrent.ForkJoinTask$RunnableExecuteAction.exec(ForkJoinTask.java:1402)
 [error]     at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
 [error]     at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
 [error]     at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1689)
 [error]     at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)
 }</pre>

 <p>If you get do not get an exception, it may be of importance to know your JVM SDK version, Java version, date of occurrence and operating system.</p>
 Describe the behavior you observed and the one you would like to obtain.
 <h3 id="product-not-found-by-text">Product not found</h3>
 <ul>
    <li>Maybe the product is not published. See {@link io.sphere.sdk.products.commands.updateactions.Publish}.</li>
    <li>The product was not found for the search because the locale was not activated in the commercetools platform project. Enable it in the Merchant Center.</li>
 </ul>
 */
public final class TroubleshootingGuide {
    private TroubleshootingGuide() {
    }
}
