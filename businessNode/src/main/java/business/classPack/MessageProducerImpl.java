package business.classPack;

import business.intefacePack.MessageProducer;
import org.jvnet.hk2.annotations.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import real.TranslateEntity;

import static real.RabbitQueue.*;


@Component
public class MessageProducerImpl implements MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public MessageProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE_UPDATE, sendMessage);
    }

    @Override
    public void produceAnswer(TranslateEntity message) {
        rabbitTemplate.convertAndSend(DOCUMENT_TO_USER, message);

    }


}
