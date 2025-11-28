package study.hellocache.util;

import org.junit.jupiter.api.Test;
import study.hellocache.model.Item;
import study.hellocache.model.ItemCreateRequest;

import java.util.concurrent.ConcurrentSkipListMap;

import static java.util.Comparator.reverseOrder;

public class ConcurrentSkipListMapTest {

    ConcurrentSkipListMap<Long, Item> dataBase = new ConcurrentSkipListMap<>(reverseOrder());

    @Test
    void add() {
        dataBase.put(1L, Item.create(new ItemCreateRequest("test1")));
        dataBase.put(2L, Item.create(new ItemCreateRequest("test2")));

        dataBase.values().forEach(System.out::println);
    }

    @Test
    void tailMap(){
        dataBase.put(1L, Item.create(new ItemCreateRequest("test1")));
        dataBase.put(2L, Item.create(new ItemCreateRequest("test2")));
        dataBase.put(3L, Item.create(new ItemCreateRequest("test3")));
        dataBase.put(4L, Item.create(new ItemCreateRequest("test4")));
        dataBase.put(5L, Item.create(new ItemCreateRequest("test5")));

        dataBase.tailMap(3L).values()
                .stream()
                .limit(2)
                .forEach(System.out::println);
    }
}
