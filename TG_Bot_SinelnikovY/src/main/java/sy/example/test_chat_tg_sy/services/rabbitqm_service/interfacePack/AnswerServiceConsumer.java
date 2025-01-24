package sy.example.test_chat_tg_sy.services.rabbitqm_service.interfacePack;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import real.TranslateEntity;

public interface AnswerServiceConsumer {
    void consume(SendMessage message);

    void consume(TranslateEntity message);

}
