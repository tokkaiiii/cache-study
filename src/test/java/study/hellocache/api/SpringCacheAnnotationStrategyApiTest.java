package study.hellocache.api;

import org.junit.jupiter.api.Test;
import study.hellocache.common.cache.CacheStrategy;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.service.response.ItemResponse;

import static study.hellocache.common.cache.CacheStrategy.SPRING_CACHE_ANNOTATION;

public class SpringCacheAnnotationStrategyApiTest {
    static final CacheStrategy CACHE_STRATEGY = SPRING_CACHE_ANNOTATION;

    @Test
    void createAndReadAndUpdateAndDelete(){
        ItemResponse item1 = ItemApiTestUtils.create(CACHE_STRATEGY, new ItemCreateRequest("test1"));
        ItemResponse item2 = ItemApiTestUtils.create(CACHE_STRATEGY, new ItemCreateRequest("test2"));
        ItemResponse item3 = ItemApiTestUtils.create(CACHE_STRATEGY, new ItemCreateRequest("test3"));

        ItemResponse read1 = ItemApiTestUtils.read(CACHE_STRATEGY, item1.itemId());
        ItemResponse read2 = ItemApiTestUtils.read(CACHE_STRATEGY, item1.itemId());

        System.out.println("read1 = " + read1);
        System.out.println("read2 = " + read2);
    }
}
