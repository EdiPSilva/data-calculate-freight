package br.com.java.datacalculatefreight.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("messages.properties")
public class MessageConfiguration {

    @Autowired
    private Environment environment;

    public String getMessageByCode(MessageCodeEnum messageCodeEnum) {
        return environment.getProperty(messageCodeEnum.getValue());
    }
}
