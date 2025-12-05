package study.hellocache.service.strategy.springcacheannotation;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import study.hellocache.common.cache.CacheStrategy;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.model.ItemUpdateRequest;
import study.hellocache.service.response.ItemPageResponse;
import study.hellocache.service.response.ItemResponse;
import study.hellocache.service.strategy.ItemCacheService;
import study.hellocache.service.strategy.ItemService;

import static study.hellocache.common.cache.CacheStrategy.SPRING_CACHE_ANNOTATION;

@Service
@RequiredArgsConstructor
public class ItemSpringCacheAnnotationCacheService implements ItemCacheService {
    private final ItemService itemService;

    @Override
    @Cacheable(cacheNames = "item", key = "#itemId")
    public ItemResponse read(Long itemId) {
        return itemService.read(itemId);
    }

    @Override
    @Cacheable(cacheNames = "itemList", key = "#page + ':' +#pageSize")
    public ItemPageResponse readAll(Long page, Long pageSize) {
        return itemService.readAll(page, pageSize);
    }

    @Override
    @Cacheable(cacheNames = "itemListInfiniteScroll", key = "#lastItemId + ':' +#pageSize")
    public ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        return itemService.readAllInfiniteScroll(lastItemId, pageSize);
    }

    @Override
    public ItemResponse create(ItemCreateRequest request) {
        return itemService.create(request);
    }

    @Override
    @CachePut(cacheNames = "item", key = "#itemId")
    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        return itemService.update(itemId, request);
    }

    @Override
    @CacheEvict(cacheNames = "item", key = "#itemId")
    public void delete(Long itemId) {
        itemService.delete(itemId);
    }

    @Override
    public boolean supports(CacheStrategy cacheStrategy) {
        return SPRING_CACHE_ANNOTATION == cacheStrategy;
    }


}
