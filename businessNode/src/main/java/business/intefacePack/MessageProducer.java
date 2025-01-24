package business.intefacePack;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import real.TranslateEntity;

public interface MessageProducer {

    void produceAnswer(SendMessage sendMessage);

    void produceAnswer(TranslateEntity message);

}
