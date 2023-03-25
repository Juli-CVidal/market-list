package com.market.list.handler;

import com.market.list.exception.MarketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidatorHandlerImpl<T> implements EntityHandler<T> {
    private final Validator validator;

    @Autowired
    public ValidatorHandlerImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void handle(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()){
           String errorMessage = violations.stream().map(ConstraintViolation::getMessage)
                   .collect(Collectors.joining(", "));
           throw new MarketException(errorMessage);
        }
    }

}
