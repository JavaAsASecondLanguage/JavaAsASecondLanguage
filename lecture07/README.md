# Java vs Python: who is better?

This is the service that analyzes news from [HackerNews](https://news.ycombinator.com/) and passes all news that contains "Java" or "Python" to service that analyzes sentiments of this news, [DeepAI](https://deepai.org/machine-learning-model/sentiment-analysis). After 2 hours of running this service, I've got such results for both Java and Python:

```
{
    python=Stats{
        mentions=3949,
        score=-1735,
        rating=-0.43924050632911393
    },
    java=Stats{
        mentions=3261,
        score=-1587,
        rating=-0.4865113427345187
    }
}
```

How we can see, after analyzing of approx. 7k of sentences, peopel at HackerNews like Python more than Java. However, both scores are negative, because programming at any language is hard work.

# Key decisions

After launching service once or twice it becomes obvious that bottleneck is DeepAI performance. So, we need to maximize the clients that goes to it and not think too much about clients that download from HackerNews.
However, if we make too many DeepAI clients, we'll get exception like this:
```
java.net.SocketTimeoutException: timeout
	at okhttp3.internal.http2.Http2Stream$StreamTimeout.newTimeoutException(Http2Stream.kt:677)
	at okhttp3.internal.http2.Http2Stream$StreamTimeout.exitAndThrowIfTimedOut(Http2Stream.kt:686)
	at okhttp3.internal.http2.Http2Stream.takeHeaders(Http2Stream.kt:143)
	at okhttp3.internal.http2.Http2ExchangeCodec.readResponseHeaders(Http2ExchangeCodec.kt:96)
	at okhttp3.internal.connection.Exchange.readResponseHeaders(Exchange.kt:106)
	at okhttp3.internal.http.CallServerInterceptor.intercept(CallServerInterceptor.kt:79)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.connection.ConnectInterceptor.intercept(ConnectInterceptor.kt:34)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.cache.CacheInterceptor.intercept(CacheInterceptor.kt:95)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.http.BridgeInterceptor.intercept(BridgeInterceptor.kt:83)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(RetryAndFollowUpInterceptor.kt:76)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
	at okhttp3.internal.connection.RealCall.getResponseWithInterceptorChain$okhttp(RealCall.kt:201)
	at okhttp3.internal.connection.RealCall.execute(RealCall.kt:154)
	at io.github.javaasasecondlanguage.lecture07.practice1.SentimentsClient.sentiments(SentimentsClient.java:36)
	at io.github.javaasasecondlanguage.lecture07.practice1.Analytics.analyzeWithDeepAI(Analytics.java:148)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:630)
	at java.base/java.lang.Thread.run(Thread.java:832)
```
So, I decided:
+ limit number of concurrent requests to DeepAI to 32 by using semaphore
+ create 1024 DeepAI threads to make a thread that wipes unnecesary sentences from queue (without "java" or "python") exist at any time
+ sometimes IOExceptions still occur. Just wait 4 minutes inside to give DeepAI some rest (not releasing semaphone, of course).

My service have analyzed 7k sentences in 2 hours, which seems quite satisfactory. 7k seems good number to me:
+ it is big enough to give some reasonable results
+ it is small enough not to pay too much dollars :-)

