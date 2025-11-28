package study.hellocache.api;

import org.junit.jupiter.api.Test;
import study.hellocache.common.cache.CacheStrategy;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.model.ItemUpdateRequest;
import study.hellocache.service.response.ItemResponse;

import static study.hellocache.common.cache.CacheStrategy.NONE;

public class NoneStrategyApiTest {
    static final CacheStrategy CACHE_STRATEGY = NONE;

    @Test
    void createAndReadAndUpdateAndDelete(){
        ItemResponse created = ItemTestUtils.create(CACHE_STRATEGY, new ItemCreateRequest("test1"));
        System.out.println("created = " + created);

        ItemResponse read1 = ItemTestUtils.read(CACHE_STRATEGY, created.itemId());
        System.out.println("read1 = " + read1);

        ItemResponse updated = ItemTestUtils.update(CACHE_STRATEGY, read1.itemId(), new ItemUpdateRequest("updated test1"));
        System.out.println("updated = " + updated);

        ItemResponse read2 = ItemTestUtils.read(CACHE_STRATEGY, read1.itemId());
        System.out.println("read2 = " + read2);

        ItemTestUtils.delete(CACHE_STRATEGY, read2.itemId());

        ItemResponse read3 = ItemTestUtils.read(CACHE_STRATEGY, read1.itemId());
        System.out.println("read3 = " + read3);
    }
}
