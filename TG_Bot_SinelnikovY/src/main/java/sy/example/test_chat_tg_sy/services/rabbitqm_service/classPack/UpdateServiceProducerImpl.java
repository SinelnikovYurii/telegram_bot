package sy.example.test_chat_tg_sy.services.rabbitqm_service.classPack;

import org.jvnet.hk2.annotations.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;
import sy.example.test_chat_tg_sy.services.rabbitqm_service.interfacePack.UpdateServiceProducer;

import java.io.File;
import java.util.List;

import static real.RabbitQueue.DOCUMENT_MESSAGE;


@Component
public class UpdateServiceProducerImpl implements UpdateServiceProducer {

    private final RabbitTemplate rabbitTemplate;

    public UpdateServiceProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void produce(String rabbitQueue, Update update) {
        System.out.println(update.getMessage().getChatId() + ": " + update.getMessage().getText());
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }

    @Override
    public void produceDoc(String rabbitQueue, Update update, File doc) {
        System.out.println("send doc");
        rabbitTemplate.convertAndSend(rabbitQueue, update);
        rabbitTemplate.convertAndSend(DOCUMENT_MESSAGE, doc);


    }


}
