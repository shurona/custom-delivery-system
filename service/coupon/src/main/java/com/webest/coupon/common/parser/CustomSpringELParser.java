package com.webest.coupon.common.parser;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spring Expression Language Parser
 */
public class CustomSpringELParser {

    private CustomSpringELParser() {
        throw new UnsupportedOperationException("이 클래스는 인스턴스 생성을 지원하지 않습니다.");
    }

    public static Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
        if (parameterNames == null || args == null || key == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        if (parameterNames.length != args.length) {
            throw new IllegalArgumentException("parameterNames and args must have the same length");
        }

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        return parser.parseExpression(key).getValue(context, Object.class);
    }
}
