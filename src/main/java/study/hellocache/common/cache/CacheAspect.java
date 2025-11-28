package study.hellocache.common.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {
    private final List<CacheHandler> handlers;
    private final CacheKeyGenerator cacheKeyGenerator;

    @Around("@annotation(customCacheable)")
    public Object handleCacheable(ProceedingJoinPoint joinPoint, CustomCacheable customCacheable) {
        CacheStrategy cacheStrategy = customCacheable.cacheStrategy();

        CacheHandler cacheHandler = findCacheHandler(cacheStrategy);

        String key = cacheKeyGenerator.generateKey(joinPoint, cacheStrategy, customCacheable.cacheName(), customCacheable.key());
        Duration ttl = Duration.ofSeconds(customCacheable.ttlSeconds());

        Supplier<Object> dataSourceSupplier = createDataSourceSupplier(joinPoint);

        Class returnType = findReturnType(joinPoint);

        try {
            log.info("[CacheAspect.handleCacheable] key = {}", key);
            return cacheHandler.fetch(key, ttl, dataSourceSupplier, returnType);
        } catch (Exception e) {
            log.error("[CacheAspect.handleCacheable] key = {}, error = {}", key, e.getMessage());
            return dataSourceSupplier.get();
        }
    }


    private CacheHandler findCacheHandler(CacheStrategy cacheStrategy) {
        return handlers.stream()
                .filter(handler -> handler.supports(cacheStrategy))
                .findAny().orElseThrow();
    }

    private Supplier<Object> createDataSourceSupplier(ProceedingJoinPoint joinPoint) {
        return () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }

    private Class findReturnType(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getReturnType();
    }
}
