package study.hellocache.common.cache;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface CustomCachePut {
    CacheStrategy cacheStrategy();

    String cacheName();

    String key();

    long ttlSeconds();
}
