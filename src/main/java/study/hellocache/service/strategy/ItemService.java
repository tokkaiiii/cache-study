package study.hellocache.service.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.hellocache.model.Item;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.model.ItemUpdateRequest;
import study.hellocache.repository.ItemRepository;
import study.hellocache.service.response.ItemPageResponse;
import study.hellocache.service.response.ItemResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemResponse read(Long itemId) {
        return itemRepository.read(itemId).map(ItemResponse::from).orElse(null);
    }

    public ItemPageResponse readAll(Long page, Long pageSize) {
        return ItemPageResponse.from(
                itemRepository.readAll(page, pageSize),
                itemRepository.count()
        );
    }

    public ItemPageResponse readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        return ItemPageResponse.from(
                itemRepository.readAllInfiniteScroll(lastItemId, pageSize),
                itemRepository.count()
        );
    }

    public ItemResponse create(ItemCreateRequest request) {
        return ItemResponse.from(itemRepository.create(Item.create(request)));
    }

    public ItemResponse update(Long itemId, ItemUpdateRequest request) {
        Item item = itemRepository.read(itemId).orElseThrow();
        item.update(request);

        return ItemResponse.from(itemRepository.update(item));
    }

    public void delete(Long itemId) {
        itemRepository.read(itemId).ifPresent(itemRepository::delete);
    }

    public long count() {
        return itemRepository.count();
    }
}
