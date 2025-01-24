package sy.example.test_chat_tg_sy.services.rabbitqm_service.interfacePack;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

public interface UpdateServiceProducer {
    void produce(String rabbitQueue, Update update);
    void produceDoc(String rabbitQueue, Update update, File doc);
}
