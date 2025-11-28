package study.hellocache.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.hellocache.common.cache.CacheStrategy;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.model.ItemUpdateRequest;
import study.hellocache.service.response.ItemPageResponse;
import study.hellocache.service.response.ItemResponse;
import study.hellocache.service.strategy.ItemCacheService;

import java.util.List;

@RequestMapping("/cache-strategy")
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final List<ItemCacheService> itemCacheServices;

    @GetMapping("/{cacheStrategy}/items/{itemId}")
    public ItemResponse read(
            @PathVariable CacheStrategy cacheStrategy,
            @PathVariable Long itemId
    ) {
        return resolveCacheHandler(cacheStrategy).read(itemId);
    }

    @GetMapping("/{cacheStrategy}/items")
    public ItemPageResponse readAll(
            @PathVariable CacheStrategy cacheStrategy,
            @RequestParam Long page,
            @RequestParam Long pageSize
    ) {
        return resolveCacheHandler(cacheStrategy).readAll(page, pageSize);
    }

    @GetMapping("/{cacheStrategy}/items/infinite-scroll")
    public ItemPageResponse readAllInfiniteScroll(
            @PathVariable CacheStrategy cacheStrategy,
            @RequestParam(required = false) Long lastItemId,
            @RequestParam Long pageSize
    ) {
        return resolveCacheHandler(cacheStrategy).readAllInfiniteScroll(lastItemId, pageSize);
    }

    @PostMapping("/{cacheStrategy}/items")
    public ItemResponse create(
            @PathVariable CacheStrategy cacheStrategy,
            @RequestBody ItemCreateRequest request
    ) {
        return resolveCacheHandler(cacheStrategy).create(request);
    }

    @PutMapping("/{cacheStrategy}/items/{itemId}")
    public ItemResponse update(
            @PathVariable CacheStrategy cacheStrategy,
            @PathVariable Long itemId,
            @RequestBody ItemUpdateRequest request
    ) {
        return resolveCacheHandler(cacheStrategy).update(itemId, request);
    }

    @DeleteMapping("/{cacheStrategy}/items/{itemId}")
    public void delete(
            @PathVariable CacheStrategy cacheStrategy,
            @PathVariable Long itemId
    ){
        resolveCacheHandler(cacheStrategy).delete(itemId);
    }

    private ItemCacheService resolveCacheHandler(CacheStrategy cacheStrategy) {
        return itemCacheServices.stream()
                .filter(itemCacheService -> itemCacheService.supports(cacheStrategy))
                .findFirst()
                .orElseThrow();
    }
}
