package business.classPack;

import business.Analize.*;
import business.FileCreate.WordFileCreater;
import business.MessagesMaker;
import business.intefacePack.MessageConsumer;
import business.intefacePack.MessageProducer;
import org.jvnet.hk2.annotations.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import real.MethodOfProcessing;
import real.TranslateEntity;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static real.RabbitQueue.*;

@Component
public class MessageConsumerImpl implements MessageConsumer {

    private final MessageProducer producer;
    private final topLayerAnalize topLayerAnalizer;
    private MessagesMaker messagesMaker;
    private Update mainUpdate;
    private MethodOfProcessing programMethod;
    private WordFileCreater wordFileCreater;


    public MessageConsumerImpl(MessageProducer producer, topLayerAnalize topLayerAnalizer, MessagesMaker messagesMaker, WordFileCreater wordFileCreater) {
        this.producer = producer;
        this.topLayerAnalizer = topLayerAnalizer;
        this.messagesMaker = messagesMaker;
        this.wordFileCreater = wordFileCreater;
    }


    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessage(Update update) {

        System.out.println("node text message");

        if(update.getMessage().getText().equals("/homeworkAnalyze")){
            programMethod = MethodOfProcessing.HOMEWORKANALIZE;
        }else if(update.getMessage().getText().equals("/averageScore")){
            programMethod = MethodOfProcessing.AVERAGESCORE;
        }


    }



    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessage(Update update) {

        mainUpdate = update;
        String filename = "Analyze " + topLayerAnalizer.analize(update, DOC_MESSAGE_UPDATE) + " in progress...";
        producer.produceAnswer(messagesMaker.generateCustomMessage(filename,update));

    }

    void HomeWorkAnalyze(File file){
        List<List<String>> list;

        try {
            list = topLayerAnalizer.analyzeExelHomeWork(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        wordFileCreater.generateHomeworkAnalyseWordFile(list);

        List<String> ll1 = list.get(0);
        List<String> ll2 = list.get(1);

        producer.produceAnswer(messagesMaker.generateCustomMessage("List of students with the largest appears: ",mainUpdate));

        for(int i = 0; i < 5; i++){
            producer.produceAnswer(messagesMaker.generateCustomMessage(ll1.get(i) + " " + ll2.get(i),mainUpdate));
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        producer.produceAnswer(new TranslateEntity(mainUpdate.getMessage().getChatId().toString(), MethodOfProcessing.HOMEWORKANALIZE));

    }

    void AverageScore(File file){
        List<List<String>> list;

        try {
            list = topLayerAnalizer.analyzeExelAverageScore(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        wordFileCreater.generateAverageScoreWordFile(list);

        List<String> list1 = list.get(0);
        List<String> list2 = list.get(1);
        List<String> list3 = list.get(2);
        List<String> list4 = list.get(3);
        List<String> list5 = list.get(4);

        producer.produceAnswer(messagesMaker.generateCustomMessage("Students with the lowest GPA: ",mainUpdate));

        for(int i = 0; i < 5; i++){
            producer.produceAnswer(messagesMaker.generateCustomMessage(list1.get(i) + ": " + list2.get(i) + "  " + list3.get(i) + "  " + list4.get(i) + " / " + list5.get(i),mainUpdate));
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        producer.produceAnswer(new TranslateEntity(mainUpdate.getMessage().getChatId().toString(), MethodOfProcessing.AVERAGESCORE));


    }

    @Override
    @RabbitListener(queues = DOCUMENT_MESSAGE)
    public void consumeDocFileMessage(File file) {
        System.out.println("document message catch");

        if(programMethod == MethodOfProcessing.HOMEWORKANALIZE){

            HomeWorkAnalyze(file);

        }else if(programMethod == MethodOfProcessing.AVERAGESCORE){
            AverageScore(file);

        }
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessage(Update update) {
        System.out.println("Business NODE: PhotoMessage");
    }




}
