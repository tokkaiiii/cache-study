package study.hellocache.common.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({METHOD})
@Retention(RUNTIME)
public @interface CustomCacheEvict {
    CacheStrategy cacheStrategy();

    String cacheName();

    String key();
}
