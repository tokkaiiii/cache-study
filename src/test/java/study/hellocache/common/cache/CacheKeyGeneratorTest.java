package study.hellocache.common.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static study.hellocache.common.cache.CacheStrategy.*;

class CacheKeyGeneratorTest {

    @Test
    void generateKey() throws NoSuchMethodException {
        var keyGenerator = new CacheKeyGenerator();
        var cacheName = "users";
        var keySpel = "#userId + ':' + #type";

        var methodSignature = Mockito.mock(MethodSignature.class);
        given(methodSignature.getParameterNames()).willReturn(new String[]{"userId", "type"});
        given(methodSignature.getMethod()).willReturn(Dummy.class.getMethod("method", Long.class, String.class));

        var joinPoint = Mockito.mock(JoinPoint.class);
        given(joinPoint.getSignature()).willReturn(methodSignature);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L, "type"});

        String key = keyGenerator.generateKey(joinPoint, NONE, cacheName, keySpel);

        assertThat(key).isEqualTo("NONE:users:1:type");
    }

    private static class Dummy {
        public void method(Long userId, String type) {
        }
    }

}