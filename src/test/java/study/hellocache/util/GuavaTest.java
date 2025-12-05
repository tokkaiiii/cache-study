package study.hellocache.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;

class GuavaTest {

    @Test
    void murmur3_128(){
        HashFunction hashFunction = Hashing.murmur3_128(1);
        HashCode hashCode = hashFunction.hashString("test", UTF_8);
        System.out.println("hashCode = " + hashCode);
        System.out.println("hashCode.asLong() = " + hashCode.asLong());
    }
}
