package br.com.java.datacalculatefreight.utils;

import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericValidations {

    @Autowired
    MessageConfiguration messageConfiguration;

    public void validatevalidateNumberGreaterThanZero(Long value, MessageCodeEnum messageCodeEnum) {
        if (value <= 0) {
            throw new CustomException(messageConfiguration.getMessageByCode(messageCodeEnum));
        }
    }
}
