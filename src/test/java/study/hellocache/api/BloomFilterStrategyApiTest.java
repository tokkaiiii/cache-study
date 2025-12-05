package study.hellocache.api;

import org.junit.jupiter.api.Test;
import study.hellocache.common.cache.CacheStrategy;
import study.hellocache.model.ItemCreateRequest;

import static study.hellocache.common.cache.CacheStrategy.BLOOM_FILTER;

class BloomFilterStrategyApiTest {
    CacheStrategy CACHE_STRATEGY = BLOOM_FILTER;

    @Test
    void test() {
        for (int i = 0; i < 1000; i++) {
            ItemApiTestUtils.create(CACHE_STRATEGY, new ItemCreateRequest("data" + i));
        }

        for (long itemId = 10000; itemId < 20000; itemId++) {
            ItemApiTestUtils.read(CACHE_STRATEGY, itemId);
        }
    }
}
