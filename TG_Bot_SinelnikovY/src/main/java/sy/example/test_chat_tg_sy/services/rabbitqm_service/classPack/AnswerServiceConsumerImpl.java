package sy.example.test_chat_tg_sy.services.rabbitqm_service.classPack;

import com.rabbitmq.tools.json.JSONUtil;
import org.jvnet.hk2.annotations.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import real.TranslateEntity;
import sy.example.test_chat_tg_sy.UpdateController;
import sy.example.test_chat_tg_sy.services.rabbitqm_service.interfacePack.AnswerServiceConsumer;

import static real.RabbitQueue.ANSWER_MESSAGE_UPDATE;
import static real.RabbitQueue.DOCUMENT_TO_USER;


@Component
public class AnswerServiceConsumerImpl implements AnswerServiceConsumer {

    private final UpdateController updateController;

    public AnswerServiceConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE_UPDATE)
    public void consume(SendMessage message) {
        updateController.NewBotMessage(message);
    }

    @Override
    @RabbitListener(queues = DOCUMENT_TO_USER)
    public void consume(TranslateEntity message) {
        updateController.NewBotMessage(message);
    }


}
