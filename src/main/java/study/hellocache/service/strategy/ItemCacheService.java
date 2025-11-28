package study.hellocache.service.strategy;

import study.hellocache.common.cache.CacheStrategy;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.model.ItemUpdateRequest;
import study.hellocache.service.response.ItemPageResponse;
import study.hellocache.service.response.ItemResponse;

public interface ItemCacheService {
    ItemResponse read(Long itemId);

    ItemPageResponse readAll(Long page, Long pageSize);

    ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize);

    ItemResponse create(ItemCreateRequest request);

    ItemResponse update(Long itemId, ItemUpdateRequest request);

    void delete(Long itemId);

    boolean supports(CacheStrategy cacheStrategy);
}
