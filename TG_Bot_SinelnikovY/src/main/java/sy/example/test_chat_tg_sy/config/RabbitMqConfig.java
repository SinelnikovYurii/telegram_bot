package sy.example.test_chat_tg_sy.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static real.RabbitQueue.*;

@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue() {
        return new Queue(TEXT_MESSAGE_UPDATE);
    }
    @Bean
    public Queue docMessageQueue() {
        return new Queue(DOC_MESSAGE_UPDATE);
    }
    @Bean
    public Queue photoMessageQueue() {
        return new Queue(PHOTO_MESSAGE_UPDATE);
    }
    @Bean
    public Queue answerMessageQueue() {
        return new Queue(ANSWER_MESSAGE_UPDATE);
    }

    @Bean
    public Queue docNewmessageQueue() {
        return new Queue(DOCUMENT_MESSAGE);
    }

    @Bean
    public Queue photoNewmessageQueue() {
        return new Queue(DOCUMENT_TO_USER);
    }







}
