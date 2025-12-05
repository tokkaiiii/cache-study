package study.hellocache.service.strategy.bloomfilter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.hash.Hashing.murmur3_128;
import static java.nio.charset.StandardCharsets.UTF_8;
import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@NoArgsConstructor(access = PRIVATE)
public class BloomFilter {
    private String id;
    private long dataCount;
    private double falsePositiveRate;
    private long bitSize;
    private int hashFunctionCount;
    private List<BloomFilterHashFunction> hashFunctions;

    public static BloomFilter create(String id, long dataCount, double falsePositiveRate) {
        if (dataCount <= 0) {
            throw new IllegalArgumentException("dataCount must be greater than 0");
        }

        if (falsePositiveRate <= 0 || falsePositiveRate >= 1) {
            throw new IllegalArgumentException("falsePositiveRate must be between 0 and 1");
        }

        long bitSize = calculateBitSize(dataCount, falsePositiveRate);
        int hashFunctionCount = calculateHashFunctionCount(dataCount, bitSize);

        List<BloomFilterHashFunction> hashFunctions = IntStream.range(0, hashFunctionCount)
                .mapToObj(seed ->
                        (BloomFilterHashFunction) value -> Math.abs(murmur3_128(seed)
                                .hashString(value, UTF_8)
                                .asLong() % bitSize)
                ).toList();

        var bloomFilter = new BloomFilter();
        bloomFilter.id = id;
        bloomFilter.dataCount = dataCount;
        bloomFilter.falsePositiveRate = falsePositiveRate;
        bloomFilter.bitSize = bitSize;
        bloomFilter.hashFunctionCount = hashFunctionCount;
        bloomFilter.hashFunctions = hashFunctions;
        return bloomFilter;
    }

    private static long calculateBitSize(long dataCount, double falsePositiveRate) {
        return (long) Math.ceil(-(dataCount * Math.log(falsePositiveRate)) / Math.pow(Math.log(2), 2));
    }

    private static int calculateHashFunctionCount(long dataCount, long bitSize) {
        return (int) Math.ceil(bitSize / (double) dataCount * Math.log(2));
    }

    public List<Long> hash(String value) {
        return hashFunctions.stream()
                .map(hashFunction -> hashFunction.hash(value))
                .toList();
    }
}
