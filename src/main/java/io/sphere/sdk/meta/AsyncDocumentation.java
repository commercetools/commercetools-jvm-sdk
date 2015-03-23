package io.sphere.sdk.meta;

/**
 <!-- intro -->

 <h3 id="why">Why</h3>

 If you don't care about threads and asynchronous computation you will probably have a slow and inefficient application.

 <p>Suppose you want to show a customer detail page with the cart items and the customer data, so you need to fetch the cart and the customer.
 Fetching both documents is not related and lets suppose fetching a document from SPHERE.IO takes 100ms.</p>


 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#serialWayToFetchCustomerAndCart()}

 <p>So it takes around 200ms since the requests are done one after another. By fetching them in parallel 100ms of time can be saved.</p>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#parallelWayToFetchCustomerAndCart()}

 <p>Using futures (We use it here as synonym for {@link java.util.concurrent.CompletableFuture} and {@link java.util.concurrent.CompletionStage}.) can be very handy to execute parallel code.</p>

 <p>So you can use future APIs to run code in separate Threads and the result will not be immediately available, but in the future.
 The overhead of creating a future can be lower than <a href="http://stackoverflow.com/a/5483105">overhead of creating new Thread</a>.</p>
 <!--
 <h3 id="what">What</h3>

 <ul>
     <li>monad</li>
link to Wikipedia, give monad laws

     <li>like {@link java.util.Optional} of a result value, can have a value or not, but this can change over time</li>
     <li>like {@link java.util.Optional} of an exception, can have an exception or not, but this can change over time</li>

    <li> by default not bound to any thread pool or asynchronous job</li>

 <li>CompletableFuture for filling it, CompletionStage for public API (dev cannot fulfill future), methods in complectionstage also in completablefuture</li>
 <li>CompletionStage.toCompletableFuture</li>
 <li>usage is not a guarantee, that is reactive or non blocking, can still be used wrong</li>
 <li></li>
 <li></li>
 </ul> -->


 <h3 id="functional-composition">Functional Composition </h3>

 Functional composition covers to transform one future into another for the happy cases. {@link java.util.concurrent.CompletionStage#thenApply(java.util.function.Function)}
 and {@link java.util.concurrent.CompletionStage#thenCompose(java.util.function.Function)} will only be called if the future finishes successfully.




 <h4>ThenApply (map, composition that returns directly a value)</h4>
Mostly, it is easier to reason about side-effect free code. A future is monad so you do not work with the value directly,
 but you provide functions to transform the value or the exception into a new future.

To use a future for further computation you probably need to use {@link java.util.concurrent.CompletionStage#thenApply(java.util.function.Function)}.
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#thenApplyFirstDemo()}


With {@link java.util.concurrent.CompletionStage#thenApply(java.util.function.Function)} you apply a function to a stage
 if this stage completes successfully. The function is a first class member, so you can store it in a value or even make it the return type of
 a method.

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#thenApplyFirstDemoVerbose()}

 {@include.example io.sphere.sdk.meta.FunctionAsReturnValueDemo}

 It has similar semantics like {@link java.util.stream.Stream#map(java.util.function.Function)}.

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#functionalCompositionMapStreamExample()}



<h4>ThenCompose (flatMap, composition that returns a CompletionStage)</h4>

 Sometimes you run in situations where you create a new future inside a future.
 For example if you load a cart and want to fetch the first line item in it.


 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#shouldUseFlatMap()}

 Instead of creating an unmaintainable {@link java.util.concurrent.CompletionStage} of {@link java.util.concurrent.CompletionStage},
 you can use {@link java.util.concurrent.CompletionStage#thenCompose(java.util.function.Function)}.

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#flatMapFirstDemo()}

 It has similar semantics like {@link java.util.stream.Stream#flatMap(java.util.function.Function)}.

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#functionalCompositionFlatMapStreamExample()}



 <!--



 map,flatmap mit future future
 only if no error occurred


 thread pool


 2 futures with thenCompose, after another

 thenCombine for when both completed


 -->

 <h3 id="callbacks">Callbacks</h3>







 <!--


 thenAccept/thenRun for side effects, value needed or not
for side effects
 println
 pseudo send server response
 -->

 <h3 id="creation">Creation and filling</h3>

 <h4>Creation of a successful future</h4>

 A future can be created fulfilled immediately:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createImmediatelyFulfilledFuture()}

 Also future can be fulfilled later:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createFulfilledFuture()}

 For the immediately fulfilled future also exists an SDK utility method:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createImmediatelyFulfilledFutureShortcut()}

 <h4>Creation of a failed future</h4>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createFailedFuture()}

 <p>Using a SDK shortcut:</p>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createFailedFutureShortcut()}


 <!--

 fulfilling in another thread

 normally only creator of future should be able to fulfill it, CompletionStage for outside access

difference Stage,Future
 promise,reedemable promise
 scala future, scala promise
 show threads used, fuer map/thenApply

 once it is filled, should be immutable


 static methods of completable future

 not null

 -->



 <h3 id="blocking">Blocking Access and Immediate Access</h3>
 <!-- CompletionStage.toCompletableFuture().join()

 occurring exception of join and what they mean
 CompletableFuture.getNow(valueIfAbsent)

 orElseThrow
 orElseGet wie bei Optional

 -->
 <h3 id="error-handling">Error Handling</h3>

 <!--

 try catch macht keinen sinn, exception ist im future
 recover & recoverWith

 handle for recover, ergebnis kann noch mal angepasst werden im nächsten stage
 handle kann auch optional zuruückgeben

 -->

 <!--

 <h3 id="ask-pattern">Ask pattern</h3>

 {@code <pre>
 public CompletableFuture<T> performTask() {
     final CompletableFuture<T> future = new CompletableFuture<>();
     //do here stuff async/non-blocking and return the future to the client
     return future;
 }

 </pre>}

 -->

 <h3 id="multi-future">Working with multiple futures</h3>

 <!--
 2
 or list

 applyToEither to use the fasted of 2

 acceptEither
 -->


 <h3 id="traps">Traps</h3>
 <!--
 new CompletableFuture() and call future.get() and nether fill it, will block forever

 try catch mit ask pattern

 thenCompine and thenCompose, IDE may suggest the wrong one, sound similar

 toCompletableFuture.join(), especially in flatMap context


 -->
 <h3 id="advanced-examples">Advanced Examples</h3>

 <!--

    1000 listener for 1 future


 -->


 <h3 id="further-topics">Further Topics</h3>
 <!--
 skipable


 CompletableFuture.obtrudeValue, normally value will not be overridden
 -->


 <h3 id="further-sources">Further read and sources</h3>

<ul>
 <li><a href="http://docs.oracle.com/javase/8/docs/api/index.html?java/util/concurrent/CompletableFuture.html">CompletableFuture</a></li>
 <li><a href="http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletionStage.html">CompletionStage</a></li>
 <li><a href="http://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html">Java 8: Definitive guide to CompletableFuture</a></li>
 <li><a href="http://www.nurkiewicz.com/2013/05/java-8-completablefuture-in-action.html">Java 8: CompletableFuture in action</a></li>
 </ul>



 */
public final class AsyncDocumentation {
    private AsyncDocumentation() {
    }
}
