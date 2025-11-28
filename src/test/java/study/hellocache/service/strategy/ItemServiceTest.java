package study.hellocache.service.strategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.hellocache.model.ItemCreateRequest;
import study.hellocache.service.response.ItemResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
record ItemServiceTest(
        @Autowired ItemService itemService
) {

    @Test
    void read() {
        ItemResponse read = itemService.read(0L);

        assertThat(read).isNull();

        itemService.create(new ItemCreateRequest("test1"));

        read = itemService.read(0L);
        assertThat(read).isNotNull();
        assertThat(read.data()).isEqualTo("test1");
    }
}