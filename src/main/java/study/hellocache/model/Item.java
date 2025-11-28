package study.hellocache.model;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@ToString
public class Item {
    private Long itemId;
    private String data;

    private static final AtomicLong NEXT_ID = new AtomicLong();

    public static Item create(ItemCreateRequest request) {
        var item = new Item();
        item.itemId = NEXT_ID.getAndIncrement();
        item.data = request.data();
        return item;
    }

    public void update(ItemUpdateRequest request) {
        data = request.data();
    }
}
