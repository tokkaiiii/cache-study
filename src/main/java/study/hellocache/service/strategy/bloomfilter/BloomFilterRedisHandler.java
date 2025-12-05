package study.hellocache.service.strategy.bloomfilter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BloomFilterRedisHandler {
    private final StringRedisTemplate redisTemplate;

    public void add(BloomFilter bloomFilter, String value) {
        redisTemplate.executePipelined((RedisConnection connection) -> {
            StringRedisConnection conn = new DefaultStringRedisConnection(connection);
            String key = genKey(bloomFilter);
            List<Long> hashedIndexes = bloomFilter.hash(value);
            hashedIndexes.forEach(index -> conn.setBit(key, index, true));
            return null;
        });
    }

    public boolean mightContain(BloomFilter bloomFilter, String value) {
        return redisTemplate.executePipelined((RedisConnection connection) -> {
                    StringRedisConnection conn = new DefaultStringRedisConnection(connection);
                    String key = genKey(bloomFilter);
                    List<Long> hashedIndexes = bloomFilter.hash(value);
                    hashedIndexes.forEach(index -> conn.getBit(key, index));
                    return null;
                })
                .stream()
                .map(Boolean.class::cast)
                .allMatch(Boolean.TRUE::equals);

    }

    public void delete(BloomFilter bloomFilter) {
        redisTemplate.delete(genKey(bloomFilter));
    }

    private String genKey(BloomFilter bloomFilter) {
        return genKey(bloomFilter.getId());
    }

    private String genKey(String id) {
        return "bloom-filter:%s".formatted(id);
    }
}
