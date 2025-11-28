package study.hellocache.service.strategy.none;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.hellocache.common.cache.CacheStrategy;
import study.hellocache.common.cache.CustomCacheEvict;
import study.hellocache.common.cache.CustomCachePut;
import study.hellocache.common.cache.CustomCacheable;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.model.ItemUpdateRequest;
import study.hellocache.service.response.ItemPageResponse;
import study.hellocache.service.response.ItemResponse;
import study.hellocache.service.strategy.ItemCacheService;
import study.hellocache.service.strategy.ItemService;

import static study.hellocache.common.cache.CacheStrategy.NONE;

@Service
@RequiredArgsConstructor
public class ItemNoneCacheService implements ItemCacheService {
    private final ItemService itemService;

    @Override
    @CustomCacheable(cacheStrategy = NONE, cacheName = "item", key = "#itemId", ttlSeconds = 5)
    public ItemResponse read(Long itemId) {
        return itemService.read(itemId);
    }

    @Override
    @CustomCacheable(cacheStrategy = NONE, cacheName = "itemList", key = "#page + ':' +#pageSize", ttlSeconds = 5)
    public ItemPageResponse readAll(Long page, Long pageSize) {
        return itemService.readAll(page, pageSize);
    }

    @Override
    @CustomCacheable(cacheStrategy = NONE, cacheName = "itemListInfiniteScroll", key = "#lastItemId + ':' +#pageSize", ttlSeconds = 5)
    public ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        return itemService.readAllInfiniteScroll(lastItemId, pageSize);
    }

    @Override
    public ItemResponse create(ItemCreateRequest request) {
        return itemService.create(request);
    }

    @Override
    @CustomCachePut(cacheStrategy = NONE, cacheName = "item", key = "#itemId", ttlSeconds = 5)
    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        return itemService.update(itemId, request);
    }

    @Override
    @CustomCacheEvict(cacheStrategy = NONE, cacheName = "item", key = "#itemId")
    public void delete(Long itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return NONE == cacheStrategy;
    }
}
