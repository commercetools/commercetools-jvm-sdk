package io.sphere.sdk.meta;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 <!-- intro -->

 <h3 id="why">Why asynchronous</h3>

 If you don't care about threads and asynchronous computation you will probably have a slow and inefficient application.

 <p>Suppose you want to show a customer detail page with the cart items and the customer data. For doing this, you need to fetch the cart and the customer.
 Let's suppose fetching those two unrelated documents from the commercetools platform takes 100ms for each document.</p>


 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#serialWayToFetchCustomerAndCart()}

 <p>So it takes around 200ms since the requests are done one after another. By fetching them in parallel 100ms of time can be saved.</p>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#parallelWayToFetchCustomerAndCart()}

 <p>Using futures (We use it here as synonym for {@link java.util.concurrent.CompletableFuture} and {@link java.util.concurrent.CompletionStage}.) can be very handy for executing code in parallel.</p>

 <p>You can use future APIs to run code in separate Threads so that the result will not be immediately available, but in the future.
 The overhead of creating a future can be lower than the <a href="https://stackoverflow.com/a/5483105">overhead of creating new Thread</a>.</p>

 <h3 id="mixing-async-and-sync">Mixing synchronous and asynchronous computations</h3>

 A lot of frameworks do not support asynchronous results but it is still possible to execute calls in parallel and then wait for computed result:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#firstAsyncThenSync()}

 Also for a group of commercetools requests they can be executed in parallel and then collected:

 {@include.example io.sphere.sdk.meta.AsyncCollectorDemo}

 <!--
 <h3 id="what">What</h3>

 <ul>
     <li>monad</li>
