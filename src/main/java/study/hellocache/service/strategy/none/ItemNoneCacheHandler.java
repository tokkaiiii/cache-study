package study.hellocache.service.strategy.none;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.hellocache.common.cache.CacheHandler;
import study.hellocache.common.cache.CacheStrategy;

import java.time.Duration;
import java.util.function.Supplier;

import static study.hellocache.common.cache.CacheStrategy.NONE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemNoneCacheHandler implements CacheHandler {
    @Override
    public <T> T fetch(String key, Duration ttl, Supplier<T> dataSourceSupplier, Class<T> clazz) {
        log.info("[ItemNoneCacheHandler.fetch] key = {}", key);
        return dataSourceSupplier.get();
    }

    @Override
    public void put(String key, Duration ttl, Object value) {
        log.info("[ItemNoneCacheHandler.put] key = {}", key);
    }

    @Override
    public void evict(String key) {
        log.info("[ItemNoneCacheHandler.evict] key = {}", key);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return NONE == cacheStrategy;
    }
}
