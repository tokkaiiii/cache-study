package study.hellocache.service.strategy.bloomfilter;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BloomFilterTest {

    @Test
    void create() {
        BloomFilter bloomFilter1 = BloomFilter.create("testId1", 1000, 0.01);
        System.out.println("bloomFilter1 = " + bloomFilter1);
    }

    @Test
    void hash() {
        BloomFilter bloomFilter = BloomFilter.create("testId1", 1000, 0.01);
        for (int index = 0; index < 100; index++) {
            List<Long> hashedIndexes = bloomFilter.hash("value" + index);
            assertThat(hashedIndexes).hasSize(bloomFilter.getHashFunctionCount());
            for (Long hashedIndex : hashedIndexes) {
                assertThat(hashedIndex).isBetween(0L, bloomFilter.getBitSize() - 1);
                System.out.println("hashedIndex = " + hashedIndex);
            }
        }
    }
}