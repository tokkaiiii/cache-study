package study.hellocache.common.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class CustomCacheKeyGenerator {
    private final ExpressionParser parser = new SpelExpressionParser();

    public String generateKey(JoinPoint joinpoint, CacheStrategy cacheStrategy, String cacheName, String keySpel) {
        EvaluationContext context = new StandardEvaluationContext();
        String[] parameterNames = ((MethodSignature) joinpoint.getSignature()).getParameterNames();
        Object[] args = joinpoint.getArgs();
        for (int index = 0; index < args.length; index++) {
            context.setVariable(parameterNames[index], args[index]);
        }

        return cacheStrategy + ":" + cacheName + ":" + parser.parseExpression(keySpel).getValue(context);
    }
}
