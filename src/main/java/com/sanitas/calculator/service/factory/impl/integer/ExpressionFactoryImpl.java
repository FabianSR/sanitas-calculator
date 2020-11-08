package com.sanitas.calculator.service.factory.impl.integer;

import com.sanitas.calculator.model.core.Expression;
import com.sanitas.calculator.model.domain.integer.AddExpression;
import com.sanitas.calculator.model.domain.integer.DivideExpression;
import com.sanitas.calculator.model.domain.integer.MultiplyExpression;
import com.sanitas.calculator.model.domain.integer.SubtractExpression;
import com.sanitas.calculator.service.factory.ExpressionFactory;
import com.sanitas.calculator.model.core.OperandExpression;

import static com.sanitas.calculator.util.Constants.*;

/**
 * Factory pattern
 * Concrete factory for Long
 */
public class ExpressionFactoryImpl implements ExpressionFactory<String,Long>{

    @Override
    public Expression<Long> getExpression(final String token) {
        switch (token) {
            case SUM_SIGN: return new AddExpression();
            case MINUS_SIGN: return new SubtractExpression();
            case MULTI_SIGN: return new MultiplyExpression();
            case DIV_SIGN: return new DivideExpression();
            default: return new OperandExpression(Long.valueOf(token));
        }
    }
}