package study.hellocache.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import study.hellocache.RedisTestContainerSupport;

@SpringBootTest
public class RedisTest extends RedisTestContainerSupport {

    @Test
    void notExistKey(){
        redisTemplate.opsForValue().getBit("no", 1L);
    }
}
