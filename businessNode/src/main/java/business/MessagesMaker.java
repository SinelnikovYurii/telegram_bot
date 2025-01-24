package business;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessagesMaker {

    public SendMessage generateCustomMessage(String text, Update update) {
        SendMessage new_message = new SendMessage();
        new_message.setChatId(update.getMessage().getChatId().toString());
        new_message.setText(text);
        return new_message;



    }
}
