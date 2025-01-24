package business.intefacePack;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface MessageConsumer {

    void consumeTextMessage(Update update);
    void consumeDocMessage(Update update);
    void consumePhotoMessage(Update update);
    void consumeDocFileMessage(File file);

}
