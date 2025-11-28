package study.hellocache.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import study.hellocache.model.Item;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

import static java.util.Comparator.reverseOrder;

@Slf4j
@Repository
public class ItemRepository {

    private final ConcurrentSkipListMap<Long, Item> dataBase = new ConcurrentSkipListMap<>(reverseOrder());


    public Optional<Item> read(Long itemId) {
        log.info("[ItemRepository.read] itemId = {}", itemId);
        return Optional.ofNullable(dataBase.get(itemId));
    }

    public List<Item> readAll(Long page, Long pageSize) {
        log.info("[ItemRepository.readAll] page = {}, pageSize = {}", page, pageSize);

        return dataBase.values().stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    public List<Item> readAllInfiniteScroll(Long lastItemId, Long pageSize) {
        log.info("[ItemRepository.readAllInfiniteScroll] lastItemId = {}, pageSize = {}", lastItemId, pageSize);
        if (lastItemId == null) {
            return readAll(1L, pageSize);
        }

        return dataBase.tailMap(lastItemId, false).values().stream()
                .limit(pageSize)
                .toList();
    }

    public Item create(Item item) {
        log.info("[ItemRepository.create] item = {}", item);
        dataBase.put(item.getItemId(), item);

        return item;
    }

    public Item update(Item item) {
        log.info("[ItemRepository.update] item = {}", item);
        dataBase.put(item.getItemId(), item);

        return item;
    }

    public void delete(Item item) {
        log.info("[ItemRepository.delete] itemId = {}", item.getItemId());
        dataBase.remove(item.getItemId());
    }

    public long count() {
        log.info("[ItemRepository.count]");
        return dataBase.size();
    }
}
