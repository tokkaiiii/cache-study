package study.hellocache.service.response;

import study.hellocache.model.Item;

import java.io.Serializable;

public record ItemResponse(
        Long itemId, String data
) implements Serializable {
    public static ItemResponse from(Item item) {
        return new ItemResponse(item.getItemId(), item.getData());
    }
}
