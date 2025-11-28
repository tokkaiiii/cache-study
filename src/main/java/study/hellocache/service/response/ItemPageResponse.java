package study.hellocache.service.response;

import study.hellocache.model.Item;

import java.util.List;

public record ItemPageResponse(
        List<ItemResponse> items,
        long count
) {
    public static ItemPageResponse from(List<Item> items, long count) {
        return new ItemPageResponse(
                items.stream()
                        .map(ItemResponse::from)
                        .toList(),
                count
        );
    }
}
