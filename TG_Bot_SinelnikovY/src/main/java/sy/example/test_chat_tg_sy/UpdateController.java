package sy.example.test_chat_tg_sy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import real.MethodOfProcessing;
import real.TranslateEntity;
import sy.example.test_chat_tg_sy.services.MessagesMaker;
import sy.example.test_chat_tg_sy.services.rabbitqm_service.classPack.UpdateServiceProducerImpl;

import static real.RabbitQueue.*;


@Component
public class UpdateController {

    private Telegram_Bot_Controller bot_controller;
    private Logger logger;
    private UpdateServiceProducerImpl updateServiceProducer;
    @Autowired
    private MessagesMaker messagesMaker;
    private MethodOfProcessing programMethod;

    public UpdateController(UpdateServiceProducerImpl updateServiceProducer) {
        this.updateServiceProducer = updateServiceProducer;
    }

    public void registerBot(Telegram_Bot_Controller bot_controller) {
        this.bot_controller = bot_controller;
    }

    private boolean CommandChecker(Update update) throws TelegramApiException {
        if(update.getMessage().getText().equals("/help") || update.getMessage().getText().equals("/start")){
            sendHelpMessage(update);
            return true;
        }else if(update.getMessage().getText().equals("/homeworkAnalyze")){
            sendAnalyzeMessage(update);
            programMethod = MethodOfProcessing.HOMEWORKANALIZE;
            return false;
        }else if(update.getMessage().getText().equals("/averageScore")){
            sendAnalyzeMessage(update);
            programMethod = MethodOfProcessing.AVERAGESCORE;
            return false;
        }

        return false;

    }



    private void sendAnalyzeMessage(Update update) {

        SendMessage str = messagesMaker.generateCustomMessage("I am ready to analyze your data,\nsend me the table for analysis",update);
        bot_controller.SendAnswer(str);

    }

    private void sendHelpMessage(Update update) {

        SendMessage str = messagesMaker.generateCustomMessage("Hello!\nThis bot can analyze your data. \nChoose what you want to analyze.\n/homeworkAnalyze\n/averageScore",update);
        bot_controller.SendAnswer(str);

    }

    public void nativeDataSort(Update update) throws TelegramApiException {

        if(update == null){
            logger.nullMessage();
            return;
        }
        if(update.getMessage() != null){
            MessageTypeDataSort(update);
        }else{
            logger.customMessage("catch unsupported message type");
        }

    }

    public void MessageTypeDataSort(Update update) throws TelegramApiException {

            Message message = update.getMessage();
            if(message.getText() != null){
                CommandChecker(update);
                processTextMessages(update);

            }else if(message.getDocument() != null){
                processDocMessages(update);
            }else if(message.getPhoto() != null){
                processPhotoMessages(update);
            }else{
                unsupportedMessageFormat(update);
            }

    }


    public void NewBotMessage(SendMessage sendMessage) {
        bot_controller.SendAnswer(sendMessage);
    }

    public void NewBotMessage(TranslateEntity message) {
        bot_controller.SendAnswer(message);
    }

    public void processDocMessages(Update update) throws TelegramApiException {

        GetFile getFile = new GetFile();
        getFile.setFileId(update.getMessage().getDocument().getFileId());
        File telegramfile = bot_controller.execute(getFile);
        java.io.File testfile = bot_controller.downloadFile(telegramfile);

        updateServiceProducer.produceDoc(DOC_MESSAGE_UPDATE, update,testfile);

    }

    public void processTextMessages(Update update) {
        updateServiceProducer.produce(TEXT_MESSAGE_UPDATE ,update);
        System.out.println("processing text");
    }

    public void processPhotoMessages(Update update) {
        updateServiceProducer.produce(PHOTO_MESSAGE_UPDATE,update);

    }

    public void unsupportedMessageFormat(Update update) {
        SendMessage sendMessage = messagesMaker.generateCustomMessage("That's unsupported message format",update);
        if(bot_controller.SendAnswer(sendMessage)){
            logger.customMessage("message sent successful");
        }
        NewBotMessage(sendMessage);
    }



}