link to Wikipedia, give monad laws

     <li>like {@link java.util.Optional} of a result value, can have a value or not, but this can change over time</li>
     <li>like {@link java.util.Optional} of an exception, can have an exception or not, but this can change over time</li>

    <li> by default not bound to any thread pool or asynchronous job</li>

 for one value, value can be a list, but it is not a subscription like in Rx.


 <li>CompletableFuture for filling it, CompletionStage for public API (dev cannot fulfill future), methods in complectionstage also in completablefuture</li>
 <li>CompletionStage.toCompletableFuture</li>
 <li>usage is not a guarantee, that is reactive or non blocking, can still be used wrong</li>
 <li></li>
 <li></li>
 </ul> -->


 <h3 id="functional-composition">Functional Composition </h3>

 Functional composition covers transforming one future into another for the happy cases. {@link java.util.concurrent.CompletionStage#thenApply(java.util.function.Function)}
 and {@link java.util.concurrent.CompletionStage#thenCompose(java.util.function.Function)} will only be called if the future finishes successfully.




 <h4>ThenApply (map, function returns directly a value)</h4>
Mostly, it is easier to reason about side-effect free code. A future is monad so you do not work with the value directly,
 but you provide functions to transform the value or the exception into a new future.

To use a future for further computation, you probably need to use {@link java.util.concurrent.CompletionStage#thenApply(java.util.function.Function)}.
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#thenApplyFirstDemo()}


With {@link java.util.concurrent.CompletionStage#thenApply(java.util.function.Function)} you apply a function to a stage
 if this stage completes successfully. The function is a first class member, so you can store it in a value or even make it the return type of
 a method.

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#thenApplyFirstDemoVerbose()}

 {@include.example io.sphere.sdk.meta.FunctionAsReturnValueDemo}

 It has similar semantics like {@link java.util.stream.Stream#map(java.util.function.Function)}.

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#functionalCompositionMapStreamExample()}



<h4>ThenCompose (flatMap, function returns a CompletionStage)</h4>

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


 For some occasions you do not want to transform a value, but to perform a side effect task, like logging a value or an error,
 writing sth. into a file or sending a response for a request.

 {@include.example io.sphere.sdk.meta.AsyncDocumentationCallbackTest#loggingCallbackExample()}

 {@link java.util.concurrent.CompletionStage#whenComplete(java.util.function.BiConsumer)} keeps the result as it is and performs side-effects,
 so it is nice to log in between and then map the stage to a new one:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationCallbackTest#whenCompleteAsyncDemo()}

 <h3 id="creation">Creation and filling</h3>

 <h4>Creation of a successful future</h4>

 A future can be created as fulfilled immediately:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createImmediatelyFulfilledFuture()}

 Also future can be fulfilled later:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createFulfilledFuture()}

 For the immediately fulfilled future an SDK utility method also exists:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createImmediatelyFulfilledFutureShortcut()}

 <h4>Creation of a failed future</h4>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createFailedFuture()}

 <p>Using an SDK shortcut:</p>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#createFailedFutureShortcut()}


 <p>If you complete a future, it is possible that the same Thread is used for functional compositions or executing callbacks. If you don't want this, the calls need to use the methods which end with "Async".</p>
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

<p>{@link java.util.concurrent.CompletionStage} does not provide immediate or blocking access to its value or error,
 but it is possible, but not encouraged to transform the {@link java.util.concurrent.CompletionStage} with {@link CompletionStage#toCompletableFuture()} to a {@link java.util.concurrent.CompletableFuture}.</p>


 <h4>Blocking access</h4>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#futureJoinDemo()}

 <h4>Access with timeout</h4>
 <p>Future completes in time:</p>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#futureGetTimeoutDemo()}

 <p>future does not complete in time:</p>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#futureGetTimeoutDemoWithActualTimeout()}

 <h4>Access for the impatient</h4>
 <p>Future completed:</p>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#demoGetNowCompleted()}

 <p>Future did not yet complete:</p>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#demoGetNow()}

<p>Workaround if the value should be lazy computed:</p>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#testOrElseGet()}

 See {@link io.sphere.sdk.utils.CompletableFutureUtils#orElseGet(CompletionStage, Supplier)}.

 <p>Workaround if exception should be thrown:</p>
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#testOrElseThrow()}
 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#testOrElseThrowHappyPath()}

 See {@link io.sphere.sdk.utils.CompletableFutureUtils#orElseThrow(CompletionStage, Supplier)} .
 <br>
 <br>
<h4>Summary of Blocking Access and Immediate Access methods </h4>

 <table class="custom">
 <caption>Blocking Access and Immediate Access methods of {@link java.util.concurrent.CompletableFuture}</caption>
 <tbody>
 <tr>
 <td>&nbsp;</td>
 <td>
 <p>future.join()</p>
 </td>
 <td>
 <p>future.get()</p>
 </td>
 <td>
 <p>future.get(12, TimeUnit.MILLISECONDS)</p>
 </td>
 <td>
 <p>future.getNow(&quot;default&quot;)</p>
 </td>
 </tr>
 <tr>
 <td>
 <p>returns value if present</p>
 </td>
 <td>
 <p>x</p>
 </td>
 <td>
 <p>x</p>
 </td>
 <td>
 <p>x</p>
 </td>
 <td>
 <p>x</p>
 </td>
 </tr>
 <tr>
 <td>
 <p>blocks potentially forever</p>
 </td>
 <td>
 <p>x</p>
 </td>
 <td>
 <p>x</p>
 </td>
 <td>&nbsp;</td>
 <td>&nbsp;</td>
 </tr>
 <tr>
 <td>
 <p>uses alternative, if value not present</p>
 </td>
 <td>&nbsp;</td>
 <td>&nbsp;</td>
 <td>&nbsp;</td>
 <td>
 <p>x</p>
 </td>
 </tr>
 <tr>
 <td>
 <p>throws TimeoutException</p>
 </td>
 <td>&nbsp;</td>
 <td>&nbsp;</td>
 <td>
 <p>x</p>
 </td>
 <td>&nbsp;</td>
 </tr>
 <tr>
 <td>
 <p>throws CompletionException</p>
 </td>
 <td>
 <p>x</p>
 </td>
 <td>&nbsp;</td>
 <td>&nbsp;</td>
 <td>
 <p>x</p>
 </td>
 </tr>
 <tr>
 <td>
 <p>throws ExecutionException</p>
 </td>
 <td>&nbsp;</td>
 <td>
 <p>x</p>
 </td>
 <td>
 <p>x</p>
 </td>
 <td>&nbsp;</td>
 </tr>
 <tr>
 <td>
 <p>throws only unchecked Exceptions</p>
 </td>
 <td>x</td>
 <td>
 <p>&nbsp;</p>
 </td>
 <td>
 <p>&nbsp;</p>
 </td>
 <td>x</td>
 </tr>
 </tbody>
 </table>


 <!-- CompletionStage.toCompletableFuture().join()

 T	get()
 Waits if necessary for this future to complete, and then returns its result.
 T	get(long timeout, TimeUnit unit)
 Waits if necessary for at most the given time for this future to complete, and then returns its result, if available.
 T	getNow(T valueIfAbsent)
 T	join()


 occurring exception of join and what they mean
 CompletableFuture.getNow(valueIfAbsent)

 orElseThrow
 orElseGet wie bei Optional

 -->

 <h3 id="java-util-function">Java Functions</h3>


Since Java 8, the JDK provides <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html">Lambda Expressions</a>
 and <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html">Method References</a>.



 <br><br>

 <table class="custom">
 <caption>Important {@link FunctionalInterface}s</caption>
 <tbody>
 <tr>
 <td>&nbsp;</td>
 <td>number of arguments</td>
 <td>behavior</td>
 <td>checked Exception</td>
 <td>purpose</td>
 </tr>
 <tr>
 <td>{@link java.util.function.Function}&lt;T,R&gt;</td>
 <td>1</td>
 <td>create value</td>
 <td>&nbsp;</td>
 <td>transforms one value into another</td>
 </tr>
 <tr>
 <td>{@link java.util.function.BiFunction}&lt;T,U,R&gt;</td>
 <td>2</td>
 <td>create value</td>
 <td>&nbsp;</td>
 <td>transforms two values into another</td>
 </tr>
 <tr>
 <td>{@link java.util.function.Consumer}&lt;T&gt;</td>
 <td>1</td>
 <td>side-effects</td>
 <td>&nbsp;</td>
 <td>side effect for one value</td>
 </tr>
 <tr>
 <td>{@link java.util.function.BiConsumer}&lt;T,U&gt;</td>
 <td>2</td>
 <td>side-effects</td>
 <td>&nbsp;</td>
 <td>side effect for two values</td>
 </tr>
 <tr>
 <td>{@link java.util.function.Supplier}&lt;T&gt;</td>
 <td>0</td>
 <td>create value</td>
 <td>&nbsp;</td>
 <td>on-demand creation of a value</td>
 </tr>
 <tr>
 <td>{@link java.util.concurrent.Callable}&lt;V&gt;</td>
 <td>0</td>
 <td>create value</td>
 <td>x</td>
 <td>like Supplier but throws Exception</td>
 </tr>
 <tr>
 <td>{@link java.lang.Runnable}</td>
 <td>0</td>
 <td>side-effects</td>
 <td>&nbsp;</td>
 <td>task which causes side-effects</td>
 </tr>
 </tbody>
 </table>



 <!--

 function vs. method block, first class member, but method reference

 -->


 <h3 id="threads">Threads and the Trinity</h3>

 <p>Which Thread is used for functional composition and callbacks depends on the method.
 For {@link java.util.concurrent.CompletionStage#thenApply(Function)},
 {@link java.util.concurrent.CompletionStage#thenAccept(Consumer)} ,
 {@link java.util.concurrent.CompletionStage#handle(BiFunction)} etc. exist three variants:</p>

 <ol>
  <li>{@link java.util.concurrent.CompletionStage#thenApply(Function)}, no suffix, if the future is not yet completed, the the thread which calls
 {@link java.util.concurrent.CompletableFuture#complete(Object)} is used to apply the function, if the future is completed, the thread which calls {@link java.util.concurrent.CompletionStage#thenApply(Function)} is used.
 So this method is discouraged if you use actors or tend to block threads.</li>
  <li>{@link java.util.concurrent.CompletionStage#thenApplyAsync(Function)} with suffix "Async" calls the function inside a Thread of {@link ForkJoinPool#commonPool()}. So you are better protected against deadlocks.</li>
  <li>{@link java.util.concurrent.CompletionStage#thenApplyAsync(Function, Executor)}  with suffix "Async" and additional {@link Executor} parameter calls the function inside a Thread pool you specify as second parameter.</li>
 </ol>


 <h3 id="error-handling">Error Handling</h3>

<p>If an exception occurs with the computation, it should be propagated to the future with {@link java.util.concurrent.CompletableFuture#completeExceptionally(Throwable)}, but only once in the lifetime of the future.
 As a result the following try catch block does not make sense, since the error is inside the future which is most likely computed in another Thread:</p>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#wrongWayOfErrorHandling()}


<h4>Recover from a failure without a new CompletionStage</h4>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#simpleRecover()}

<h4>Recover from a failure with producing a new CompletionStage</h4>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#simpleRecover()}

 <h4>Handle</h4>
{@link CompletionStage#exceptionally(Function)} is like applying {@link CompletionStage#thenApply(Function)} and then {@link CompletionStage#exceptionally(Function)}:

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#handleLikeExceptionallyAndThenApply()}

<p>You can use the exceptions to give error specific text to the user:</p>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#exceptionallyWithExceptionTypes()}

 <p>But you do not need to cover all problems:</p>

 {@include.example io.sphere.sdk.meta.AsyncDocumentationTest#exceptionallyWithExceptionTypesButUncoveredPart()}

 <!--



 handle for recover, ergebnis kann noch mal angepasst werden im nächsten stage
 handle kann auch optional zuruückgeben

 https://stackoverflow.com/questions/25338376/completablefuture-withfallback-handle-only-some-errors

 side effect, log error

 timeout with exception
 timeout with defaultvalue
 https://stackoverflow.com/questions/23575067/timeout-with-default-value-in-java-8-completablefuture

 rethrow exception if not matching

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

 <h3 id="summary">Summary</h3>




 <table class="custom">
 <caption>{@link java.util.concurrent.CompletionStage} vs. {@link java.util.concurrent.CompletableFuture}</caption>
 <tbody>
 <tr>
 <td>&nbsp;</td>
 <td>
 CompletionStage
 </td>
 <td>
 CompletableFuture
 </td>
 </tr>
 <tr>
 <td>
 type
 </td>
 <td>
 interface
 </td>
 <td>
 concrete class
 </td>
 </tr>


 <tr>
 <td>
 implements CompletionStage
 </td>
 <td>
 x
 </td>
 <td>
 x
 </td>
 </tr>

 <tr>
 <td>functional composition
 </td>
 <td>
 x
 </td>
 <td>
 x
 </td>
 </tr>


 <tr>
 <td>
 implements Future interface
 </td>
 <td>&nbsp;</td>
 <td>
 x
 </td>
 </tr>
 <tr>
 <td>
 can be filled with value or exception
 </td>
 <td>&nbsp;</td>
 <td>
 x
 </td>
 </tr>
 <tr>
 <td>
 can be cancelled
 </td>
 <td>&nbsp;</td>
 <td>
 x
 </td>
 </tr>
 <tr>
 <td>
 can check if completed
 </td>
 <td>&nbsp;</td>
 <td>
 x
 </td>
 </tr>
 <tr>
 <td>
 blocking usage directly possible
 </td>
 <td>&nbsp;</td>
 <td>
 x
 </td>
 </tr>
 <tr>
 <td>
 provides static methods for creation
 </td>
 <td>&nbsp;</td>
 <td>
 x
 </td>
 </tr>
 </tbody>
 </table>

 <br><br>

 <table class="custom centered">
 <caption>CompletionStage methods</caption>
 <tbody>
 <tr>
 <td>&nbsp;</td>

 <td> value </td>

 <td>
effect
 </td>

 <td>
 Function param
 </td>

 <td>
 Consumer param
 </td>

 <td>
 Runnable param
 </td>

 <td>
 maps value
 </td>

 <td>
 maps error
 </td>

 <td>
 or
 </td>

 <td>
 and
 </td>

 <td>
 Scala Future
 </td>

 <td>
 Play F.Promise
 </td>

 </tr>

 <tr>
 <td>
 thenApply
 </td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 map
 </td>

 <td>
 map
 </td>

 </tr>

 <tr>
 <td>
 thenCompose
 </td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 flatMap
 </td>

 <td>
 flatMap
 </td>

 </tr>

 <tr>
 <td>
 thenAccept
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
onSuccess
 </td>

 <td>
onSuccess
 </td>


 </tr>

 <tr>
 <td>
 thenRun
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>


 </tr>

 <tr>
 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>


 </tr>

 <tr>
 <td>
 exceptionally
 </td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 recover
 </td>

 <td>
 recover
 </td>


 </tr>

 <tr>
 <td>
 handle
 </td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 andThen
 </td>

 <td>&nbsp;</td>


 </tr>

 <tr>
 <td>
 whenComplete
 </td>

 <td>
 x
 </td>

 <td>x</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>


 </tr>

 <tr>
 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 </tr>

 <tr>
 <td>
 acceptEither
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>


 </tr>

 <tr>
 <td>
 thenAcceptBoth
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>
 zip
 </td>

 <td>
 zip
 </td>

 </tr>

 <tr>
 <td>
 applyToEither
 </td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 fallbackTo
 </td>

 <td>
 fallbackTo
 </td>


 </tr>

 <tr>
 <td>
 thenCombine
 </td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 </tr>

 <tr>
 <td>
 runAfterEither
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>


 </tr>

 <tr>
 <td>
 runAfterBoth
 </td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>

 <td>
 x
 </td>

 <td>&nbsp;</td>

 <td>&nbsp;</td>


 </tr>
 </tbody>
 </table>


 <h3 id="further-topics">Further Topics</h3>
 <!--
 skipable


 CompletableFuture.obtrudeValue, normally value will not be overridden
 -->





 <!-- Tabelle mit methoden und eigenschaften

 java.util.function funktionen vorstellen und wie man sie erweitern kann, triconsumer @FunctionalInterface
 -->

 <h3 id="further-sources">Further read and sources</h3>

<ul>
 <li><a href="https://docs.oracle.com/javase/8/docs/api/index.html?java/util/concurrent/CompletableFuture.html">CompletableFuture</a></li>
 <li><a href="https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletionStage.html">CompletionStage</a></li>
 <li><a href="https://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html">Java 8: Definitive guide to CompletableFuture</a></li>
 <li><a href="https://www.nurkiewicz.com/2013/05/java-8-completablefuture-in-action.html">Java 8: CompletableFuture in action</a></li>
 </ul>



 */
public final class AsyncDocumentation {
    private AsyncDocumentation() {
    }
}
