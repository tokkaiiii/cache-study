package study.hellocache.service.strategy.bloomfilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.hellocache.common.cache.CacheStrategy;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.model.ItemUpdateRequest;
import study.hellocache.service.response.ItemPageResponse;
import study.hellocache.service.response.ItemResponse;
import study.hellocache.service.strategy.ItemCacheService;
import study.hellocache.service.strategy.ItemService;

import static study.hellocache.common.cache.CacheStrategy.BLOOM_FILTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemBloomFilterCacheService implements ItemCacheService {
    private final BloomFilterRedisHandler bloomFilterRedisHandler;
    private final ItemService itemService;

    private static final BloomFilter bloomFilter = BloomFilter.create(
            "item-bloom-filter",
            1000,
            0.01
    );

    @Override
    public ItemResponse read(Long itemId) {
        boolean result = bloomFilterRedisHandler.mightContain(bloomFilter, String.valueOf(itemId));

        if (!result) {
            return null;
        }

        return itemService.read(itemId);
    }

    @Override
    public ItemPageResponse readAll(Long page, Long pageSize) {
        return itemService.readAll(page, pageSize);
    }

    @Override
    public ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        return itemService.readAllInfiniteScroll(lastItemId, pageSize);
    }

    @Override
    public ItemResponse create(ItemCreateRequest request) {
        ItemResponse itemResponse = itemService.create(request);

        bloomFilterRedisHandler.add(bloomFilter, String.valueOf(itemResponse.itemId()));

        return itemResponse;
    }

    @Override
    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        return itemService.update(itemId, request);
    }

    @Override
    public void delete(Long itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return BLOOM_FILTER == cacheStrategy;
    }
}
