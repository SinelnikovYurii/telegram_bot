package sy.example.test_chat_tg_sy;

import jakarta.annotation.PostConstruct;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import real.MethodOfProcessing;
import real.TranslateEntity;

import java.io.File;

@Component
public class Telegram_Bot_Controller extends TelegramLongPollingBot {


    @Value("${bot.name}")
    private String bot_name;
    @Value("${bot.token}")
    private String bot_token;

    @Autowired
    private UpdateController updateController;

    private Logger logger;

    private Telegram_Bot_Controller(){
        logger = new Logger("TelegramBot_Logger");
    }

    public boolean SendAnswer(SendMessage message){
        try {
            this.execute(message);
            return true;
        } catch (TelegramApiException e) {
            logger.customMessage("message transfer Error");
            throw new RuntimeException(e);
        }
    }

    public boolean SendAnswer(TranslateEntity message){

        if(message.getMethodOfProcessing() == MethodOfProcessing.HOMEWORKANALIZE){
            File docToUser = new File("Home Work Analyze.docx");
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(message.getId());
            sendDocument.setDocument(new InputFile(docToUser));

            try {
                this.execute(sendDocument);
                return true;
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }else if(message.getMethodOfProcessing() == MethodOfProcessing.AVERAGESCORE){
            File docToUser = new File("Average Score Analyze.docx");
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(message.getId());
            sendDocument.setDocument(new InputFile(docToUser));

            try {
                this.execute(sendDocument);
                return true;
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }


        return true;

    }




    @PostConstruct
    public void ControllerInit(){
        updateController.registerBot(this);
    }


    @Override
    public void onUpdateReceived(Update update) {

        logger.Logging(update.getMessage());

        try {
            updateController.nativeDataSort(update);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    public String getBotToken() {
        return bot_token;
    }

    @Override
    public String getBotUsername() {
        return bot_name;
    }

}
