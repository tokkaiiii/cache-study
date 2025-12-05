package study.hellocache.service.strategy.bloomfilter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.hellocache.RedisTestContainerSupport;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BloomFilterRedisHandlerTest extends RedisTestContainerSupport {

    @Autowired
    BloomFilterRedisHandler bloomFilterRedisHandler;

    @Test
    void add() {
        BloomFilter bloomFilter = BloomFilter.create("testId", 1000, 0.01);

        bloomFilterRedisHandler.add(bloomFilter, "value");

        List<Long> hashedIndexes = bloomFilter.hash("value");
        for (long offset = 0; offset < bloomFilter.getBitSize(); offset++) {
            Boolean result = redisTemplate.opsForValue().getBit("bloom-filter:testId", offset);
            assertThat(result).isEqualTo(hashedIndexes.contains(offset));
        }
    }

    @Test
    void delete() {
        BloomFilter bloomFilter = BloomFilter.create("testId", 1000, 0.01);

        bloomFilterRedisHandler.add(bloomFilter, "value");

        bloomFilterRedisHandler.delete(bloomFilter);

        for (long offset = 0; offset < bloomFilter.getBitSize(); offset++) {
            Boolean result = redisTemplate.opsForValue().getBit("bloom-filter:testId", offset);
            assertThat(result).isFalse();
        }
    }

    @Test
    void mightContain() {
        BloomFilter bloomFilter = BloomFilter.create("testId", 1000, 0.01);

        List<String> values = IntStream.range(0, 1000)
                .mapToObj(index -> "value" + index)
                .toList();

        values.forEach(value -> bloomFilterRedisHandler.add(bloomFilter, value));

        values.forEach(value -> assertThat(bloomFilterRedisHandler.mightContain(bloomFilter, value)).isTrue());

        for (int index = 0; index < 10000; index++) {
            String value = "notAddedValue" + index;
            boolean result = bloomFilterRedisHandler.mightContain(bloomFilter, value);
            if (result) {
                System.out.println("value = " + value);
            }
        }
    }
}